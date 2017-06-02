package at.spengergasse.input;

import at.spengergasse.game.Core;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuKeyboard implements EventHandler<KeyEvent> {

	private Core core;

	public MenuKeyboard(Core core) {
		this.core = core;
	}

	@Override
	public void handle(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			if (event.getCode() == KeyCode.UP)
				core.menu(-1);
			else if (event.getCode() == KeyCode.DOWN)
				core.menu(1);
			else if (event.getCode() == KeyCode.ENTER) {
				core.enter();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				core.escape();
			}
		} else {
			// release
		}
	}
}
