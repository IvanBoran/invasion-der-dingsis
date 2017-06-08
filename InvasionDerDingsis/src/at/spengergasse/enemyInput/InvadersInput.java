package at.spengergasse.enemyInput;

import java.util.Random;

/**
 * InvadersInput makes the "Inputs" for the AI in the gamemode "Invaders".
 * It generates random moving and random shooting.
 * The InvadersInput class is a subclass of EnemyInput.
 */
public class InvadersInput extends EnemyInput{

	public boolean left,right;
	public int[] shoot=new int[100];
	
	private int counter;
	private int z=1;
	
	/**
	 * Makes random moving and shooting for the AI in the gamemode "Invaders".
	 * 
	 * @param enemies the number of Enemies
	 */
	@Override
	public void update(int enemies) {
		left=false;
		right=false;
		if(counter == 0){
			z*=-1;
			counter = (new Random().nextInt(3)+1)*35;
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
