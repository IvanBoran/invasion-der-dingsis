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
		int fRow = 1;
		int sRow = 1;
		

		switch (difficulty) {
		case 1:
			for (int g = 1; g <= 8; g++) { // 8 1er
				advancedEntities.add(new AdvancedEntity(g * 160 - 8*4, screenY / 13, "src/entities/shapeEnemy1", 4, 100, 1));
			}  
			break;

		case 2:
			fRow = 1; // 8 2er
			sRow = 1; // 5 1er
			for(int i = 1; i <= 13; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 160 - 10*4, screenY / 13, "src/entities/shapeEnemy2", 5, 100, 2));
				fRow ++;
				if(fRow >= 8/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 160 + (160+160/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy1", 4, 200, 1));
					sRow++;
					i++;
				}
			}
			break;

		case 3:
			fRow = 1; // 12 2er
			sRow = 1; // 5 1er
			for(int i = 1; i <= 17; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*4, screenY / 13, "src/entities/shapeEnemy2", 5, 200, 2)); System.out.println("First");
				fRow ++;
				if(fRow >= 12/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*3+110/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy1", 4, 100, 1));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;

		case 4:
			fRow = 1; // 12 2er
			sRow = 1; // 7 1er
			for(int i = 1; i <= 19; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*4, screenY / 13, "src/entities/shapeEnemy2", 5, 300, 2));System.out.println("first");
				fRow ++;
				if(fRow >= 12/2-2 && sRow <= 7){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*2+110/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy1", 4, 200, 1));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;

		case 5:
			fRow = 1; // 8 3er
			sRow = 1; // 5 2er
			for(int i = 1; i <= 13; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 160 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 400, 3));System.out.println("first");
				fRow ++;
				if(fRow >= 8/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 160 + (160+160/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy2", 5, 300, 2));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;
			
		case 6:
			fRow = 1; // 12 3er
			sRow = 1; // 5 2er
			for(int i = 1; i <= 17; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 400, 3));System.out.println("first");
				fRow ++;
				if(fRow >= 12/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*3+110/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy2", 5, 300, 2));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;
			
		case 7:
			fRow = 1; // 12 3er
			sRow = 1; // 7 2er
			for(int i = 1; i <= 19; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 500, 3));System.out.println("first");
				fRow ++;
				if(fRow >= 12/2-2 && sRow <= 7){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*5+110/2)) - 8*44, screenY / 5, "src/entities/shapeEnemy2", 5, 400, 2));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;
			
		case 8:
			fRow = 1; // 12 3er
			sRow = 1; // 9 2er
			for(int i = 1; i <= 21; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 500, 3));System.out.println("first");
				fRow ++;
				if(fRow >= 12/2-3 && sRow <= 9){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110+110/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy2", 5, 400, 2));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;
			
		case 9:
			fRow = 1; // 12 3er
			sRow = 1; // 11 2er
			for(int i = 1; i <= 23; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 5, 600, 3));System.out.println("first");
				fRow ++;
				if(fRow >= 12/2-4 && sRow <= 11){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + 110/2) - 10*4, screenY / 5, "src/entities/shapeEnemy2", 5, 500, 2));System.out.println("last");
					sRow++;
					i++;
				}
			}
			break;
			
		case 10:
			advancedEntities.add(new AdvancedEntity(screenX /2 - 10*25, 1, "src/entities/shapeEnemy4", 17, 1000, 4));
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
		
//		if(enemyInput.left){
//			x-=movementSpeedE;
//		}
//		if(enemyInput.right){
//			x+=movementSpeedE;
//		}
//		
//		if(advancedEntities.get(1).getX()+x<=1  || advancedEntities.get(advancedEntities.size()-1).getX()+x<=1  ){
//			for(int e = 1;e<advancedEntities.size();e++){
//				advancedEntities.get(e).move(-x, 0);
//			}
//		}
//		else if(advancedEntities.get(1).getX()+x+ advancedEntities.get(1).getWidth()>=screenX-1 
//				|| advancedEntities.get(advancedEntities.size()-1).getX()+x + advancedEntities.get(1).getWidth()>=screenX-1 ){
//			for(int e = 1;e<advancedEntities.size();e++){
//				advancedEntities.get(e).move(-x, 0);
//			}
//		}
//		else{
//			for(int e = 1;e<advancedEntities.size();e++){
//				advancedEntities.get(e).move(x, 0);
//			}
//		}
		
		for(int e = 1;e<advancedEntities.size();e++){
			if(enemyInput.shoot[e-1]==0){
				try {
					if(timers[e-1] + delay< System.currentTimeMillis() && !(e == last)){
							if(advancedEntities.get(e).getType()==1){
							simpleEntities.add(advancedEntities.get(e).shoot(-12, advancedEntities.get(e).getHeight()-20, -3, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						}else if(advancedEntities.get(e).getType()==2){
							simpleEntities.add(advancedEntities.get(e).shoot(-13, advancedEntities.get(e).getHeight()-25, -3, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));
						}else if(advancedEntities.get(e).getType()==3){
							simpleEntities.add(advancedEntities.get(e).shoot(-15, advancedEntities.get(e).getHeight()-20, -3, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
						}else{
							simpleEntities.add(advancedEntities.get(e).shoot(-13, advancedEntities.get(e).getHeight()-20, -4, 10, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
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
				if (System.currentTimeMillis() > timeS) {
					simpleEntities.add(
							player.shoot(xOffset, yOffset, 6, 20, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
					timeF = System.currentTimeMillis() + shotDelayS;
					timeD = System.currentTimeMillis() + shotDelayS;
					timeS = System.currentTimeMillis() + shotDelayS;
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
