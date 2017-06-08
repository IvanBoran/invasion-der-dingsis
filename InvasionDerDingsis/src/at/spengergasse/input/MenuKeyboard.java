package at.spengergasse.input;

import at.spengergasse.game.Core;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles the Input while the menu is running.
 */
public class MenuKeyboard implements EventHandler<KeyEvent> {

	private Core core;

	/**
	 * Constructs a new MenuKeyboard.
	 * @param core
	 */
	public MenuKeyboard(Core core) {
		this.core = core;
	}

	/**
	 * Handles the Input while the menu is running.
	 * 
	 * @param event the KeyEvent which was done
	 */
	@Override
	public void handle(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.RIGHT)
				core.menu(-1);
			else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT)
				core.menu(1);
			else if (event.getCode() == KeyCode.ENTER) {
				core.enter();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				core.escape();
			}
		}
	}
}
