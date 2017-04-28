package at.spengergasse.visual;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Visual extends Canvas{

	private BufferedImage image;// Ein BufferdImage Objekt mit dem dann alles auf das Canvas Visual gerendert wird
	private int[] pixel;

	private int[] data;

	public Visual(int resolutionX,int resolutionY,int screenWidth,int screenHeight,int[] data){
		this.data=data;// Es wird kein "new" Array initialisiert sondern die Referenz aus Core benutzt die in den Parametern des Konstruktors hier vorhanden ist

		image = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_RGB);//Siehe Java Docs - BufferedImage
		pixel = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		setPreferredSize(new Dimension(screenWidth,screenHeight));//Diese Zeile sorgt dafür das vom Rahmen des Fensters die Größe nicht eingeschränkt wird

	}
	
	public void render(){// Hier werden die ganzen Daten erstmal ins pixel Array übertragen und dann mit drawImage auf Canvas gerendert
		for(int i = 0;i<data.length;i++){ 
			pixel[i] = data[i];
		}

		Graphics g = getGraphics();//Siehe Java Docs - Graphics
		g.drawImage(image, 0, 0,getWidth(),getHeight(), null);
		g.dispose();
	}

}
