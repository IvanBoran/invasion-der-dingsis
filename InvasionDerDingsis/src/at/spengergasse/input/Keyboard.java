package at.spengergasse.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		for(int i = 0;i < keys.length;i++) {
			if(keys[i]){
				System.out.println("KEY: "+ i);
			} 
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		try{
			keys[k.getKeyCode()]= true;
//			System.out.println("KEY: "+ k);
			}
			catch(IndexOutOfBoundsException e){
				System.out.println("KEY : --");
			}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		try{
			keys[k.getKeyCode()]= false;
			}
			catch(IndexOutOfBoundsException e){
				System.out.println("KEY : --");
			}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
