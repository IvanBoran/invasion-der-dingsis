package at.spengergasse.enemyInput;

import java.util.Random;

public class InvadersInput extends EnemyInput{

	public boolean left,right;
	public int[] shoot=new int[100];
	
	private int counter;
	private int z=1;
	
	@Override
	public void update(int enemies) {
		left=false;
		right=false;
		if(counter == 0){
			z*=-1;
			counter = (new Random().nextInt(3)+1)*30;
		}else{
			if(z<0){
				left=true;
				counter--;
			}else{
				right=true;
				counter--;
			}
		}
		
		shoot = new int[enemies];
		
		for(int i =0;i<shoot.length;i++){
			shoot[i]=new Random().nextInt(75);
		}
	}
}
