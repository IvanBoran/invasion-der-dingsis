package at.spengergasse.entities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdvancedEntity extends Entity {

	private final int type;

	private int health;

	private final int[][] SHAPE;

	private int rotation;

	private int tileSize;
	
	private boolean hit;

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

	public SimpleEntity shoot(int xOffset, int yOffset, int speed, int damage, int momentumX, int momentumY,
			int modolX, int modolY, String pathShape) throws IOException {
		int x = 0, y = 0;
		int mX = momentumX, mY = momentumY;

		switch (rotation) {
		case 0: {
			x = this.x + width / 2;
			y = this.y;
			mY -= speed+2;
			break;
		}
		case 1: {
			x = this.x + width;
			y = this.y;
			mX += speed;
			mY += -speed;
			break;
		}
		case 2: {
			x = this.x + width;
			y = this.y + height / 2;
			mX += speed+2;
			break;
		}
		case 3: {
			x = this.x + width;
			y = this.y + height;
			mX += speed;
			mY += speed;
			break;
		}
		case 4: {
			x = this.x + width / 2;
			y = this.y + height;
			mY += speed+2;
			break;
		}
		case 5: {
			x = this.x;
			y = this.y + height;
			mX = -speed;
			mY += speed;
			break;
		}
		case 6: {
			x = this.x;
			y = this.y + height / 2;
			mX -= speed+2;
			break;
		}
		case 7: {
			x = this.x;
			y = this.y;
			mX += -speed;
			mY += -speed;
			break;
		}
		}

		return new SimpleEntity(x + xOffset, y + yOffset, pathShape, tileSize, mX, mY, modolX, modolY, damage, this.ID);
	}

	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void hit(int damage) {
		hit=true;
		health -= damage;
		if (health <= 0) {
			died();
		}
	}
	
	public boolean getHit(){
		return hit;
	}
	
	public void setHit(boolean hit){
		this.hit=hit;
	}

	public void rotate(int direction) {// 0 - 7
		rotation += direction;
		if (rotation == -1)
			rotation = 7;
		else if (rotation == 8)
			rotation = 0;
	}

	@Override
	public int[] getShape() {
		return SHAPE[rotation];
	}

	public int getType() {
		return type;
	}

	public double getHealth() {
		return health;
	}
	
	public int getRotation(){
		return rotation;
	}
}
