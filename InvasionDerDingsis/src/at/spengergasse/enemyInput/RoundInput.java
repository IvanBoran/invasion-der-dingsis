package at.spengergasse.enemyInput;

import java.util.Random;

/**
 * RoundInput makes the "Inputs" for the AI in the gamemode "round".
 * It generates random moving and random shooting.
 * The RoundInput class is a subclass of EnemyInput.
 */
public class RoundInput extends EnemyInput{

	public int[] shoot=new int[100];
	
	private int counter;
	private int z=1;
	
	/**
	 * Makes random moving and shooting for the AI in the gamemode "round".
	 * 
	 * @param enemies the number of Enemies
	 */
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
