package at.spengergasse.mode;

import java.io.IOException;
import java.util.ArrayList;

import at.spengergasse.enemyInput.EnemyInput;
import at.spengergasse.entities.AdvancedEntity;
import at.spengergasse.entities.Entity;
import at.spengergasse.entities.SimpleEntity;
import at.spengergasse.input.Keyboard;

/**
 * Handles the different gamemodes.
 * Handles the Entity placement, Entity movement, shots.
 */
public abstract class Mode {

	protected AdvancedEntity player;

	protected ArrayList<SimpleEntity> simpleEntities;
	protected ArrayList<AdvancedEntity> advancedEntities;

	protected int[] data;
	protected int[] collisionMap;

	protected int screenX, screenY;

	protected Keyboard keyboard;

	protected int difficulty;

	protected int updates;
	
	/**
	 * Constructs a new Mode.
	 * 
	 * @param screenX the width of the window
	 * @param screenY the height of the window
	 * @param data the Array, where the different Colors of the Pixels are saved
	 * @param keyboard the keyboard class which is used
	 * @param difficulty the level number
	 */
	public Mode(int screenX, int screenY, int[] data, Keyboard keyboard, int difficulty) {
		this.screenX = screenX;
		this.screenY = screenY;

		this.difficulty = difficulty;

		this.keyboard = keyboard;

		this.data = data;
		collisionMap = new int[screenX * screenY];

		simpleEntities = new ArrayList<>();
		advancedEntities = new ArrayList<>();
	}

	/**
	 * Loads the shape in the data Array.
	 * Loads the id in the collisionMap.
	 * 
	 * @param entity the Entity which will be loaded
	 */
	private void load(Entity entity) {// Ruft alle Informationen vom jeweiligen
										// Entity auf und lädt es so auf die
										// richtige Position im data Array
		int[] shape = entity.getShape();

		int id = entity.getID();

		int width = entity.getWidth();
		int heigth = entity.getHeight();

		int x = entity.getX();
		int y = entity.getY();

		if (x + width > screenX - 0 || y + heigth > screenY - 0 || x <= 0 || y <= 0) {
			entity.died();
			return;
		}

		for (int posY = 0; posY < heigth; posY++) {
			for (int posX = 0; posX < width; posX++) {
				if (shape[posX + posY * width] != 0) {
					data[x + y * screenX + posX + posY * screenX] = shape[posX + posY * width];
					collisionMap[x + y * screenX + posX + posY * screenX] = id;
				}
			}
		}
	}

	/**
	 * Looks for hits.
	 */
	private void check() {
		for (int i = 0; i < collisionMap.length - 1-screenX; i++) {
			if ((collisionMap[i] != 0 && collisionMap[i + 1] != 0) && collisionMap[i] != collisionMap[i + 1]) {
				hit(collisionMap[i], collisionMap[i + 1]);
			}
			else if ((collisionMap[i] != 0 && collisionMap[i + screenX] != 0) && collisionMap[i] != collisionMap[i + screenX]) {
				hit(collisionMap[i], collisionMap[i + screenX]);
			}
		}
	}

	/**
	 * Handles hits.
	 * 
	 * @param id1
	 * @param id2
	 */
	abstract protected void hit(int id1, int id2);

	/**
	 * Removes the dead Entities from the Arrays.
	 */
	private void clean() {

		while (true) {
			for (Entity e : simpleEntities) {
				if (e.isDead()) {
					simpleEntities.remove(e);
					break;
				}
			}
			for (Entity e : advancedEntities) {
				if (e.isDead()) {
					advancedEntities.remove(e);
					break;
				}
			}
			break;
		}
	}

	/**
	 * Updates the different Entities.
	 */
	public void update() {
		updates++;
		
		for (int i = 0; i < data.length; i++) {
			data[i] = 0xff000000;
			collisionMap[i] = 0;
		}
			updateEnemies();
			clean();
			
		for (SimpleEntity e : simpleEntities) {
			e.update();
			load(e);
		}

		handleInputs();

		for (AdvancedEntity e : advancedEntities) {
			load(e);
			e.setHit(false);
		}

		check();
	}
	
	/**
	 * Returns if the game is finished.
	 * 
	 * @return if the game is finished (1 = win; -1 = lose; 0 = unfinished)
	 */
	abstract public int finished();
	
	/**
	 * Updates the Enemies.
	 */
	abstract protected void updateEnemies();

	/**
	 * Handles the Inputs of the Player.
	 */
	abstract protected void handleInputs();

	/**
	 * Returns the index of the AdvancedEntity with this ID.
	 * 
	 * @param ID the ID of the AdvancedEntity
	 * @return the index of the AdvancedEntity
	 */
	protected int getAdvancedEntity(int ID) {
		for (int i = 0; i < advancedEntities.size(); i++) {
			if (advancedEntities.get(i).getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the index of the SimpleEntity with this ID.
	 * 
	 * @param ID the ID of the SimpleEntity
	 * @return the index of the SimpleEntity
	 */
	protected int getSimpleEntity(int ID) {
		for (int i = 0; i < simpleEntities.size(); i++) {
			if (simpleEntities.get(i).getID() == ID) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the Player Object.
	 * 
	 * @return the Player Object
	 */
	public AdvancedEntity getPlayer() {
		return player;
	}
	
	/**
	 * Returns the Boss Object.
	 * If there is no boss = null.
	 * 
	 * @return the Boss Object; no boss = null
	 */
	public AdvancedEntity getBoss(){
		if(advancedEntities.size() != 1 && advancedEntities.get(1).getType() == 4){
			return advancedEntities.get(1);
		}
		return null;
	}
}
