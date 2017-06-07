package at.spengergasse.enemyInput;

import java.util.Random;

public class RoundInput extends EnemyInput{

	public int[] shoot=new int[100];
	
	private int counter;
	private int z=1;
	
	@Override
	public void update(int enemies) {
		
		if(counter == 0){
			z*=-1;
			counter = (new Random().nextInt(3)+1)*30;
		}else{
			if(z<0){
				counter--;
			}else{
				counter--;
			}
		}
		
		shoot = new int[enemies];
		
		for(int i =0;i<shoot.length;i++){
			shoot[i]=new Random().nextInt(75);
		}
	}

}
