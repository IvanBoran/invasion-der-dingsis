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
	
//	private DisplayMode dm;
	
	public void init() throws IOException{
//		dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		screenX = 1080;
		screenY = 720;
		
		entities = new ArrayList<>();
		
//		player = new Entity("shapeTest",screenX/2-40, screenY-80, 3);//TODO
//		entities.add(player);
		entities.add(new Entity("shapeTest",screenX/2-40, screenY-80, 3));
		player=entities.get(0);
		entities.add(new Entity("shapeTest",screenX/4-40, screenY-80, 3));
		
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
			data[i]=0xffff0000;
			collisionMap[i]=-1;
		}
		
		if(player!=null){
			movementHandler.handleMovement();//nur für player
		}
		
		for(Entity e:entities){ 
			load(e);
		}
		
		for(Entity e:entities){ 
			check(e);
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
						entities.remove(collisionMap[(x + y * screenX + posX + posY * screenX)-1]);System.out.println(collisionMap[(x + y * screenX + posX + posY * screenX)-1]+" "+id);
						entities.remove(id);
						
						return;
					}
					if(collisionMap[(x + y * screenX + posX + posY * screenX)+1]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)+1]!=id){
						entities.remove(id);
						entities.remove(collisionMap[(x + y * screenX + posX + posY * screenX)+1]);
						return;
					}
					if(collisionMap[(x + y * screenX + posX + posY * screenX)+1*screenX]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)+1*screenX]!=id){
						entities.remove(id);
						entities.remove(collisionMap[(x + y * screenX + posX + posY * screenX)+1*screenX]);
						return;
					}
					if(collisionMap[(x + y * screenX + posX + posY * screenX)-1*screenX]!=-1 && collisionMap[(x + y * screenX + posX + posY * screenX)-1*screenX]!=id){
						entities.remove(id);
						entities.remove(collisionMap[(x + y * screenX + posX + posY * screenX)-1*screenX]);
						return;
					}
				}
			}
		}
	}
		
	private void load(Entity entity){//Ruft alle Informationen vom jeweiligen Entity auf und lädt es so auf die richtige Position im data Array
		int[] shape = entity.getShape();
		
		int id = entity.getId();

		int width = entity.getWidth();
		int heigth = entity.getHeight();

		int x = entity.getX();
		int y = entity.getY();

		for(int posY = 0;posY<heigth;posY++){
			for(int posX=0;posX<width;posX++){
				if(shape[posX+posY*width]!= 0){
					data[x + y * screenX + posX + posY * screenX] = shape[posX+posY*width];
					collisionMap[x + y * screenX + posX + posY * screenX] = id;
				}
			}
		}
	}
	
	private abstract class MovementHandler{ // MovementHandler wie oben beschrieben wird dazu benutzt später verschiedene Tastaturlayouts benutzen zu können
		public abstract void handleMovement();
	}
	
	private class DefaultMovement extends MovementHandler{

		@Override
		public void handleMovement() {
			
			int x = 0;

			int moveFactor = 1;
			
			if(keyboard.right){
				x+=moveFactor;
			}
			
			if(keyboard.left){
				x-=moveFactor;
			}
			
			int width = player.getWidth();
			
			int posX = player.getX();

			if(posX+width+ x > screenX){
				x=screenX-posX-width;
			}else if(posX + x < 0){
				x= -posX;
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
			
			if(posX+width+ x > screenX){
				x=screenX-posX-width;
			}else if(posX + x < 0){
				x= -posX;
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
