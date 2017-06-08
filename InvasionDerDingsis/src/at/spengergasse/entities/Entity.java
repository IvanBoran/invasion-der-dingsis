package at.spengergasse.entities;

/**
 * Entities are every moving Object in the game (Player, enemies, shots).
 * The Entity class is the super class of SimpleEntity and AdvancedEntity.
 */
public abstract class Entity {

	private static int countId = 1;
	protected final int ID;

	protected int x, y;

	protected boolean dead;

	protected int width, height;

	/**
	 * Constructs a new Entity.
	 * 
	 * @param sim determines whether it is a SimpleEntity (-1) or a AdvancedEntity (1)
	 * @param x  the X Coordinate (coordinate origin is the upon left corner)
	 * @param y the Y Coordinate (coordinate origin is the upon left corner)
	 */
	public Entity(int sim, int x, int y) {
		this.ID = countId++ * sim;
		this.x = x;
		this.y = y;
	}

	/**
	 * returns the ID.
	 * 
	 * @return the ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * returns the X Coordinate.
	 * 
	 * @return the X Coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * returns the Y Coordinate.
	 * 
	 * @return the Y Coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * returns true if the Entity is dead; false if the Entity is alive.
	 *
	 * @return true if the Entity is dead; false if the Entity is alive
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * returns the width.
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * returns the height.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * returns the Shape.
	 * 
	 * @return the Shape
	 */
	abstract public int[] getShape();

	/**
	 * set dead to true.
	 */
	public void died() {
		dead = true;
	}

	/**
	 * returns the hashCode.
	 * 
	 * @return the hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

}
