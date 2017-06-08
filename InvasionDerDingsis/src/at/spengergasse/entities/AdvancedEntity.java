package at.spengergasse.entities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * AdvancedEntities are every Player and Enemies.
 * The AdvancedEntity class is a subclass of Entity.
 */
public class AdvancedEntity extends Entity {

	private final int type;

	private int health;

	private final int[][] SHAPE;

	private int rotation;

	private int tileSize;
	
	private boolean hit;

	/**
	 * Constructs a new AdvancedEntity.
	 * 
	 * @param x the X Coordinate (coordinate origin is the upon left corner)
	 * @param y the Y Coordinate (coordinate origin is the upon left corner)
	 * @param shapePath the Path where the Shape is
	 * @param tilesize how big one Pixel is
	 * @param health the hit points (HP)
	 * @param type the Type (0 = Player; 1 = Enemy lvl.1; 2 = Enemy lvl.2; 3 = Enemy lvl.3; 4 = Boss)
	 */
	public AdvancedEntity(int x, int y, String shapePath, int tileSize, int health, int type)
			throws NumberFormatException, IOException {
		super(1, x, y);

		this.health = health;
		this.type = type;

		BufferedReader reader = new BufferedReader(new FileReader(shapePath));

		this.tileSize = tileSize;

		int simpleWidth = Integer.parseInt(reader.readLine());
		int simpleHeight = Integer.parseInt(reader.readLine());

		width = simpleWidth * tileSize + tileSize * 4;
		height = simpleHeight * tileSize + tileSize * 4;

		SHAPE = new int[8][width * height];

		for (int i = 0; i < simpleHeight; i++) { 

			String[] row = reader.readLine().split(";");

			for (int k = 0; k < tileSize; k++) {
				for (int e = 0; e < simpleWidth; e++) {
					for (int o = 0; o < tileSize; o++) {// + (tileSize/2) *5+1
						SHAPE[0][e * tileSize + (i * tileSize) * width + o + k * width + (tileSize * 2) * width
								+ tileSize * 2] = (0xff << 24) + Integer.parseUnsignedInt(row[e], 16);
					}
				}
			}
		}

		for (int l = 1; l < 8; l++) {
			BufferedImage unrotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			unrotatedImage.setRGB(0, 0, width, height, SHAPE[0], 0, width);

			BufferedImage rotatedImage = new BufferedImage(unrotatedImage.getWidth(), unrotatedImage.getHeight(),
					unrotatedImage.getType());
			AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(45 * l),
					(unrotatedImage.getWidth()) / 2, (unrotatedImage.getHeight()) / 2);
			Graphics2D g = (Graphics2D) rotatedImage.getGraphics();

			g.setTransform(at);
			g.drawImage(unrotatedImage, 0, 0, null);
			g.dispose();

			rotatedImage.getRGB(0, 0, width, height, SHAPE[l], 0, width);

			for (int k = 0; k < SHAPE[0].length; k++) {
				if (SHAPE[l][k] == -16777216) {
					SHAPE[l][k] = 0;
				}
			}
		}
		for (int k = 0; k < SHAPE[0].length; k++) {
			if (SHAPE[0][k] == -16777216) {
				SHAPE[0][k] = 0;
			}
		}
	}

	/**
	 * Makes a new SimpleEntity and shoots.
	 * 
	 * 
	 * @param xOffset how far the shot spawns on the x axis from the coordinate origin of the AdvancedEntity
	 * @param yOffset how far the shot spawns on the y axis from the coordinate origin of the AdvancedEntity
	 * @param speed how fast it travels
	 * @param damage the damage
	 * @param momentumX the momentum on the x axis 
	 * @param momentumY the momentum on the Y axis 
	 * @param modolX veer on the x axis 
	 * @param modolY veer on the y axis 
	 * @param shapePath the Path where the Shape is
	 */
	public SimpleEntity shoot(int xOffset, int yOffset, int speed, int damage, int momentumX, int momentumY,
			int modolX, int modolY, String pathShape) throws IOException {
		int x = 0, y = 0;
		int mX = momentumX, mY = momentumY;
		int xO = xOffset, yO = yOffset;
		int t = 0;

		switch (rotation) {
			case 0: {
				x = this.x + width / 2;
				y = this.y + height;
				mY += speed+2;
				break;
			}
			case 1: {
				x = this.x;
				y = this.y + height;
				mX -= speed;
				mY -= -speed;
				t = xO;
				xO = 0;
				yO = -this.height/4;
				break;
				
			}
			case 2: {
				x = this.x;
				y = this.y + height / 2;
				mX -= speed+2;
				t = xO;
				xO = yO + 10;
				yO = t;
				break;
			}
			case 3: {
				x = this.x;
				y = this.y;
				mX -= speed;
				mY -= speed;
				t = xO;
				xO = 0;
				yO = 0;
				break;
			}
			case 4: {
				x = this.x + width / 2;
				y = this.y;
				mY -= speed+2;
				yO += this.height/4;
				break;
			}
			case 5: {
				x = this.x + width;
				y = this.y;
				mX = speed;
				mY -= speed;
				t = xO;
				xO = -this.width/4;
				yO = 0;
				break;
			}
			case 6: {
				x = this.x + width;
				y = this.y + height / 2;
				mX += speed+2;
				t = xO;
				xO = yO;
				yO = t;
				break;
			}
			case 7: {
				x = this.x + width;
				y = this.y + height;
				mX -= -speed;
				mY -= -speed;
				t = xO;
				xO = -this.width/4;
				yO = -this.height/4;
				break;
			}
		}

		return new SimpleEntity(x + xO, y + yO, pathShape, tileSize, mX, mY, modolX, modolY, damage, this.ID);
	}
	
	/**
	 * Moves the AdvancedEntity.
	 * 
	 * @param x how far it travels on the x axis
	 * @param y how far it travels on the y axis
	 */
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Hits the AdvancedEntity with this damage.
	 * 
	 * @param damage how much damage 
	 */
	public void hit(int damage) {
		hit=true;
		health -= damage;
		if (health <= 0) {
			died();
		}
	}
	
	/**
	 * returns true if the Entity is hit; false if the Entity is not hit.
	 * 
	 * @return true if the Entity is hit; false if the Entity is not hit
	 */
	public boolean getHit(){
		return hit;
	}
	
	/**
	 * set hit.
	 * 
	 * @param hit is it hit or not
	 */
	public void setHit(boolean hit){
		this.hit=hit;
	}

	/**
	 * Rotates the AdvancedEntity by this direction .
	 * 
	 * @param direction the direction in which the AdvancedEntity rotate (>0 = right; <0 = left)
	 */
	public void rotate(int direction) {// 0 - 7
		rotation += direction;
		if (rotation == -1)
			rotation = 7;
		else if (rotation == 8)
			rotation = 0;
	}

	/**
	 * returns the Shape.
	 * 
	 * @return the shape
	 */
	@Override
	public int[] getShape() {
		return SHAPE[rotation];
	}

	/**
	 * returns the type (0 = Player; 1 = Enemy lvl.1; 2 = Enemy lvl.2; 3 = Enemy lvl.3; 4 = Boss).
	 * 
	 * @return type 
	 */
	public int getType() {
		return type;
	}

	/**
	 * returns the health.
	 * 
	 * @return the health
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * return rotation (0 = up; 1 = right up; 2 = right; 3 = right down; 4 = down; 5 = left down; 6 = left; 7 = left up).
	 * 
	 * @return rotation
	 */
	public int getRotation(){
		return rotation;
	}
}
