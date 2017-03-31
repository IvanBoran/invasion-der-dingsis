package at.spengergasse.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Entity {
	
	private static int id;
	
	private int myId;
	
	private int x,y;
	
	private int rotation;//rotation * 45°
	
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
		
		width=simpleWidth*tileSize;
		height=simpleHeight*tileSize;
		
		
		int diagonal = tileSize;//a²*b²=c²
		
		shape = new int[8][width*height];//*tileSize*tileSize
		reader.mark(simpleHeight*simpleWidth);
		for(int i=0;i<simpleHeight;i++){//TODO nicht optimal ?
			String[] row = reader.readLine().split(";");
			for(int k = 0;k<tileSize;k++){
				for(int e=0;e<simpleWidth;e++){
					for(int o=0;o<tileSize;o++){
						shape[0][e *tileSize + (i*tileSize)*width +o+k*width ] = Integer.parseUnsignedInt(row[e], 16);
					}
				}
			}
		}
		reader.reset();
		for(int i=0;i<simpleHeight;i++){//TODO nicht optimal ?
			String[] row = reader.readLine().split(";");
			for(int k = 0;k<tileSize;k++){
				for(int e=0;e<simpleWidth;e++){
					for(int o=0;o<tileSize;o++){
						shape[1][e *tileSize + (i*tileSize)*width +o+k*width ] = Integer.parseUnsignedInt(row[e], 16);
					}
				}
			}
		}
		
		reader.close();
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
	
	public void rotate(boolean rightElseLeft){//0 - 7
		int direction;
		if(rightElseLeft)
			direction = 1;
		else
			direction = -1;
		
		rotation+=direction;
		if(rotation==-1)
			rotation=7;
		else if(rotation==8)
			rotation=0;
	}
}
