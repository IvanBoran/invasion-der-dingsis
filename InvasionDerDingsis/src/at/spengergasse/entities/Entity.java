package at.spengergasse.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Entity {
	
	private int x,y;
	
	private int[] shape;
	
	private int width,heigth;
	
	public Entity(String pathShape,int width,int height,int x,int y,int tileSize) throws IOException{
		
		shape = new int[width*height];
		
		this.x=x;
		this.y=y;
		
		this.width=width;
		this.heigth=height;
		
		BufferedReader reader = new BufferedReader(new FileReader(pathShape));
		
		for(int i=0;i<height;i++){
			String[] row = reader.readLine().split(";");
			for(int e=0;e<width;e++){
				shape[e + i*width] = Integer.parseUnsignedInt(row[e], 16);
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
		return heigth;
	}
}
