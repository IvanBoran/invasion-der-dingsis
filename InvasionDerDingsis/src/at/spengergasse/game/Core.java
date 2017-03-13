package at.spengergasse.game;

import javax.swing.JFrame;

public class Core implements Runnable{
	
	private int[] data;
	private int[] collisionMap;
	
	private Thread thread;
	
	private JFrame frame;
	
	private int screenWidth,screenHeight;
	private int resolutionX,resolutionY;
	
	private boolean running = false;

	@Override
	public void run() {
		
		while(running){
			
		}
		
	}
	
	public Core(){
		frame = new JFrame("Invasion der Dingsis");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
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

}
