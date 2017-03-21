package at.spengergasse.game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import at.spengergasse.input.Keyboard;
import at.spengergasse.visual.Visual;

public class Core implements Runnable{
	
	private Visual visual;
	
	private int[] data;
	private int[] collisionMap;
	
	private Thread thread;
	
//	private GraphicsDevice gd;
	
	private JFrame frame;
	
	private int screenWidth,screenHeight;
	private int resolutionX,resolutionY;
	
	private boolean running = false;
	
	private Keyboard keyboard;
	
	boolean fullscreen;

	@Override
	public void run() {
		
		while(running){// sync funktionalitšt nutzen
			long now;
			long next;
			long delta = 1000000000 / 60;
			
			while(running){	//testen mit zb thread.sleep

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
		//1600x900 anfangs // es ist resizeable und fullscreen
		screenWidth = 1600;
		screenHeight = 900;
		//1920x1080
		resolutionX = 1920;
		resolutionY = 1080;
		
//		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		data = new int[resolutionX*resolutionY];
		
		visual = new Visual(resolutionX, resolutionY, data);
				
		frameInit(false);
		
		start();
	}
	
	public void frameInit(boolean fullscreen){
		frame = new JFrame("Invasion der Dingsis");
		
		frame.add(visual);
		
		keyboard = new Keyboard();
		
		visual.addKeyListener(keyboard);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(fullscreen){
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		else{
			frame.setUndecorated(false);
			frame.setSize(screenWidth, screenHeight);
		}
		
		frame.setVisible(true);
		
		frame.setSize(screenWidth, screenHeight);
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
		keyboard.update();
		
		if(keyboard.f11){
			if(fullscreen){
				fullscreen=false;
				frameInit(fullscreen);
			}
			else{
				fullscreen=true;
				frameInit(fullscreen);
			}
		}
		
		for(int i=0;i<data.length;i++){//bsp
			data[i]=0xff0066;
		}
	}
	
}
