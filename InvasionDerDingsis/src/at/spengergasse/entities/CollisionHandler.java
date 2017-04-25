package at.spengergasse.entities;

import java.util.ArrayList;

public abstract class CollisionHandler {
	
	protected final int U_ID = Entity.id-1;
	
	protected final ArrayList<Entity> entities;
	
	public CollisionHandler(ArrayList<Entity> entities){
		this.entities=entities;
	}
	
	public abstract void handleCollision(int id);
}
