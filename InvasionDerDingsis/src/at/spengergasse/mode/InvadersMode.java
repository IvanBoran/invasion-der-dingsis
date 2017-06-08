package at.spengergasse.mode;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

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
		player.rotate(4);
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
				advancedEntities.add(new AdvancedEntity(fRow * 160 - 10*4, screenY / 13, "src/entities/shapeEnemy2", 5, 200, 2));
				fRow ++;
				if(fRow >= 8/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 160 + (160+160/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy1", 4, 100, 1));
					sRow++;
					i++;
				}
			}
			break;

		case 3:
			fRow = 1; // 12 2er
			sRow = 1; // 5 1er
			for(int i = 1; i <= 17; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*4, screenY / 13, "src/entities/shapeEnemy2", 5, 200, 2));
				fRow ++;
				if(fRow >= 12/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*3+110/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy1", 4, 100, 1));
					sRow++;
					i++;
				}
			}
			break;

		case 4:
			fRow = 1; // 12 2er
			sRow = 1; // 7 1er
			for(int i = 1; i <= 19; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*4, screenY / 13, "src/entities/shapeEnemy2", 5, 200, 2));
				fRow ++;
				if(fRow >= 12/2-2 && sRow <= 7){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*2+110/2)) - 8*4, screenY / 5, "src/entities/shapeEnemy1", 4, 100, 1));
					sRow++;
					i++;
				}
			}
			break;

		case 5:
			fRow = 1; // 8 3er
			sRow = 1; // 5 2er
			for(int i = 1; i <= 13; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 160 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 300, 3));
				fRow ++;
				if(fRow >= 8/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 160 + (160+160/2)) - 7*4, screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
					sRow++;
					i++;
				}
			}
			break;
			
		case 6:
			fRow = 1; // 12 3er
			sRow = 1; // 5 2er
			for(int i = 1; i <= 17; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 300, 3));
				fRow ++;
				if(fRow >= 12/2-1 && sRow <= 5){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*3+110/2)) - (7*4+2), screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
					sRow++;
					i++;
				}
			}
			break;
			
		case 7:
			fRow = 1; // 12 3er
			sRow = 1; // 7 2er
			for(int i = 1; i <= 19; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 300, 3));
				fRow ++;
				if(fRow >= 12/2-2 && sRow <= 7){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110*2+110/2)) - (7*4+2), screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
					sRow++;
					i++;
				}
			}
			break;
			
		case 8:
			fRow = 1; // 12 3er
			sRow = 1; // 9 2er
			for(int i = 1; i <= 21; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 6, 300, 3));
				fRow ++;
				if(fRow >= 12/2-3 && sRow <= 9){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + (110+110/2)) - 10*4, screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
					sRow++;
					i++;
				}
			}
			break;
			
		case 9:
			fRow = 1; // 12 3er
			sRow = 1; // 11 2er
			for(int i = 1; i <= 23; i++){
				advancedEntities.add(new AdvancedEntity(fRow * 110 - 10*5, screenY / 13, "src/entities/shapeEnemy3", 5, 300, 3));
				fRow ++;
				if(fRow >= 12/2-4 && sRow <= 11){
					advancedEntities.add(new AdvancedEntity((sRow * 110 + 110/2) - 10*4, screenY / 5, "src/entities/shapeEnemy2", 5, 200, 2));
					sRow++;
					i++;
				}
			}
			break;
			
		case 10:
			advancedEntities.add(new AdvancedEntity(screenX /2 - 10*17, 1, "src/entities/shapeEnemy4", 17, 2000, 4));
		}
		
		timers = new long[advancedEntities.size()-1];
	}
	
	int movementSpeedE = 1;
	int delay = 650;
	int last = 0;
	
	protected void updateEnemies(){
		
		Random r = new Random();
		int t = 0;
		int bossFRate = 0;
		
		if(!(advancedEntities.size()>1)){
			return;
		}
		
		int x=0;
		if(getBoss() != null){
			movementSpeedE = 3;
		}
		if(getBoss() != null && getBoss().getHealth() <= 1000){
			movementSpeedE = 6;
		}
		
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
			if(enemyInput.shoot[e-1]==1){
				try {
					if(timers[e-1] + delay< System.currentTimeMillis() && (!(e == last) || advancedEntities.get(e).getType() == 4)){
							if(advancedEntities.get(e).getType()==1){
							simpleEntities.add(advancedEntities.get(e).shoot(-12, -30, 4, 20, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
						}else if(advancedEntities.get(e).getType()==2){
							simpleEntities.add(advancedEntities.get(e).shoot(-12, -30, 5, 30, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));
						}else if(advancedEntities.get(e).getType()==3){
							simpleEntities.add(advancedEntities.get(e).shoot(-16, -30, 6, 40, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
						}else{
							if(advancedEntities.get(e).getHealth() <= 1000){
								bossFRate = 12;
							} else bossFRate = 8;
							if(enemyInput.shoot[e-1] <= bossFRate){
								t = r.nextInt(4);
								if(t == 0){
									simpleEntities.add(advancedEntities.get(e).shoot(-50,-80, 3, 80, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
								}else if(t == 1){
									simpleEntities.add(advancedEntities.get(e).shoot(-140, -80, 5, 50, -6, 0, 2, 1, "src/entities/shots/shapeShoot2"));
									simpleEntities.add(advancedEntities.get(e).shoot(-110, -80, 5, 50, -4, 0, 2, 1, "src/entities/shots/shapeShoot2"));
									simpleEntities.add(advancedEntities.get(e).shoot(-80, -80, 5, 50, -2, 0, 2, 1, "src/entities/shots/shapeShoot2"));
									simpleEntities.add(advancedEntities.get(e).shoot(-50, -80, 5, 50, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));
									simpleEntities.add(advancedEntities.get(e).shoot(-20, -80, 5, 50, 2, 0, 2, 1, "src/entities/shots/shapeShoot2"));
									simpleEntities.add(advancedEntities.get(e).shoot(+10, -80, 5, 50, 4, 0, 2, 1, "src/entities/shots/shapeShoot2"));
									simpleEntities.add(advancedEntities.get(e).shoot(+40, -80, 5, 50, 6, 0, 2, 1, "src/entities/shots/shapeShoot2"));
								} else if(t == 2){
									simpleEntities.add(advancedEntities.get(e).shoot(-200, -140, 5, 40, -10, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(-170, -120, 5, 40, -8, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(-140, -100, 5, 40, -6, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(-110, -80, 5, 40, -4, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(-80, -80, 5, 40, -2, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(-50, -80, 5, 40, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(-20, -80, 5, 40, 2, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(+10, -80, 5, 40, 4, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(+40, -100, 5, 40, 6, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(+70, -120, 5, 40, 8, 0, 2, 1, "src/entities/shots/shapeShoot3"));
									simpleEntities.add(advancedEntities.get(e).shoot(+100, -140, 5, 40, 10, 0, 2, 1, "src/entities/shots/shapeShoot3"));
								} else{
									simpleEntities.add(advancedEntities.get(e).shoot(-230, -200, 5, 50, -12, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-200, -180, 5, 50, -10, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-170, -160, 5, 50, -8, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-140, -140, 5, 50, -6, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-110, -120, 5, 50, -4, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-80, -100, 5, 50, -2, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-50, -80, 5, 50, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(-20, -100, 5, 50, 2, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(+10, -120, 5, 50, 4, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(+40, -140, 5, 50, 6, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(+70, -160, 5, 50, 8, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(+100, -180, 5, 50, 10, 0, 2, 1, "src/entities/shots/shapeShoot1"));
									simpleEntities.add(advancedEntities.get(e).shoot(+130, -200, 5, 50, 12, 0, 2, 1, "src/entities/shots/shapeShoot1"));
								}
							}
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
	int shotDelayD = 800;
	int shotDelayF = 250;

	int xOffset = -10;
	int yOffset = -16;

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
							player.shoot(xOffset, yOffset, 6, 40, 0, 0, 1, 1, "src/entities/shots/shapeShoot1"));
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
								player.shoot(xOffset-8, yOffset, 3, 20, -2, 0, 2, 1, "src/entities/shots/shapeShoot2"));
						simpleEntities.add(
								player.shoot(xOffset-4, yOffset, 3, 20, -1, 0, 2, 1, "src/entities/shots/shapeShoot2"));

						simpleEntities.add(
								player.shoot(xOffset, yOffset, 3, 20, 0, 0, 1, 1, "src/entities/shots/shapeShoot2"));

						simpleEntities.add(
								player.shoot(xOffset+4, yOffset, 3, 20, 1, 0, 2, 1, "src/entities/shots/shapeShoot2"));
						simpleEntities.add(
								player.shoot(xOffset+8, yOffset, 3, 20, 2, 0, 2, 1, "src/entities/shots/shapeShoot2"));
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
								player.shoot(xOffset, yOffset, 7, 15, 0, 0, 1, 1, "src/entities/shots/shapeShoot3"));
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
				
			} else if (id1 > 0 && id2 > 0) {
				advancedEntities.get(getAdvancedEntity(id1)).hit(COLLISION_DAMAGE);
				advancedEntities.get(getAdvancedEntity(id2)).hit(COLLISION_DAMAGE);
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

}
