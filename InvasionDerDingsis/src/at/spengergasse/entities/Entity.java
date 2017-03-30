package at.spengergasse.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Entity {
	
	private int x,y;
	
	private int[] shape;
	
	private int width,height;
	
	private int simpleWidth,simpleHeight;
	
	public Entity(String pathShape,int x,int y,int tileSize) throws IOException{
		
		this.x=x;
		this.y=y;
		
		BufferedReader reader = new BufferedReader(new FileReader(pathShape));
		
		simpleWidth =Integer.parseInt(reader.readLine());
		simpleHeight =Integer.parseInt(reader.readLine());
		
		width=simpleWidth*tileSize;
		height=simpleHeight*tileSize;
		
		shape = new int[width*height*tileSize*tileSize];
		
		for(int i=0;i<simpleHeight;i++){//TODO nicht optimal ?
			String[] row = reader.readLine().split(";");
			for(int k = 0;k<tileSize;k++){
				for(int e=0;e<simpleWidth;e++){
					for(int o=0;o<tileSize;o++){
						shape[e *tileSize + (i*tileSize)*width +o+k*width ] = Integer.parseUnsignedInt(row[e], 16);
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
		return shape;
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
}
