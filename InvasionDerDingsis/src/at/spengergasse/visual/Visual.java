package at.spengergasse.visual;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Visual extends Canvas{
	
	private BufferedImage image;
	private int[] pixel;
	
	public Visual(int resolutionX,int resolutionY){
		image = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_RGB);
		pixel = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		
	}

}
