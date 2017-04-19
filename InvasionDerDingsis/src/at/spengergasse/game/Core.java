package at.spengergasse.game;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import at.spengergasse.entities.Entity;
import at.spengergasse.input.Keyboard;
import at.spengergasse.visual.Visual;

public class Core implements Runnable{
	
	private JFrame frame;
	//--
	private Thread thread;
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
	private boolean running = false;
	//--
	private ArrayList<Entity> entities;
	//--
	private int tileSize;//größe der "pixel"
	
//	boolean fullscreen;

	private long rot; // rotation timer
	private final long  TIMER_ROT = 150;
	
	@Override
	public void run() {
		
			long now;
			long next;
			long delta = 1000 / 40;//in ms
			
			while(running){

				now = System.currentTimeMillis();
				next = now + delta;
				
				update();//60 mal die sekunde
				visual.render();
				
				while(now < next){
					//unbegrenzt oft in der sekunde
					now = System.currentTimeMillis();
					
				}
				
//				try { // gibt ab und so kleine ruckler
//					thread.sleep(1000/120,0);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				next = now + delta;
			}
		
	}
	
	public Core() throws IOException{
//		dM = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		tileSize = 5;
		//1600x900   16:9
		screenWidth = 1600;
		screenHeight = 900;
		
		resolutionX = screenWidth;
		resolutionY = screenHeight;
		
		frame = new JFrame("Invasion der Dingsis");
		
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
		running = true;
		thread = new Thread(this,"Frame"); 
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){//tick
		keyboard.update();
		handleMovement();
		
		for(int i=0;i<data.length;i++){//clear
			data[i]=0;
		}
		
		for(Entity e:entities){//alle entities werden in data eingeschrieben
			load(e);
		}
	}
	
	private void handleMovement(){//provisorisch
		long now = System.currentTimeMillis();
		
		int movF = 2;
		
		int orientation = entities.get(0).getRotation();
		
		int x = 0;
		int y = 0;
		if(keyboard.up){
			if(orientation == 0){
				y-=movF;
			}else if(orientation == 1){
				y-=movF;
				x+=movF;
			}else if(orientation == 2){
				x+=movF;
			}else if(orientation == 3){
				y+=movF;
				x+=movF;
			}else if(orientation == 4){
				y+=movF;
			}else if(orientation == 5){
				y+=movF;
				x-=movF;
			}else if(orientation == 6){
				x-=movF;
			}else if(orientation == 7){
				x-=movF;
				y-=movF;
			}
		}
		if(keyboard.down){
			if(orientation == 0){
				y+=movF;
			}else if(orientation == 1){
				y+=movF;
				x-=movF;
			}else if(orientation == 2){
				x-=movF;
			}else if(orientation == 3){
				y-=movF;
				x-=movF;
			}else if(orientation == 4){
				y-=movF;
			}else if(orientation == 5){
				y-=movF;
				x+=movF;
			}else if(orientation == 6){
				x+=movF;
			}else if(orientation == 7){
				x+=movF;
				y+=movF;
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
