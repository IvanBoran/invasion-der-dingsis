package at.spengergasse.entities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Entity {
	
	private static int id;
	
	private int myId;
	
	private int x,y;
	
	private int rotation;//rotation * 45�
	
	private int[][] shape;
	
	private int width,height;
	
	private int simpleWidth,simpleHeight;
	
	public Entity(String pathShape,int x,int y,int tileSize) throws IOException{
		
		myId = id++;
		
		this.x=x;
		this.y=y;
		
		BufferedReader reader = new BufferedReader(new FileReader(pathShape));
		
		simpleWidth =Integer.parseInt(reader.readLine());
		simpleHeight =Integer.parseInt(reader.readLine());
		
		width=simpleWidth*tileSize+tileSize*4;
		height=simpleHeight*tileSize+tileSize*4;//+tileSize*5+1
				
		shape = new int[8][width*height];
		
		reader.mark(simpleHeight*simpleWidth);
		
		for(int i=0;i<simpleHeight;i++){
			
			String[] row = reader.readLine().split(";");
			
			for(int k = 0;k<tileSize;k++){
				for(int e=0;e<simpleWidth;e++){
					
					for(int o=0;o<tileSize;o++){//+ (tileSize/2) *5+1
						shape[0][e *tileSize + (i*tileSize)*width +o+k*width + (tileSize*2)*width + tileSize*2] = Integer.parseUnsignedInt(row[e], 16);
					}
				}
			}
		}
		
		int[] pixels = shape[0];
		for(int l = 1;l<8;l++){
			BufferedImage unrotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
//			for(int f = 0; f < height; f++)
//			{
//				for(int g = 0; g < width;g++)
//				{
//					unrotatedImage.setRGB(f, g, pixels[f + g * width]);
//				}
//			}
			
			unrotatedImage.setRGB(0, 0, width, height, pixels, 0, width);
			
			BufferedImage rotatedImage = new BufferedImage(unrotatedImage.getWidth(), unrotatedImage.getHeight(), unrotatedImage.getType());
			AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(45*l), (unrotatedImage.getWidth()) / 2, (unrotatedImage.getHeight()) / 2 );
			Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
			
			g.setTransform(at);
			g.drawImage(unrotatedImage, 0, 0, null);
			g.dispose(); 
			
			rotatedImage.getRGB(0, 0, width, height, shape[l], 0, width);
			
			for(int k=0;k<shape[0].length;k++){
				if(shape[l][k]==-16777216){
					shape[l][k]=0;
				}
			}
		}
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
		return myId;
	}
	
	public void rotate(int direction){//0 - 7		
		rotation+=direction;
		if(rotation==-1)
			rotation=7;
		else if(rotation==8)
			rotation=0;
	}

}
