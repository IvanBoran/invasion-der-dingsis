package at.spengergasse.visual;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Visual extends Canvas{
	
	private BufferedImage image;
	private int[] pixel;
	
	private int[] data;
	
	public Visual(int resolutionX,int resolutionY,int screenWidth,int screenHeight,int[] data){
		this.data=data;
		
		image = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_RGB);
		pixel = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		setPreferredSize(new Dimension(screenWidth,screenHeight));
		
	}
	
	public void render(){
		for(int i = 0;i<data.length;i++){ 
			pixel[i] = data[i];
		}
		
//		Graphics2D g = (Graphics2D)getGraphics();
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0,getWidth(),getHeight(), null);
		g.dispose();
	}

}
