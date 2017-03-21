package at.spengergasse.visual;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Visual extends Canvas{
	
	private BufferedImage image;
	private int[] pixel;
	
	private int[] data;
	
	public Visual(int resolutionX,int resolutionY,int[] data){
		this.data=data;
		
		image = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_RGB);
		pixel = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		
	}
	
	public void render(){
		for(int i = 0;i<data.length;i++){
			pixel[i] = data[i];
		}
		
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}

}
