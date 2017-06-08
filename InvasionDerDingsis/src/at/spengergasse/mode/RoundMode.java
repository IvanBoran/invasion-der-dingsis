package at.spengergasse.mode;

import java.awt.Adjustable;
import java.io.IOException;
import java.util.Random;

import at.spengergasse.enemyInput.EnemyInput;
import at.spengergasse.enemyInput.RoundInput;
import at.spengergasse.entities.AdvancedEntity;
import at.spengergasse.input.Keyboard;

public class RoundMode extends Mode{
	
	private RoundInput enemyInput;
	private long[] timers;

	public RoundMode(int screenX, int screenY, int[] data, Keyboard keyboard, int difficulty,RoundInput enemyInput) throws NumberFormatException, IOException {
		super(screenX, screenY, data, keyboard, difficulty);
		
		this.enemyInput=enemyInput;
		
		player = new AdvancedEntity(screenX / 2, screenY/2, "src/entities/shapePlayer", 4, 10000, 0);
		advancedEntities.add(player);

		switch (difficulty) {
		case 1:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy1", 4, 100, 1));
			break;

		case 2:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(screenX/2, 100, "src/entities/shapeEnemy1", 4, 100, 1));
			advancedEntities.add(new AdvancedEntity(screenX/2, screenY-100, "src/entities/shapeEnemy1", 4, 100, 1));advancedEntities.get(advancedEntities.size()-1).rotate(4);
			break;

		case 3:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy2", 4, 200, 2));//klasse 2
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));
			break;

		case 4:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX/2, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX/2, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));advancedEntities.get(advancedEntities.size()-1).rotate(4);
			break;

		case 5:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy2", 4, 200, 2));//klasse 2 und 3 
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));
			break;
			
		case 6:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX/2, 100, "src/entities/shapeEnemy2", 4, 200, 2));
			advancedEntities.add(new AdvancedEntity(screenX/2, screenY-100, "src/entities/shapeEnemy2", 4, 200, 2));advancedEntities.get(advancedEntities.size()-1).rotate(4);
			break;
			
		case 7:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy3", 4, 300, 3));//klasse 3 
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(100, screenY-100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-100, "src/entities/shapeEnemy3", 4, 300, 3));
			break;
			
		case 8:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(100, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX/2, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX/2, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));advancedEntities.get(advancedEntities.size()-1).rotate(4);
			break;
			
		case 9:
			advancedEntities.add(new AdvancedEntity(100, 100, "src/entities/shapeEnemy3", 4, 300, 3));//klasse 3 
			advancedEntities.add(new AdvancedEntity(screenX - 100, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(100, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX-100, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(100+100, 100, "src/entities/shapeEnemy3", 4, 300,3));//klasse 3 
			advancedEntities.add(new AdvancedEntity(screenX - 100-100, 100, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(100+100, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));
			advancedEntities.add(new AdvancedEntity(screenX-100-100, screenY-200, "src/entities/shapeEnemy3", 4, 300, 3));
			break;
			
		case 10:
			advancedEntities.add(new AdvancedEntity(200, 100,"src/entities/shapeEnemy4", 6, 2000, 4));
			break;
		}
		
		timers = new long[advancedEntities.size()-1];
	}

	@Override
	protected void hit(int id1, int id2) {
		final int COLLISION_DAMAGE = 50;
		if (id1 < 0 && id2 < 0) {// Beide simple

		} else if (id1 > 0 && id2 > 0) {
			if(advancedEntities.get(getAdvancedEntity(id1)).getType() == 0 || advancedEntities.get(getAdvancedEntity(id2)).getType() == 0){
				advancedEntities.get(getAdvancedEntity(id1)).hit(COLLISION_DAMAGE);
				advancedEntities.get(getAdvancedEntity(id2)).hit(COLLISION_DAMAGE);
			}
		} else if (id1 < 0 || id2 < 0) {
			if (id1 < 0) {
				if(!advancedEntities.get(getAdvancedEntity(id2)).getHit() && (advancedEntities.get(getAdvancedEntity(id2)).getType() == 0 || advancedEntities.get(getAdvancedEntity(simpleEntities.get(getSimpleEntity(id1)).getAId())).getType() == 0)){
					advancedEntities.get(getAdvancedEntity(id2)).hit(simpleEntities.get(getSimpleEntity(id1)).getDamage());
					simpleEntities.get(getSimpleEntity(id1)).died();
				}
			} else {
				if(!advancedEntities.get(getAdvancedEntity(id1)).getHit() && (advancedEntities.get(getAdvancedEntity(id1)).getType() == 0 || advancedEntities.get(getAdvancedEntity(simpleEntities.get(getSimpleEntity(id2)).getAId())).getType() == 0)){
					advancedEntities.get(getAdvancedEntity(id1)).hit(simpleEntities.get(getSimpleEntity(id2)).getDamage());
					simpleEntities.get(getSimpleEntity(id2)).died();
				}
			}
		}
	}

	@Override
	public int finished() {
		if(player.isDead()){
			return -1;
		}else if(advancedEntities.size()<=1){
			return 1;
		}
		return 0;
	}

	long timeS, timeF, timeD;
	int movementSpeed = 5;

	int shotDelayS = 500;
	int shotDelayD = 20;
	int shotDelayF = 150;

	int xOffset = -9;
	int yOffset = -9;
	
	long timerRotation;
	int timerDelta=150;
	
	@Override
	protected void handleInputs() {
		int orientation = player.getRotation();
		
		if(keyboard.esc){
			player.died();
		}
		int x=0;
		int y=0;
		
		if(keyboard.left){
			if(timerRotation+timerDelta<System.currentTimeMillis()){
				player.rotate(-1);
				timerRotation=System.currentTimeMillis();
			}
		}
		if(keyboard.right){
			if(timerRotation+timerDelta<System.currentTimeMillis()){
				player.rotate(1);
				timerRotation=System.currentTimeMillis();
			}
		}
		
		if(keyboard.up){
			if(orientation == 0){
				y+=movementSpeed+2;
			}else if(orientation == 1){
				y+=movementSpeed;
				x-=movementSpeed;
			}else if(orientation == 2){
				x-=movementSpeed+2;
			}else if(orientation == 3){
				y-=movementSpeed;
				x-=movementSpeed;
			}else if(orientation == 4){
				y-=movementSpeed+2;
			}else if(orientation == 5){
				y-=movementSpeed;
				x+=movementSpeed;
			}else if(orientation == 6){
				x+=movementSpeed+2;
			}else if(orientation == 7){
				x+=movementSpeed;
				y+=movementSpeed;
			}
		}
		
		if(keyboard.down){
			if(orientation == 0){
				y-=movementSpeed+2;
			}else if(orientation == 1){
				y-=movementSpeed;
				x+=movementSpeed;
			}else if(orientation == 2){
				x+=movementSpeed+2;
			}else if(orientation == 3){
				y+=movementSpeed;
				x+=movementSpeed;
			}else if(orientation == 4){
				y+=movementSpeed+2;
			}else if(orientation == 5){
				y+=movementSpeed;
				x-=movementSpeed;
			}else if(orientation == 6){
				x-=movementSpeed+2;
			}else if(orientation == 7){
				x-=movementSpeed;
				y-=movementSpeed;
			}
		}
		
		if(player.getX()+player.getWidth()+ x +3> screenX){
			x =screenX-player.getX()-player.getWidth()-3;
		}else if(player.getX() + x -3< 0){
			x = - player.getX()+3;
		}
		
		if(player.getY()+player.getHeight()+ y > screenY){
			y=screenY-player.getY()-player.getHeight();
		}else if(player.getY() + y <= 0){
			y= -player.getY()+1;
		}
		
		player.move(x, y);

		if (keyboard.s) {
			try {
					if (System.currentTimeMillis() > timeS) {
						simpleEntities.add(
								player.shoot(xOffset, yOffset, 7, 40, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						timeF = System.currentTimeMillis() + shotDelayF;
						timeD = System.currentTimeMillis() + shotDelayD;
						timeS = System.currentTimeMillis() + shotDelayS;
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (keyboard.d) {
			try {
				if (!player.isDead()) {
					if (System.currentTimeMillis() > timeD) {
						simpleEntities.add(player.shoot(xOffset, yOffset, 6, 5, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));
						timeF = System.currentTimeMillis() + shotDelayD;
						timeD = System.currentTimeMillis() + shotDelayD;
						timeS = System.currentTimeMillis() + shotDelayD;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (keyboard.f) {
			try {
					if (System.currentTimeMillis() > timeF) {
						simpleEntities.add(player.shoot(xOffset, yOffset, 8, 20, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
						timeF = System.currentTimeMillis() + shotDelayF;
						timeD = System.currentTimeMillis() + shotDelayD;
						timeS = System.currentTimeMillis() + shotDelayS;
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	int delay = 650;
	int last = 0;
	
	int idOffset = 30;
	
	@Override
	protected void updateEnemies() {
		int xP = player.getX();
		int yP = player.getY();
		
		int movementSpeed = 3;
		
		for(int e=1;e<advancedEntities.size();e++){
			int x = advancedEntities.get(e).getX();
			int y = advancedEntities.get(e).getY();
			int rotation = advancedEntities.get(e).getRotation();
			
			int bossFRate = 0;
			
			int id = e;
			
			int dX=0;
			int dY=0;
			int dR=0;
			
			int vX=xP-x;
			int vY=yP-y;
			
			switch(rotation){//TODO abstand halten
			
			case 0:
				if(vX<=150 && vX>=-150){
					if(vY<250+id*idOffset){
						dY-=movementSpeed*2;
					}else if(vY>350+id*idOffset){
						dY+=movementSpeed*2;
					}
					
					if(vX<=movementSpeed && vX >= -movementSpeed){
						break;
					}
					if(vX<0){
						dX-=movementSpeed;
					}else{
						dX+=movementSpeed;
					}
					
				}else{
					if(vX<0){
						dR=1;
					}else{
						dR=-1;
					}
				}
				
				break;
			case 1:
				if(vX<=-75 && vY>=75){
					if(vY-vX<250+id*idOffset){
						dY-=movementSpeed*2;
						dX+=movementSpeed*2;
					}else if(vY-vX>350+id*idOffset){
						dY+=movementSpeed*2;
						dX-=movementSpeed*2;
					}
					
					if(vY+vX<movementSpeed+1 &&vY+vX>-movementSpeed+1){
						break;
					}
					if(-vX>vY){
						dY-=movementSpeed/2;
						dX-=movementSpeed/2;
					}else{
						dY+=movementSpeed/2;
						dX+=movementSpeed/2;
					}
					
				}else{
					if(vX>-75){
						dR=-1;
					}else{
						dR=1;
					}
				}
				break;
			case 2:
				if(vY<=150 && vY>=-150){
					
					if(-vX<250+id*idOffset){
						dX+=movementSpeed*2;
					}else if(-vX>350+id*idOffset){
						dX-=movementSpeed*2;
					}
					
					if(vY<=movementSpeed && vY >= -movementSpeed){
						break;
					}
					if(vY<0){
						dY-=movementSpeed;
					}else{
						dY+=movementSpeed;
					}
				}else{
					if(vY<0){
						dR=1;
					}else{
						dR=-1;
					}
				}
				break;
			case 3:
				if(vX<=-75 && vY<=-75){
					if(-vY-vX<250+id*idOffset){
						dY+=movementSpeed*2;
						dX+=movementSpeed*2;
					}else if(-vY-vX>350+id*idOffset){
						dY-=movementSpeed*2;
						dX-=movementSpeed*2;
					}
					
					if(-vY+vX<movementSpeed+1 &&-vY+vX>-movementSpeed+1){
						break;
					}
					if(vX>vY){
						dY-=movementSpeed/2;
						dX+=movementSpeed/2;
					}else{
						dY+=movementSpeed/2;
						dX-=movementSpeed/2;
					}
					
				}else{
					if(vX>-75){
						dR=1;
					}else{
						dR=-1;
					}
				}
				break;
			case 4:
				if(vX<=150 && vX>=-150){
					
					if(-vY<250+id*idOffset){
						dY+=movementSpeed*2;
					}else if(-vY>350+id*idOffset){
						dY-=movementSpeed*2;
					}
					
					if(vX<=movementSpeed && vX >= -movementSpeed){
						break;
					}
					if(vX<0){
						dX-=movementSpeed;
					}else{
						dX+=movementSpeed;
					}
				}else{
					if(vX<0){
						dR=-1;
					}else{
						dR=1;
					}
				}
				break;
			case 5:
				if(vX>=75 && vY<=-75){
					if(-vY+vX<250+id*idOffset){
						dY+=movementSpeed*2;
						dX-=movementSpeed*2;
					}else if(-vY+vX>350+id*idOffset){
						dY-=movementSpeed*2;
						dX+=movementSpeed*2;
					}
					
					if(vY+vX<movementSpeed+1 &&vY+vX>-movementSpeed+1){
						break;
					}
					
					if(vX>-vY){
						dY+=movementSpeed/2;
						dX+=movementSpeed/2;
					}else{
						dY-=movementSpeed/2;
						dX-=movementSpeed/2;
					}					
				}else{
					if(vX<75){
						dR=-1;
					}else{
						dR=1;
					}
				}
				break;
			case 6:
				if(vY<=150 && vY>=-150){
					if(vX<250+id*idOffset){
						dX-=movementSpeed*2;
					}else if(vX>350+id*idOffset){
						dX+=movementSpeed*2;
					}
					
					if(vY<=movementSpeed && vY >= -movementSpeed){
						break;
					}
					if(vY<0){
						dY-=movementSpeed;
					}else{
						dY+=movementSpeed;
					}
				}else{
					if(vY<0){
						dR=-1;
					}else{
						dR=1;
					}
				}
				break;
			case 7:
				if(vX>=75 && vY>=75){
					if(vY+vX<250+id*idOffset){
						dY-=movementSpeed*2;
						dX-=movementSpeed*2;
					}else if(vY+vX>350+id*idOffset){
						dY+=movementSpeed*2;
						dX+=movementSpeed*2;
					}
					
					if(vY-vX<movementSpeed+1 &&vY-vX>-movementSpeed+1){
						break;
					}
					
					if(vX>vY){
						dY-=movementSpeed/2;
						dX+=movementSpeed/2;
					}else{
						dY+=movementSpeed/2;
						dX-=movementSpeed/2;
					}
				}else{
					if(vX<75){
						dR=1;
					}else{
						dR=-1;
					}
				}
				break;
				
			}
			
			advancedEntities.get(e).rotate(dR);
			
			if(advancedEntities.get(e).getX()+dX<=1){ // links
				if(advancedEntities.get(e).getY()+dY<=1){
					advancedEntities.get(e).move(1-advancedEntities.get(e).getX(), 1-advancedEntities.get(e).getY());
				}
				else if(advancedEntities.get(e).getY()+dY+ advancedEntities.get(e).getHeight()>=screenY-1){
					advancedEntities.get(e).move(1-advancedEntities.get(e).getX(), 0);
				}
				else{
					advancedEntities.get(e).move(1-advancedEntities.get(e).getX(), dY);
				}
			}
			else if(advancedEntities.get(e).getX()+dX+ advancedEntities.get(e).getWidth()>=screenX-1){ // rechts
				if(advancedEntities.get(e).getY()+dY<=1){
					advancedEntities.get(e).move(0, 1-advancedEntities.get(e).getY());
				}
				else if(advancedEntities.get(e).getY()+dY+ advancedEntities.get(e).getHeight()>=screenY-1){
					advancedEntities.get(e).move(0, 0);
				}
				else{
					advancedEntities.get(e).move(0, dY);
				}
			}
			else if(advancedEntities.get(e).getY()+dY<=1){ // oben
				if(advancedEntities.get(e).getX()+dX<=1){
					advancedEntities.get(e).move(1-advancedEntities.get(e).getX(), 1-advancedEntities.get(e).getY());
				}
				else if(advancedEntities.get(e).getX()+dX+ advancedEntities.get(e).getWidth()>=screenX-1){
					advancedEntities.get(e).move(0, 1-advancedEntities.get(e).getY());
				}
				else{
					advancedEntities.get(e).move(dX, 1-advancedEntities.get(e).getY());
				}
			}
			else if(advancedEntities.get(e).getY()+dY+ advancedEntities.get(e).getHeight()>=screenY-1){ // unten
				if(advancedEntities.get(e).getX()+dX<=1){
					advancedEntities.get(e).move(1-advancedEntities.get(e).getX(),0);
				}
				else if(advancedEntities.get(e).getX()+dX+ advancedEntities.get(e).getWidth()>=screenX-1){
					advancedEntities.get(e).move(0, 0);
				}
				else{
					advancedEntities.get(e).move(dX, 0);
				}
			}
			else {
				advancedEntities.get(e).move(dX, dY);
			}
			
			for(int z = 1;z<advancedEntities.size();z++){
				if(enemyInput.shoot[e-1]==1){
					try {
						if(timers[e-1] + delay< System.currentTimeMillis() && (!(e == last) || advancedEntities.size()==2)){
								if(advancedEntities.get(e).getType()==1){
								simpleEntities.add(advancedEntities.get(e).shoot(-advancedEntities.get(e).getWidth()/4, -advancedEntities.get(e).getHeight()/4, 7, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
							}else if(advancedEntities.get(e).getType()==2){
								simpleEntities.add(advancedEntities.get(e).shoot(-advancedEntities.get(e).getWidth()/3, -advancedEntities.get(e).getHeight()/4, 7, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));
							}else if(advancedEntities.get(e).getType()==3){
								simpleEntities.add(advancedEntities.get(e).shoot(-advancedEntities.get(e).getWidth()/5+5, -advancedEntities.get(e).getHeight()+120, 7, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
							}else{
								if(advancedEntities.get(e).getHealth() <= 1000){
									bossFRate = 20;
								} else bossFRate = 16;
								if(enemyInput.shoot[e-1] <= bossFRate){
								simpleEntities.add(advancedEntities.get(e).shoot(0, 0, 7, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
								}
							}
							timers[e-1] = System.currentTimeMillis()-(new Random().nextInt(300)+100);
							last = e;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		enemyInput.update(advancedEntities.size()-1);
	}

}
