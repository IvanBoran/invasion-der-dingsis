package at.spengergasse.enemyInput;

import java.io.IOException;

/**
 * EnemyInput makes the "Inputs" for the AI.
 * It generates random moving and random shooting.
 * The EnemyInput class is the super class of InvadersInput and RoundInput.
 */
public abstract class EnemyInput {

	/**
	 * @param enemies the number of Enemies
	 * @throws IOException
	 */
	abstract public void update(int enemies) throws IOException;
}
