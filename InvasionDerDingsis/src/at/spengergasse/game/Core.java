package at.spengergasse.game;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import at.spengergasse.entities.Entity;
import at.spengergasse.input.Keyboard;
import at.spengergasse.visual.Visual;

public class Core implements Runnable{
	
	private Keyboard keyboard;
	//--
	private Visual visual;
	//--
	private int[] data;
	private int[] collisionMap;
	//--
	private Thread thread;
	//--
	private DisplayMode dM;
	//--
	private JFrame frame;
	
	private int screenWidth,screenHeight;
	private int resolutionX,resolutionY;
	//--
	private boolean running = false;
	//--
	private ArrayList<Entity> entities;
	//--
	private int tileSize;//größe der "pixel"
	
//	boolean fullscreen;

	private long rot;
	private final long  TIMER_ROT = 150;
	
	@Override
	public void run() {
		
		while(running){// sync funktionalität nutzen
			long now;
			long next;
			long delta = 1000000000 / 120;
			
			while(running){	//testen mit zb thread.sleep
				try { // gibt ab und so kleine ruckler
					thread.sleep(0,10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				now = System.nanoTime();
				next = now + delta;

				while(now < next){
					//unbegrenzt oft in der sekunde
					now = System.nanoTime();
					visual.render();
				}
				
				
				//60 mal die sekunde
				
				update();

				
				next = now + delta;
			}
		}
		
	}
	
	public Core() throws IOException{//TODO exception handling
		dM = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		tileSize = 10;
		//1600x900 anfangs // es ist resizeable und fullscreen
		screenWidth = 1600;
		screenHeight = 900;
		//1920x1080
		resolutionX = screenWidth;
		resolutionY = screenHeight;
		
		frame = new JFrame("Invasion der Dingsis");
		
		keyboard = new Keyboard();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenWidth, screenHeight);
		
		entities = new ArrayList<Entity>();
		entities.add(new Entity("shapeTest",resolutionX/2, resolutionX/2, tileSize));//bsp der spieler
		
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
		running = true;
		thread = new Thread(this,"Frame"); 
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(){//tick
		keyboard.update();//tastatur eingaben werden überprüft //sollte vll wo anders hin verschoben werden weil ticks nur 60 mal die sekunde ablaufen
		//im moment kommen hier die abfragen bezüglich welche tasten drücke registriert wurden
		handleMovement();
		
		for(int i=0;i<data.length;i++){//clear
			data[i]=0;
		}
		
		for(Entity e:entities){//alle entities werden in data eingeschrieben
			load(e);
		}
	}
	
	private void handleMovement(){
		long now = System.currentTimeMillis();
		
		int x = 0;
		int y = 0;
		if(keyboard.up){
			y--;
		}
		if(keyboard.down){
			y++;
		}
		if(keyboard.right && now > rot){
			entities.get(0).rotate(true);
			rot=System.currentTimeMillis()+TIMER_ROT;
		}
		if(keyboard.left && now > rot ){
			entities.get(0).rotate(false);
			rot=System.currentTimeMillis()+TIMER_ROT;
		}
		entities.get(0).move(x, y);//index 0 = player
	}
	
	public void load(Entity entity){//lädt das shape der entity ins data array
		int[] shape = entity.getShape();
		int width = entity.getWidth();
		int heigth = entity.getHeight();
		int x = entity.getX();
		int y = entity.getY();
		for(int posY = 0;posY<heigth;posY++){
			for(int posX=0;posX<width;posX++){
				if(shape[posX+posY*width]!=0){
					data[x + y * resolutionX + posX + posY * resolutionX] = shape[posX+posY*width];
				}
			}
		}
	}
	
}
