package at.spengergasse.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;

import javax.swing.JFrame;

import at.spengergasse.entities.Entity;
import at.spengergasse.input.Keyboard;
import at.spengergasse.visual.Visual;

public class Core implements Runnable{

	private final int TICK_RATE = 60;// Die Tickrate mit der der Gameloop l�uft

	private JFrame frame;
	//--
	//	private Thread thread;
	//--
	private Visual visual;
	//--
	private Keyboard keyboard;
	//--
	private int[] data;
	private int[] collisionMap;
	//--
	//	private DisplayMode dM;
	//--
	private int screenWidth,screenHeight;
	private int resolutionX,resolutionY;
	//--
	//	private boolean running = false;
	//--
	private ArrayList<Entity> entities;
	//--
	private int tileSize;//gr��e der "pixel"

	//	boolean fullscreen;

	private MovementHandler movementHandler;

	private long rot; // rotation timer
	private final long  TIMER_ROT = 150;//verz�gerung f�r rotation in ms

	private final ScheduledExecutorService scheduler =
			Executors.newSingleThreadScheduledExecutor();

	@Override
	public void run() {

		//			long now;
		//			long next;
		//			long delta = 100000000 / 40;//in ms

		//			while(running){

		//				now = System.nanoTime();
		//				next = now + delta;

		update();

		visual.render();

		//				try {
		//					Thread.sleep(1000/500);
		//				} catch (InterruptedException e) {
		//					// TODO Auto-generated catch block
		//					e.printStackTrace();
		//				}
		//				
		//				while(now < next){//unbegrenzt oft in der sekunde
		//					now = System.nanoTime();
		//				}

		//				next = now + delta;
		//			}
	}

	public Core() throws IOException{
		//		dM = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

		tileSize = 5;
		//1600x900   16:9
		screenWidth = 1600;
		screenHeight = 900;

		resolutionX = screenWidth;
		resolutionY = screenHeight;

		frame = new JFrame("Invasion der Dingsis"); //falls men� und alles im selben fenster sein soll muss das dann ausgelagert werden

		movementHandler = new TestMovement();//TODO provisorisch

		keyboard = new Keyboard();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenWidth, screenHeight);

		entities = new ArrayList<Entity>();//bsp der spieler
		entities.add(new Entity("shapeTest",resolutionX/2, resolutionX/2, tileSize));

		data = new int[resolutionX*resolutionY];

		collisionMap = new int[resolutionX*resolutionY];

		visual = new Visual(resolutionX, resolutionY,screenWidth,screenHeight, data);

		visual.addKeyListener(keyboard);
		frame.add(visual);

		frame.setResizable(false);

		frame.setVisible(true);

		frame.pack();

		start();
	}


	public synchronized void start(){
		//		running = true;
		//		thread = new Thread(this,"Frame"); 
		//		thread.start();

		final ScheduledFuture<?> loopHandle =
				scheduler.scheduleAtFixedRate(this, 0, 1000000000/TICK_RATE, NANOSECONDS);//1000000000 ns = 1 sec
	}

	public synchronized void stop(){
		frame.dispose();
		scheduler.shutdown();
	}

	public void update(){//tick

		keyboard.update();
		movementHandler.handleMovement();

		for(int i=0;i<data.length;i++){//clear
			data[i]=0xaaaa00;
			//			if(i%2==0){data[i]=0;}else data[i]=0xffffff;
		}

		for(Entity e:entities){//alle entities werden in data eingeschrieben
			load(e);
		}
	}

	public void load(Entity entity){//l�dt das shape der entity ins data array
		int[] shape = entity.getShape();

		int width = entity.getWidth();
		int heigth = entity.getHeight();

		int x = entity.getX();
		int y = entity.getY();

		for(int posY = 0;posY<heigth;posY++){
			for(int posX=0;posX<width;posX++){
				if(shape[posX+posY*width]!= 0){
					data[x + y * resolutionX + posX + posY * resolutionX] = shape[posX+posY*width];
				}
			}
		}
	}

	private abstract class MovementHandler{
		public abstract void handleMovement();
	}

	private class TestMovement extends MovementHandler{

		@Override
		public void handleMovement() {
			long now = System.currentTimeMillis();

			int moveFactor = 5;

			int orientation = entities.get(0).getRotation();

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
				entities.get(0).rotate(1);
				rot=System.currentTimeMillis()+TIMER_ROT;
			}

			if(keyboard.left && now > rot ){
				entities.get(0).rotate(-1);
				rot=System.currentTimeMillis()+TIMER_ROT;
			}

			entities.get(0).move(x, y);//index 0 = player
		}
	}
}
