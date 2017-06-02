package at.spengergasse.mode;

import java.io.IOException;
import java.util.Random;

import at.spengergasse.enemyInput.EnemyInput;
import at.spengergasse.enemyInput.InvadersInput;
import at.spengergasse.entities.AdvancedEntity;
import at.spengergasse.input.Keyboard;

public class InvadersMode extends Mode {
	
	private InvadersInput enemyInput;
	private long[] timers;
	
	public InvadersMode(int screenX, int screenY, int[] data, Keyboard keyboard, int difficulty,InvadersInput enemyInput)
			throws NumberFormatException, IOException {
		super(screenX, screenY, data, keyboard, difficulty);
		
		this.enemyInput=enemyInput;

		player = new AdvancedEntity(screenX / 2 - 8*4, screenY - 100, "src/entities/shapePlayer", 4, 100, 0);
		advancedEntities.add(player);

		switch (difficulty) {
		case 1:
			for (int g = 1; g <= 8; g++) {
				advancedEntities.add(new AdvancedEntity(g * 160 - 8*4, screenY / 13, "src/entities/shapeEnemy1", 4, 100, 1));
			}  
			break;

		case 2:
			for (int g = 1; g <= 8; g++) {
				advancedEntities.add(new AdvancedEntity(g * 160 - 8*4, screenY / 13, "src/entities/shapeEnemy1", 4, 100, 1));
			}
			for (int g = 1; g <= 5; g++) {
				advancedEntities.add(new AdvancedEntity((g * 160 + (160+160/2)) - 10*4, screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
			}
			break;

		case 3:
			for (int g = 1; g <= 12; g++) {
				advancedEntities.add(new AdvancedEntity(g * 110 - 8*4, screenY / 13, "src/entities/shapeEnemy1", 4, 100, 1));
			}
			for (int g = 1; g <= 5; g++) {
				advancedEntities.add(new AdvancedEntity((g * 110 + (110*3+110/2)) - 10*4, screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
			}
			break;

		case 4:
			for (int g = 1; g <= 12; g++) {
				advancedEntities.add(new AdvancedEntity(g * 110 - 8*4, screenY / 13, "src/entities/shapeEnemy1", 4, 200, 1));
			}
			for (int g = 1; g <= 7; g++) {
				advancedEntities.add(new AdvancedEntity((g * 110 + (110*2+110/2)) - 10*4, screenY / 5, "src/entities/shapeEnemy2", 5, 300, 1));
			}
			break;

		case 5:
			for (int g = 1; g <= 8; g++) {
				advancedEntities.add(new AdvancedEntity(g * 160 - 8*4, screenY / 13, "src/entities/shapeEnemy2", 4, 300, 2));
			}
			for (int g = 1; g <= 5; g++) {
				advancedEntities.add(new AdvancedEntity((g * 160 + (160+160/2)) - 10*5, screenY / 4, "src/entities/shapeEnemy3", 5, 400, 3));
			}
			break;
		case 6:
			for (int g = 1; g <= 12; g++) {
				advancedEntities.add(new AdvancedEntity(g * 110 - 8*4, screenY / 13, "src/entities/shapeEnemy2", 4, 300, 2));
			}
			for (int g = 1; g <= 5; g++) {
				advancedEntities.add(new AdvancedEntity((g * 110 + (110*3+110/2)) - 10*5, screenY / 4, "src/entities/shapeEnemy3", 5, 400, 3));
			}
			break;
		case 7:
			for (int g = 1; g <= 12; g++) {
				advancedEntities.add(new AdvancedEntity(g * 110 - 8*4, screenY / 13, "src/entities/shapeEnemy2", 4, 400, 2));
			}
			for (int g = 1; g <= 7; g++) {
				advancedEntities.add(new AdvancedEntity((g * 110 + (110*2+110/2)) - 10*5, screenY / 4, "src/entities/shapeEnemy3", 5, 500, 3));
			}
			break;
		case 8:
			for (int g = 1; g <= 12; g++) {
				advancedEntities.add(new AdvancedEntity(g * 110 - 8*4, screenY / 13, "src/entities/shapeEnemy2", 4, 400, 2));
			}
			for (int g = 1; g <= 9; g++) {
				advancedEntities.add(new AdvancedEntity((g * 110 + (110+110/2)) - 10*5, screenY / 4, "src/entities/shapeEnemy3", 5, 500, 3));
			}
			break;
		case 9:
			for (int g = 1; g <= 12; g++) {
				advancedEntities.add(new AdvancedEntity(g * 110 - 8*4, screenY / 13, "src/entities/shapeEnemy2", 4, 500, 2));
			}
			for (int g = 1; g <= 11; g++) {
				advancedEntities.add(new AdvancedEntity((g * 110 + 110/2) - 10*5, screenY / 4, "src/entities/shapeEnemy3", 5, 600, 3));
			}
			break;
		case 10:
			advancedEntities.add(new AdvancedEntity(screenX /2 - 10*25, 1, "src/entities/shapeEnemy4", 25, 1000, 4));
		}
		
		timers = new long[advancedEntities.size()-1];
	}
	
	int movementSpeedE = 1;
	int delay = 650;
	int last = 0;
	
	protected void updateEnemies(){
		
		if(!(advancedEntities.size()>1)){
			return;
		}
		
		int x=0;
		
		if(enemyInput.left){
			x-=movementSpeedE;
		}
		if(enemyInput.right){
			x+=movementSpeedE;
		}
		
		if(advancedEntities.get(1).getX()+x<=1  || advancedEntities.get(advancedEntities.size()-1).getX()+x<=1  ){
			for(int e = 1;e<advancedEntities.size();e++){
				advancedEntities.get(e).move(-x, 0);
			}
		}
		else if(advancedEntities.get(1).getX()+x+ advancedEntities.get(1).getWidth()>=screenX-1 
				|| advancedEntities.get(advancedEntities.size()-1).getX()+x + advancedEntities.get(1).getWidth()>=screenX-1 ){
			for(int e = 1;e<advancedEntities.size();e++){
				advancedEntities.get(e).move(-x, 0);
			}
		}
		else{
			for(int e = 1;e<advancedEntities.size();e++){
				advancedEntities.get(e).move(x, 0);
			}
		}
		
		for(int e = 1;e<advancedEntities.size();e++){
			if(enemyInput.shoot[e-1]==0){
				try {
					if(timers[e-1] + delay< System.currentTimeMillis() && !(e == last)){
							if(advancedEntities.get(e).getType()==1){
							simpleEntities.add(advancedEntities.get(e).shoot(0, advancedEntities.get(e).getHeight(), -4, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						}else if(advancedEntities.get(e).getType()==2){
							simpleEntities.add(advancedEntities.get(e).shoot(0, advancedEntities.get(e).getHeight(), -4, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));
						}else if(advancedEntities.get(e).getType()==3){
							simpleEntities.add(advancedEntities.get(e).shoot(0, advancedEntities.get(e).getHeight(), -4, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
						}else{
							simpleEntities.add(advancedEntities.get(e).shoot(0, advancedEntities.get(e).getHeight(), -4, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						}
						timers[e-1] = System.currentTimeMillis()-(new Random().nextInt(300)+100);
						last = e;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		enemyInput.update(advancedEntities.size()-1);
	}

	int movementSpeed = 7;

	long timeS, timeD, timeF = 0;
	
	int shotDelayS = 500;
	int shotDelayD = 2500;
	int shotDelayF = 200;

	int xOffset = -14;
	int yOffset = 0;

	@Override
	protected void handleInputs() {
		if(keyboard.esc){
			player.died();
		}
		
		if (keyboard.left) {
			if (player.getX() - movementSpeed < 1)
				player.move(-(player.getX()) + 1, 0);
			else
				player.move(-movementSpeed, 0);
		}
		if (keyboard.right) {
			if (player.getX() + movementSpeed + player.getWidth() >= screenX - 1)
				player.move((screenX - player.getX()) - 2 - player.getWidth(), 0);
			else
				player.move(movementSpeed, 0);
		}

		if (keyboard.s) {
			try {
				if (!player.isDead()) {
					if (System.currentTimeMillis() > timeS) {
						simpleEntities.add(
								player.shoot(xOffset, yOffset, 6, 20, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						timeF = System.currentTimeMillis() + shotDelayS;
						timeD = System.currentTimeMillis() + shotDelayS;
						timeS = System.currentTimeMillis() + shotDelayS;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (keyboard.d) {
			try {
				if (!player.isDead()) {
					if (System.currentTimeMillis() > timeD) {
						simpleEntities.add(
								player.shoot(xOffset-8, yOffset, 1, 10, -2, 0, 2, 1, "src/entities/shots/shapeShoot2"));
						simpleEntities.add(
								player.shoot(xOffset-4, yOffset, 1, 10, -1, 0, 2, 1, "src/entities/shots/shapeShoot2"));

						simpleEntities.add(
								player.shoot(xOffset, yOffset, 1, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));

						simpleEntities.add(
								player.shoot(xOffset+4, yOffset, 1, 10, 1, 0, 2, 1, "src/entities/shots/shapeShoot2"));
						simpleEntities.add(
								player.shoot(xOffset+8, yOffset, 1, 10, 2, 0, 2, 1, "src/entities/shots/shapeShoot2"));
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
				if (!player.isDead()) {
					if (System.currentTimeMillis() > timeF) {
						simpleEntities.add(
								player.shoot(xOffset, yOffset, 7, 500, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
						timeF = System.currentTimeMillis() + shotDelayF;
						timeD = System.currentTimeMillis() + shotDelayF;
						timeS = System.currentTimeMillis() + shotDelayF;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void hit(int id1, int id2) {
		final int COLLISION_DAMAGE = 50;
			if (id1 < 0 && id2 < 0) {// Beide simple
				simpleEntities.get(getSimpleEntity(id1)).died();
				simpleEntities.get(getSimpleEntity(id2)).died();
			} else if (id1 > 0 && id2 > 0) {
				advancedEntities.get(getAdvancedEntity(id1)).hit(COLLISION_DAMAGE);
				advancedEntities.get(getAdvancedEntity(id2)).hit(COLLISION_DAMAGE);
			} else if (id1 < 0 || id2 < 0) {
				if (id1 < 0) {
					if(!advancedEntities.get(getAdvancedEntity(id2)).getHit()){
						advancedEntities.get(getAdvancedEntity(id2)).hit(simpleEntities.get(getSimpleEntity(id1)).getDamage());
						simpleEntities.get(getSimpleEntity(id1)).died();
					}
				} else {
					if(!advancedEntities.get(getAdvancedEntity(id1)).getHit()){
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

}
