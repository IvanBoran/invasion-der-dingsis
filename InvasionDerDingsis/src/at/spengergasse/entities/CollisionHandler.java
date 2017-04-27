package at.spengergasse.entities;

import java.util.ArrayList;

public abstract class CollisionHandler {
	
	protected final int U_ID;
	
	protected Entity U_ENTITY;
	
	protected boolean inDanger;
	
	protected final ArrayList<Entity> entities;
	protected final int[] collisionMap;
	
	protected int resolutionX,resolutionY;
	
	public CollisionHandler(int U_ID,ArrayList<Entity> entities,int[] collisionMap,int resolutionX,int resolutionY){
		this.U_ID=U_ID;
		
		this.entities=entities;
		this.collisionMap=collisionMap;
		
		this.resolutionX=resolutionX;
		this.resolutionY=resolutionY;
	}
	
	public abstract void detectCollision();
	
	public abstract void handleHit(int id);
}
