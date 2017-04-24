package at.spengergasse.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{//Siehe Java Docs - KeyListener

	private boolean[] keys = new boolean[250];
	public boolean up, down, left, right,f11;

	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		f11 = keys[KeyEvent.VK_F11];

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
