package at.spengergasse.entities;

import java.util.ArrayList;

public class TestCollisionHandler extends CollisionHandler{
	
	public TestCollisionHandler(ArrayList<Entity> entities) {
		super(entities);
	}

	@Override
	public void handleCollision(int id) {
		System.out.println(U_ID+" "+id);
	}

}
