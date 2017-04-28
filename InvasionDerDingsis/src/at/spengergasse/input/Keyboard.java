package at.spengergasse.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{//Siehe Java Docs - KeyListener

	private boolean[] keys = new boolean[150];
	public boolean up, down, left, right;

	public void update(){
		up = keys[KeyEvent.VK_UP] ;
		down = keys[KeyEvent.VK_DOWN] ;
		left = keys[KeyEvent.VK_LEFT] ;
		right = keys[KeyEvent.VK_RIGHT] ;

		//for(int i = 0;i < keys.length;i++) {
		//	if(keys[i]){
		//		System.out.println("KEY: "+ i);
		//	} 
		//}
	}

	@Override
	public void keyPressed(KeyEvent k) {
			keys[k.getKeyCode()]= true;
			//System.out.println("KEY: "+ k);
	}

	@Override
	public void keyReleased(KeyEvent k) {
			keys[k.getKeyCode()]= false;
			//System.out.println("KEY: "+ k);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//System.out.println("KEY: Typed");
	}

}
