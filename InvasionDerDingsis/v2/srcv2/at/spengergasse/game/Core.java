package at.spengergasse.game;

import java.io.IOException;
import java.nio.IntBuffer;

import at.spengergasse.entities.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.PixelFormat;

public class Core extends Application {
	
	private Entity player;
	
	private Canvas canvas;
	private WritableImage image;
	private PixelWriter pixelWriter;
	
	private GraphicsContext graphicsContext;
	
	private Group group;
	private Scene scene;
	
	private int[] data;
	
	private int screenX,screenY;
	
	private Timeline gameloop;
	
	private PixelFormat<IntBuffer> pixelFormat;
	
	public void init() throws IOException{
		screenX = 1600+6;
		screenY = 900+29;
		
		player = new Entity("shapeTest",screenX/2, screenY/2, 5);
		
		group = new Group();
		scene = new Scene(group,screenX,screenY);
		
		data = new int[screenX*screenY];
		
		image = new WritableImage(screenX, screenY);
		canvas = new Canvas(screenX,screenY);
		pixelWriter = image.getPixelWriter();
		
		pixelFormat = PixelFormat.getIntArgbInstance();
		
		graphicsContext = canvas.getGraphicsContext2D();
		
		group.getChildren().add(canvas);
		
		gameloop = new Timeline();
		gameloop.setCycleCount(Timeline.INDEFINITE);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(screenX);
		primaryStage.setHeight(screenY);
		
		primaryStage.setScene(scene);
		
		primaryStage.setResizable(false);
		
		primaryStage.show();
		
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.016),new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
            	update();
            	draw();
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
			data[i]=-16777216;
		}
		
		load(player);
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
					data[x + y * screenX + posX + posY * screenY] = shape[posX+posY*width];
				}
			}
		}
	}

	
}
