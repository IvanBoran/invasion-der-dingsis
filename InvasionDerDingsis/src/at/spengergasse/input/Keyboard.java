package at.spengergasse.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keyboard implements EventHandler<KeyEvent>{
	
	public boolean up,down,left,right;

	@Override
	public void handle(KeyEvent event) {
		if(event.getEventType()==KeyEvent.KEY_PRESSED){
			if(event.getCode()==KeyCode.UP)
				up=true;
			else if(event.getCode()==KeyCode.DOWN)
				down=true;
			else if(event.getCode()==KeyCode.LEFT)
				left=true;
			else if(event.getCode()==KeyCode.RIGHT)
				right=true;
		}else{
			if(event.getCode()==KeyCode.UP)
				up=false;
			else if(event.getCode()==KeyCode.DOWN)
				down=false;
			else if(event.getCode()==KeyCode.LEFT)
				left=false;
			else if(event.getCode()==KeyCode.RIGHT)
				right=false;
		}
	}

}
