package at.spengergasse.mode;

import java.awt.Adjustable;
import java.io.IOException;

import at.spengergasse.enemyInput.EnemyInput;
import at.spengergasse.enemyInput.RoundInput;
import at.spengergasse.entities.AdvancedEntity;
import at.spengergasse.input.Keyboard;

public class RoundMode extends Mode{
	private RoundInput enemyInput;

	public RoundMode(int screenX, int screenY, int[] data, Keyboard keyboard, int difficulty,RoundInput enemyInput) throws NumberFormatException, IOException {
		super(screenX, screenY, data, keyboard, difficulty);
		
		this.enemyInput=enemyInput;
		
		player = new AdvancedEntity(screenX / 2, screenY - 100, "src/entities/shapePlayer", 4, 100, 0);
		advancedEntities.add(player);

		switch (difficulty) {
		case 1:
			advancedEntities.add(new AdvancedEntity(screenX / 2, 200, "src/entities/shapeEnemy1", 4, 100, 1));
			break;

		case 2:
			
			break;

		case 3:
			
			break;

		case 4:
			
			break;

		case 5:
			
			break;
			
		case 6:
			
			break;
			
		case 7:
			
			break;
			
		case 8:
			
			break;
			
		case 9:
			
			break;
			
		case 10:
			
			break;
		}
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

	long timeS, timeF;
	int movementSpeed = 5;

	int shotDelayS = 500;
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
		
		if(keyboard.down){
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
								player.shoot(xOffset, yOffset, 6, 20, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						timeF = System.currentTimeMillis() + shotDelayF;
						timeS = System.currentTimeMillis() + shotDelayS;
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (keyboard.f) {
			try {
					if (System.currentTimeMillis() > timeF) {
						simpleEntities.add(
								player.shoot(xOffset, yOffset, 7, 500, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
						timeF = System.currentTimeMillis() + shotDelayF;
						timeS = System.currentTimeMillis() + shotDelayS;
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	protected void updateEnemies() {
		int xP = player.getX();
		int yP = player.getY();
		
		int movementSpeed = 3;
		
		for(int e=1;e<advancedEntities.size();e++){
			int x = advancedEntities.get(e).getX();
			int y = advancedEntities.get(e).getY();
			int rotation = advancedEntities.get(e).getRotation();
			
			int dX=0;
			int dY=0;
			int dR=0;
			
			int vX=xP-x;
			int vY=yP-y;
			
			switch(rotation){//TODO abstand halten
			
			case 0:
				if(vX<=150 && vX>=-150){
					if(vX==0){
						break;
					}
					if(vX<=movementSpeed+1 && vX>=-movementSpeed-1){
						dX=vX;
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

				break;
			case 2:
				if(vY<=150 && vY>=-150){
					if(vY==0){
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

				break;
			case 4:
				if(vX<=150 && vX>=-150){
					if(vX==0){
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
			case 5:
				break;
			case 6:
				if(vY<=150 && vY>=-150){
					if(vY==0){
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
			case 7:
			
				break;
				
			}
			
			advancedEntities.get(e).rotate(dR);
			advancedEntities.get(e).move(dX, dY);
		}
		
		enemyInput.update(advancedEntities.size()-1);
	}

}