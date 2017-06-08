package at.spengergasse.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles the Input while the game is running.
 */
public class Keyboard implements EventHandler<KeyEvent> {

	public boolean up, down, left, right, space, s, d, f,esc;

	/**
	 * Handles the Input while the game is running.
	 * 
	 * @param event the KeyEvent which was done
	 */
	@Override
	public void handle(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			if (event.getCode() == KeyCode.UP)
				up = true;
			else if (event.getCode() == KeyCode.DOWN)
				down = true;
			else if (event.getCode() == KeyCode.LEFT)
				left = true;
			else if (event.getCode() == KeyCode.RIGHT)
				right = true;
			else if (event.getCode() == KeyCode.SPACE)
				space = true;
			else if (event.getCode() == KeyCode.S)
				s = true;
			else if (event.getCode() == KeyCode.D)
				d = true;
			else if (event.getCode() == KeyCode.F)
				f = true;
			else if (event.getCode() == KeyCode.ESCAPE)
				esc = true;
		} else {
			if (event.getCode() == KeyCode.UP)
				up = false;
			else if (event.getCode() == KeyCode.DOWN)
				down = false;
			else if (event.getCode() == KeyCode.LEFT)
				left = false;
			else if (event.getCode() == KeyCode.RIGHT)
				right = false;
			else if (event.getCode() == KeyCode.SPACE)
				space = false;
			else if (event.getCode() == KeyCode.S)
				s = false;
			else if (event.getCode() == KeyCode.D)
				d = false;
			else if (event.getCode() == KeyCode.F)
				f = false;
			else if (event.getCode() == KeyCode.ESCAPE)
				esc = false;
		}
	}
	
	/**
	 * Cleans esc if you switch the Scene.
	 */
	public void clean(){
		esc=false;
	}

}
