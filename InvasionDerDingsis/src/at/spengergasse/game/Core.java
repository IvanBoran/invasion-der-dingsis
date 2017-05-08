package at.spengergasse.game;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import at.spengergasse.entities.Entity;
import at.spengergasse.input.Keyboard;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.image.PixelFormat;

public class Core extends Application {
	
	private static int id;//Der Zähler damit jedes Entity eine eigene U_ID bekommt
	
	private Entity player;
	private ArrayList<Entity> entities;
	
	private Canvas canvas;
	private WritableImage image;
	private PixelWriter pixelWriter;
	
	private GraphicsContext graphicsContext;
	
	private Group group;
	private Scene scene;
	
	private int[] data;
	private int[] collisionMap;
	
	private int screenX,screenY;
	
	private PixelFormat<IntBuffer> pixelFormat;
	
	private Keyboard keyboard;

	private MovementHandler movementHandler;// Abstrakte super-Klasse von allen MovementHandlern damit man verschiedene benutzen kann um z.B in verschiedenen Spielmodie verschiedene Tasten belegen zu können

	private long rot; // Der Timer für die Rotation des Spielers damit das drehen kontrollierbar wird
	private final long  TIMER_ROT = 150;// Die Zeitdifferenz zwischen einmal rotieren und dem nächsten mal in millisekunden (1 sec = 1000 ms)
	
	private long sht;
	
//	private DisplayMode dm;
	
	public void init() throws IOException{
//		dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		screenX = 1080;
		screenY = 720;
		
		entities = new ArrayList<>();
		
		entities.add(new Entity("shapePlayer",screenX/2-40, screenY-80, 3,id++,0,0));
		player=entities.get(0);
		
		entities.add(new Entity("shapeEnemy",screenX-40-100*1, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*2, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*3, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*4, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*5, screenY/4-80, 3,id++,0,0));
		
		entities.add(new Entity("shapeEnemy",screenX-40-100*6, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*7, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*8, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*9, screenY/4-80, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*10, screenY/4-80, 3,id++,0,0));
		
		entities.add(new Entity("shapeEnemy",screenX-40-100*1, screenY/4-80-80*1, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*1, screenY/4-80-80*2, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*1, screenY/4-80-80*3, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*1, screenY/4-80-80*4, 3,id++,0,0));
		entities.add(new Entity("shapeEnemy",screenX-40-100*1, screenY/4-80-80*5, 3,id++,0,0));
		
		
		group = new Group();
		scene = new Scene(group,screenX,screenY);
		
		data = new int[screenX*screenY];
		
		collisionMap = new int[screenX*screenY];
		
		image = new WritableImage(screenX, screenY);
		canvas = new Canvas(screenX,screenY);
		pixelWriter = image.getPixelWriter();
		
		pixelFormat = PixelFormat.getIntArgbInstance();
		
		keyboard = new Keyboard();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, keyboard);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, keyboard);
		
		movementHandler = new DefaultMovement();
		
		graphicsContext = canvas.getGraphicsContext2D();
		
		group.getChildren().add(canvas);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(screenX+6);
		primaryStage.setHeight(screenY+29);
		
		primaryStage.setScene(scene);
		
		primaryStage.setResizable(false);
		
		primaryStage.show();

		new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				update();
				draw();
			}
		}.start();;
	}
	
	private void draw(){
		pixelWriter.setPixels(0, 0, screenX, screenY,pixelFormat,data,0, screenX);
		graphicsContext.drawImage(image, 0, 0);
	}
	
	private void update(){
		for(int i = 0;i<data.length;i++){
			data[i]=0xff000000;
			collisionMap[i]=-1;
		}
		
		if(player!=null){
			movementHandler.handleMovement();//nur für player
			if(keyboard.space){
				if(sht<=System.currentTimeMillis()-500){
					try {
						entities.add(player.shoot(id++));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					sht=System.currentTimeMillis();
				}
			}
		}
		
		for(Entity e:entities){ 
			if(!e.isDead()){
				if(load(e)){
					check(e);
					e.update();
				}
			}
		}
	}
	
	private void check(Entity e){
		int id = e.getId();
		
		int width = e.getWidth();
		int heigth = e.getHeight();
		
		int x = e.getX();
		int y = e.getY();
		
		for(int posY = 0;posY<heigth;posY++){
			for(int posX=0;posX<width;posX++){
				if(collisionMap[x + y * screenX + posX + posY * screenX]==id){
					if(collisionMap[(x + y * screenX + posX + posY * screenX)-1]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)-1]!=id){
						entities.get(collisionMap[(x + y * screenX + posX + posY * screenX)-1]).died();
						entities.get(id).died();
						return;
					}
					if(collisionMap[(x + y * screenX + posX + posY * screenX)+1]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)+1]!=id){
						entities.get(id).died();
						entities.get(collisionMap[(x + y * screenX + posX + posY * screenX)+1]).died();
						return;
					}
					if(collisionMap[(x + y * screenX + posX + posY * screenX)+1*screenX]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)+1*screenX]!=id){
						entities.get(id).died();
						entities.get(collisionMap[(x + y * screenX + posX + posY * screenX)+1*screenX]).died();
						return;
					}
					if(collisionMap[(x + y * screenX + posX + posY * screenX)-1*screenX]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)-1*screenX]!=id){
						entities.get(id).died();
						entities.get(collisionMap[(x + y * screenX + posX + posY * screenX)-1*screenX]).died();
						return;
					}
				}
			}
		}
	}
		
	private boolean load(Entity entity){//Ruft alle Informationen vom jeweiligen Entity auf und lädt es so auf die richtige Position im data Array
		int[] shape = entity.getShape();
		
		int id = entity.getId();

		int width = entity.getWidth();
		int heigth = entity.getHeight();

		int x = entity.getX();
		int y = entity.getY();
		
		if(x>screenX-1 || y>screenY-1 ||x<=1 || y<=1){
			entity.died();
			return false;
		}

		for(int posY = 0;posY<heigth;posY++){
			for(int posX=0;posX<width;posX++){
				if(shape[posX+posY*width]!= 0){
					data[x + y * screenX + posX + posY * screenX] = shape[posX+posY*width];
					collisionMap[x + y * screenX + posX + posY * screenX] = id;
				}
			}
		}
		return true;
	}
	
	private abstract class MovementHandler{ // MovementHandler wie oben beschrieben wird dazu benutzt später verschiedene Tastaturlayouts benutzen zu können
		public abstract void handleMovement();
	}
	
	private class DefaultMovement extends MovementHandler{

		@Override
		public void handleMovement() {
			
			int x = 0;

			int moveFactor = 4;
			
			if(keyboard.right){
				x+=moveFactor;
			}
			
			if(keyboard.left){
				x-=moveFactor;
			}
			
			int width = player.getWidth();
			
			int posX = player.getX();

			if(posX+width+ x +3> screenX){
				x =screenX-posX-width-3;
			}else if(posX + x -3< 0){
				x = - posX+3;
			}

			player.move(x, 0);
		}
		
	}

	private class TestMovement extends MovementHandler{

		@Override
		public void handleMovement() {
			
			long now = System.currentTimeMillis();

			int moveFactor = 5;

			int orientation = player.getRotation();
			
			int width = player.getWidth();
			int height = player.getHeight();
			
			int posX = player.getX();
			int posY = player.getY();

			int x = 0;
			int y = 0;

			if(keyboard.up){
				if(orientation == 0){
					y-=moveFactor+2;
				}else if(orientation == 1){
					y-=moveFactor;
					x+=moveFactor;
				}else if(orientation == 2){
					x+=moveFactor+2;
				}else if(orientation == 3){
					y+=moveFactor;
					x+=moveFactor;
				}else if(orientation == 4){
					y+=moveFactor+2;
				}else if(orientation == 5){
					y+=moveFactor;
					x-=moveFactor;
				}else if(orientation == 6){
					x-=moveFactor+2;
				}else if(orientation == 7){
					x-=moveFactor;
					y-=moveFactor;
				}
			}
			if(keyboard.down){
				if(orientation == 0){
					y+=moveFactor+2;
				}else if(orientation == 1){
					y+=moveFactor;
					x-=moveFactor;
				}else if(orientation == 2){
					x-=moveFactor+2;
				}else if(orientation == 3){
					y-=moveFactor;
					x-=moveFactor;
				}else if(orientation == 4){
					y-=moveFactor+2;
				}else if(orientation == 5){
					y-=moveFactor;
					x+=moveFactor;
				}else if(orientation == 6){
					x+=moveFactor+2;
				}else if(orientation == 7){
					x+=moveFactor;
					y+=moveFactor;
				}
			}

			if(keyboard.right && now > rot){
				player.rotate(1);
				rot=System.currentTimeMillis()+TIMER_ROT;
			}

			if(keyboard.left && now > rot ){
				player.rotate(-1);
				rot=System.currentTimeMillis()+TIMER_ROT;
			}
			
			if(posX+width+ x +3> screenX){
				x =screenX-posX-width-3;
			}else if(posX + x -3< 0){
				x = - posX+3;
			}
				
			
			if(posY+height+ y > screenY){
				y=screenY-posY-height;
			}else if(posY + y < 0){
				y= -posY;
			}

			player.move(x, y);
		}
	}

	
}
