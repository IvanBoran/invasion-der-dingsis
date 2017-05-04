package at.spengergasse.game;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.sun.javafx.sg.prism.EffectFilter;

import at.spengergasse.entities.Entity;
import at.spengergasse.input.Keyboard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
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
	
	private Timeline gameloop;
	
	private PixelFormat<IntBuffer> pixelFormat;
	
	private Keyboard keyboard;

	private MovementHandler movementHandler;// Abstrakte super-Klasse von allen MovementHandlern damit man verschiedene benutzen kann um z.B in verschiedenen Spielmodie verschiedene Tasten belegen zu können

	private long rot; // Der Timer für die Rotation des Spielers damit das drehen kontrollierbar wird
	private final long  TIMER_ROT = 150;// Die Zeitdifferenz zwischen einmal rotieren und dem nächsten mal in millisekunden (1 sec = 1000 ms)
	
	
	public void init() throws IOException{
		screenX = 1600;
		screenY = 900;
		
		entities = new ArrayList<>();
		
		player = new Entity("shapeTest",screenX/2-40, screenY-80, 5);//TODO
		entities.add(player);
		
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
		
		movementHandler = new TestMovement();
		
		graphicsContext = canvas.getGraphicsContext2D();
		
		group.getChildren().add(canvas);
		
		gameloop = new Timeline();
		gameloop.setCycleCount(Timeline.INDEFINITE);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(screenX+6);
		primaryStage.setHeight(screenY+29);
		
		primaryStage.setScene(scene);
		
		primaryStage.setResizable(false);
		
		primaryStage.show();
		
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.016666666666666666666),new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
            	draw();
            	update();
            }
        });
		
		gameloop.getKeyFrames().add( keyFrame );
		gameloop.play();
	}
	
	private void draw(){
		pixelWriter.setPixels(0, 0, screenX, screenY,pixelFormat,data,0, screenX);
		graphicsContext.drawImage(image, 0, 0);
	}
	
	private void update(){
		for(int i = 0;i<data.length;i++){
			data[i]=0xff000000;
			collisionMap[i]=0;
		}
		
		movementHandler.handleMovement();//nur für player
		
		for(Entity e:entities){ 
			load(e);
		}
	}
		
	public void load(Entity entity){//Ruft alle Informationen vom jeweiligen Entity auf und lädt es so auf die richtige Position im data Array
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

			int moveFactor = 10;
			
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
