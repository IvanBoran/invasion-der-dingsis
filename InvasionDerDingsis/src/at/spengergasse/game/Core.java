package at.spengergasse.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;

import javax.swing.JFrame;

import at.spengergasse.entities.Entity;
import at.spengergasse.entities.TestCollisionHandler;
import at.spengergasse.input.Keyboard;
import at.spengergasse.visual.Visual;

public class Core implements Runnable{

	private final int TICK_RATE = 60;// Die Tickrate mit der der Gameloop läuft in nanosekunden (1 sec = 1000000000 ns)

	private JFrame frame;// Das eigentliche Fenster in dem sich alles abspielt, wird jedoch vll aus dieser Klasse ausgelagert werden müssen wenn später ein Menü dazu kommt das im selben Fenster wie das Spiel stattfinden soll
	private Visual visual;// Die Canvas("Leinwand) Klasse die benutzt wird um alle Elemenete darauf zu rendern
	
	private Keyboard keyboard;// Der KeyListener der überall im Spiel benutzt wird
	
	private int[] data;// Im data Array sind alle Pixel in Form von RGB Farbwerten vertreten (RGB -> 0xRRGGBB (hexadecimal -> 0xff0000 ist z.B pures Rot))
	private int[] collisionMap;// Mithilfe der collisionMap wird später die Hitdetection umgesetzt
	
	//private DisplayMode dM; // DisplayMode wird vielleicht später für Fullscreen gebraucht und wird deswegen hier hinterlassen als Erinnerung
	
	private int screenWidth,screenHeight;// Die Größe des Fensters
	private final int resolutionX,resolutionY;// Die Auflösung des Dargestelltem im Fenster aber ACHTUNG darf nicht größer sein als die Größe da sonst Pixel verschluckt werden und es zu verformungen kommen kann
	
	private ArrayList<Entity> entities;// Hier werden alle Entities vertreten sein die dann auch direkt hier rauß gerendert werden mit einer Schleife die in der Methode update implementiert ist (ein Entity ist alles was "lebt/sich bewegt" z.B Spieler,Gegner oder Geschosse
	
	private final int INDEX_PLAYER = 0;// Wichtig die ID vom Player ist 1
	
	private int tileSize;// Größe der Kacheln/Pixel der gerenderten Entities

	private MovementHandler movementHandler;// Abstrakte super-Klasse von allen MovementHandlern damit man verschiedene benutzen kann um z.B in verschiedenen Spielmodie verschiedene Tasten belegen zu können

	private long rot; // Der Timer für die Rotation des Spielers damit das drehen kontrollierbar wird
	private final long  TIMER_ROT = 150;// Die Zeitdifferenz zwischen einmal rotieren und dem nächsten mal in millisekunden (1 sec = 1000 ms)

	private final ScheduledExecutorService scheduler =    // Wird für den Gameloop benutzt, unten wird sie genauer erklärt
			Executors.newSingleThreadScheduledExecutor();

	@Override
	public void run() { // run wird benötigt dadurch das Core das Interface Runnable hat, das Interface wird benötigt damit man den Gameloop benutzen kann mit dem scheduler
		update();// Ein "Tick" / Es werden alle Positionen geupdated und alle Entities in data mit ihren neuen Positionen geladen

		visual.render();// Hier werden alle Daten aus data in das pixel Array von Visual geladen und dann gerendert -> siehe Visual

	}

	public Core() throws IOException{
		//dM = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode(); //Wie oben beschrieben nur eventuell gebraucht

		tileSize = 5;
		
		screenWidth = 1600;
		screenHeight = 900;

		resolutionX = screenWidth;
		resolutionY = screenHeight;

		frame = new JFrame("Invasion der Dingsis");

		movementHandler = new TestMovement();//TestMovement ist ein movementHandler der erstmal zum Testen benutzt wird

		keyboard = new Keyboard();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Es wird das Standard verfahren gewählt was passieren soll wenn man versucht das Fenster zu schließen
		frame.setSize(screenWidth, screenHeight);

		entities = new ArrayList<Entity>();//Dadurch dass der Spieler zuerst erstellt wird kommt er auf Index 
		entities.add(new Entity("shapeTest",resolutionX/2, resolutionX/2, tileSize,new TestCollisionHandler(entities)));//Momentan wird hier der Spieler erstellt damit man das Spiel testen kann

		data = new int[resolutionX*resolutionY];

		collisionMap = new int[resolutionX*resolutionY];

		visual = new Visual(resolutionX, resolutionY,screenWidth,screenHeight, data);//Das visual Objekt wird mit der data Referenz erstellt und das sorgt dafür das man in Core nur data verändern muss und visual das dann direkt auslesen kann -> siehe Visual

		visual.addKeyListener(keyboard);
		frame.add(visual);

		frame.setResizable(false);

		frame.setVisible(true);

		frame.pack();

		start();// Es wird start am ende des Konstruktors aufgerufen damit alles oben erstmal laden kann und dann erst angefangen wird zu Rendern
	}


	public synchronized void start(){//stop und start haben synchronized damit die zwei Methonden nicht miteinander in Konflikt kommen
		final ScheduledFuture<?> gameLoop = scheduler.scheduleAtFixedRate(this, 0, 1000000000/TICK_RATE, NANOSECONDS);//Hier wird der Game Loop gestartet mit der oben definierten TICK_RATE in nanosekunden (1 sec = 1000000000 ns)
	}

	public synchronized void stop(){
		frame.dispose();
		scheduler.shutdown();//Hier wird vielleicht später ExceptionHandling benötigt
	}
	
	private void update(){//Wird TICK_RATE mal in der Sekunde aufgerufen vom GameLoop
		
		keyboard.update();//Der KeyListener keyboard fragt die Tasten ab
		movementHandler.handleMovement();//Hier wird die Position vom Spieler geupdated aufgrund von Tastatureingaben
		detectCollision();//TODO Unsicher - Muss vielleicht woanders hin verschoben werden

		for(int i=0;i<data.length;i++){//In dieser Schleife wird das data Array geleert damit alles neu darauf gerendert werden kann ohne überschneidungen mit dem letzten Frame zu haben
			data[i]=0xaaaa00;//Momentan die Farbe Gelb 
		}

		for(Entity e:entities){//Es werden alle Entities neu in data geladen mithilfe der Methode load
			load(e);
		}
	}

	private void load(Entity entity){//Ruft alle Informationen vom jeweiligen Entity auf und lädt es so auf die richtige Position im data Array
		int[] shape = entity.getShape();

		int width = entity.getWidth();
		int heigth = entity.getHeight();

		int x = entity.getX();
		int y = entity.getY();
		
		int id = entity.getId();

		for(int posY = 0;posY<heigth;posY++){
			for(int posX=0;posX<width;posX++){
				if(shape[posX+posY*width]!= 0){
					data[x + y * resolutionX + posX + posY * resolutionX] = shape[posX+posY*width];
					
					collisionMap[x + y * resolutionX + posX + posY * resolutionX] = id;//TODO Hitdetection - Es wird bei jedem Tick auf Hits überprüft werden müssen aber nicht innerhalb von load das wird eine neue Methode die in update kommt
				}
			}
		}
	}
	
	private void detectCollision(){//TODO Unsicher Hitdetection - Erster entwurf
		// Hits mit Entities müssen gehandelt werden und mit dem Rand -> kollision mit dem Rand = -1 / Der Spieler wird mit 1 eingetragen
		for(int i=0;i<collisionMap.length;i++){//TODO Grober Gedanke -> nicht vergessen einen Abstand zu berücksichtigen
			if(collisionMap[i]!=0){
				int firstEntity = collisionMap[i];
				int secondEntity = 0;
				for(int e=i+1;e<collisionMap.length && secondEntity==0;e++){
					if(collisionMap[e]!=0){
						secondEntity = collisionMap[e];
					}
				}
			}
		}
	}

	private abstract class MovementHandler{ // MovementHandler wie oben beschrieben wird dazu benutzt später verschiedene Tastaturlayouts benutzen zu können
		public abstract void handleMovement();
	}

	private class TestMovement extends MovementHandler{

		@Override
		public void handleMovement() {
			long now = System.currentTimeMillis();

			int moveFactor = 5;

			int orientation = entities.get(INDEX_PLAYER).getRotation();

			int x = 0;
			int y = 0;

			if(keyboard.up){
				if(orientation == 0){
					y-=moveFactor+2;
				}else if(orientation == 1){
					y-=moveFactor;
					x+=moveFactor;
				}else if(orientation == 2){
					x+=moveFactor+2;
				}else if(orientation == 3){
					y+=moveFactor;
					x+=moveFactor;
				}else if(orientation == 4){
					y+=moveFactor+2;
				}else if(orientation == 5){
					y+=moveFactor;
					x-=moveFactor;
				}else if(orientation == 6){
					x-=moveFactor+2;
				}else if(orientation == 7){
					x-=moveFactor;
					y-=moveFactor;
				}
			}
			if(keyboard.down){
				if(orientation == 0){
					y+=moveFactor+2;
				}else if(orientation == 1){
					y+=moveFactor;
					x-=moveFactor;
				}else if(orientation == 2){
					x-=moveFactor+2;
				}else if(orientation == 3){
					y-=moveFactor;
					x-=moveFactor;
				}else if(orientation == 4){
					y-=moveFactor+2;
				}else if(orientation == 5){
					y-=moveFactor;
					x+=moveFactor;
				}else if(orientation == 6){
					x+=moveFactor+2;
				}else if(orientation == 7){
					x+=moveFactor;
					y+=moveFactor;
				}
			}

			if(keyboard.right && now > rot){
				entities.get(INDEX_PLAYER).rotate(1);
				rot=System.currentTimeMillis()+TIMER_ROT;
			}

			if(keyboard.left && now > rot ){
				entities.get(INDEX_PLAYER).rotate(-1);
				rot=System.currentTimeMillis()+TIMER_ROT;
			}

			entities.get(INDEX_PLAYER).move(x, y);
		}
	}
}
