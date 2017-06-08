package at.spengergasse.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * SimpleEntities are every shots.
 * The SimpleEntity class is a subclass of Entity.
 */
public class SimpleEntity extends Entity {

	private final int[] SHAPE;

	private int momentumX, momentumY;

	private int damage;

	private int modolX, modolY;

	private int updates = 0;
	
	private int aId;

	/**
	 * Constructs a new SimpleEntity.
	 * 
	 * @param x the X Coordinate (coordinate origin is the uppon left corner)
	 * @param y  the Y Coordinate (coordinate origin is the uppon left corner)
	 * @param shapePath the Path where the Shape is
	 * @param tilesize how big one Pixel is
	 * @param momentumX the momentum on the x axis 
	 * @param momentumY the momentum on the Y axis 
	 * @param modolX veer on the x axis 
	 * @param modolY veer on the y axis 
	 * @param damage the damage 
	 * @param aId the ID of the AdvancedEntity which shot this SimpleEntity
	 */
	public SimpleEntity(int x, int y, String shapePath, int tileSize, int momentumX, int momentumY, int modolX,
			int modolY, int damage, int aId) throws IOException {
		super(-1, x, y);
		
		this.aId = aId;

		this.modolX = modolX;
		this.modolY = modolY;

		this.damage = damage;

		this.momentumX = momentumX;
		this.momentumY = momentumY;

		BufferedReader reader = new BufferedReader(new FileReader(shapePath));

		int simpleWidth = Integer.parseInt(reader.readLine());
		int simpleHeight = Integer.parseInt(reader.readLine());

		width = simpleWidth * tileSize + tileSize * 4;
		height = simpleHeight * tileSize + tileSize * 4;

		SHAPE = new int[width * height];

		for (int i = 0; i < simpleHeight; i++) { // In den nächsten Schleifen
													// werden die Texturen
													// hochgerechnet

			String[] row = reader.readLine().split(";");

			for (int k = 0; k < tileSize; k++) {
				for (int e = 0; e < simpleWidth; e++) {
					for (int o = 0; o < tileSize; o++) {// + (tileSize/2) *5+1
						SHAPE[e * tileSize + (i * tileSize) * width + o + k * width + (tileSize * 2) * width
								+ tileSize * 2] = (0xff << 24) + Integer.parseUnsignedInt(row[e], 16);
					}
				}
			}
		}
	}

	/**
	 * returns the damage.
	 * 
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * returns the aId.
	 * 
	 * @return the aId
	 */
	public int getAId() {
		return aId;
	}

	/**
	 * updates the position.
	 */
	public void update() {
		if (updates % modolX == 0) {
			x += momentumX;
		}
		if (updates % modolY == 0) {
			y += momentumY;
		}

		updates++;
	}

	/**
	 * returns the shape.
	 * 
	 * @return the shape
	 */
	@Override
	public int[] getShape() {
		return SHAPE;
	}

}
