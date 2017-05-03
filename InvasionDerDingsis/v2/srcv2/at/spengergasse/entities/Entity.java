package at.spengergasse.entities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

public class Entity {

	private static int id;//Der Zähler damit jedes Entity eine eigene U_ID bekommt
	
	private final int U_ID;//Bei jedem Entity eindeutig und wird gebraucht um die verschiedenen Entities zu indentifizieren
	
	private int x,y;

	private int rotation;//Der Grad der Rotation in 45° Schritten

	private int[][] shape;//Die Textur der Entity in jeder möglichen rotierten Position

	private int width,height;

	private int simpleWidth,simpleHeight;//Die "einfache" Breite und Höhe dadurch das aus dennen dann mithilfe von tileSize die Textur hochgerechnet wird

	public Entity(String pathShape,int x,int y,int tileSize) throws IOException{

		U_ID = id++;

		this.x=x;
		this.y=y;

		BufferedReader reader = new BufferedReader(new FileReader(pathShape));//Siehe Java Docs - BufferdReader

		simpleWidth =Integer.parseInt(reader.readLine());
		simpleHeight =Integer.parseInt(reader.readLine());

		width=simpleWidth*tileSize+tileSize*4;
		height=simpleHeight*tileSize+tileSize*4;

		shape = new int[8][width*height];

		for(int i=0;i<simpleHeight;i++){ // In den nächsten Schleifen werden die Texturen hochgerechnet

			String[] row = reader.readLine().split(";");

			for(int k = 0;k<tileSize;k++){
				for(int e=0;e<simpleWidth;e++){
					for(int o=0;o<tileSize;o++){//+ (tileSize/2) *5+1
						shape[0][e *tileSize + (i*tileSize)*width +o+k*width + (tileSize*2)*width + tileSize*2] = Integer.parseUnsignedInt(row[e], 16);
					}
				}
			}
		}

		for(int l = 1;l<8;l++){
			BufferedImage unrotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			unrotatedImage.setRGB(0, 0, width, height, shape[0], 0, width);

			BufferedImage rotatedImage = new BufferedImage(unrotatedImage.getWidth(), unrotatedImage.getHeight(), unrotatedImage.getType());
			AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(45*l), (unrotatedImage.getWidth()) / 2, (unrotatedImage.getHeight()) / 2 );
			Graphics2D g = (Graphics2D) rotatedImage.getGraphics();

			g.setTransform(at);
			g.drawImage(unrotatedImage, 0, 0, null);
			g.dispose(); 

			rotatedImage.getRGB(0, 0, width, height, shape[l], 0, width);

			for(int k=0;k<shape[0].length;k++){
				if(shape[l][k] == -16777216){
					shape[l][k]=0;
				}
			}
		}
	}
	
	public void update(){
		
	}

	public void move(int xChange,int yChange){
		x+=xChange;
		y+=yChange;
	}

	public int[] getShape(){
		return shape[rotation];
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getRotation(){
		return rotation;
	}

	public int getId(){
		return U_ID;
	}

	public void rotate(int direction){//0 - 7		
		rotation+=direction;
		if(rotation==-1)
			rotation=7;
		else if(rotation==8)
			rotation=0;
	}

}
