package at.spengergasse.entities;

public abstract class Entity {

	private static int countId = 1;
	protected final int ID;

	protected int x, y;

	protected boolean dead;

	protected int width, height;

	public Entity(int sim, int x, int y) {
		this.ID = countId++ * sim;
		this.x = x;
		this.y = y;
	}

	public int getID() {
		return ID;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isDead() {
		return dead;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	abstract public int[] getShape();

	public void died() {
		dead = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

}
