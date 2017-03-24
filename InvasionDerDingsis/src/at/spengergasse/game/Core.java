package at.spengergasse.game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
//	private GraphicsDevice gd;
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

	@Override
	public void run() {
		
		while(running){// sync funktionalität nutzen
			long now;
			long next;
			long delta = 1000000000 / 120;
			
			while(running){	//testen mit zb thread.sleep
				try {
					thread.sleep(10);
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
	
	public Core(){
		tileSize = 10;
		//1600x900 anfangs // es ist resizeable und fullscreen
		screenWidth = 500;
		screenHeight = 500;
		//1920x1080
		resolutionX = 500;
		resolutionY = 500;
		
		frame = new JFrame("Invasion der Dingsis");
		
		keyboard = new Keyboard();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenWidth, screenHeight);
		
//		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		entities = new ArrayList<Entity>();
		entities.add(new Entity(null, 50,50, resolutionX/2, resolutionY/2));//bsp
		entities.add(new Entity(null, 10,50, resolutionX/3, resolutionY/3));//bsp
		
		data = new int[resolutionX*resolutionY];
		
		visual = new Visual(resolutionX, resolutionY,screenWidth,screenHeight, data);
		
		visual.addKeyListener(keyboard);
		frame.add(visual);
		
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
		if(keyboard.up){//TODO
			entities.get(0).move(1, -1);
		}
		
		for(int i=0;i<data.length;i++){//clear
			data[i]=0;
		}
		
		for(Entity e:entities){//alle entities werden in data eingeschrieben
			load(e);
		}
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
