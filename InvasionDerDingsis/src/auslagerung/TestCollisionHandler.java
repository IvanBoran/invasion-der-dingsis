package auslagerung;

import java.util.ArrayList;

import at.spengergasse.entities.Entity;

public class TestCollisionHandler extends CollisionHandler{
	
	public TestCollisionHandler(int U_ID,ArrayList<Entity> entities,int[] collisionMap,int resolutionX,int resolutionY) {
		super(U_ID, entities, collisionMap,resolutionX,resolutionY);
	}
	

	@Override
	public void detectCollision() {
		if(U_ENTITY==null){
			U_ENTITY = entities.get(U_ID-1);
		}
		
		int width = U_ENTITY.getWidth();
		int height = U_ENTITY.getHeight();
		
		int x = U_ENTITY.getX();
		int y = U_ENTITY.getY();
		
		for(int i=0;i<height;i++){
			for(int e=0;e<width;e++){
				if(collisionMap[x + y*resolutionX + e + i* resolutionX]!=0 || collisionMap[x + y*resolutionX + e + i* resolutionX]!=U_ID){
					if(collisionMap[x + y*resolutionX + e + i* resolutionX+1]!=0 || collisionMap[x + y*resolutionX + e + i* resolutionX-1]!=0
							|| collisionMap[x + y*resolutionX + e + i* resolutionX+resolutionX]!=0 || collisionMap[x + y*resolutionX + e + i* resolutionX-resolutionX]!=0){
						System.out.println("HIT");
					}
				}
			}
		}
		
	}

	@Override
	public void handleHit(int id) {
		
	}
	
}
