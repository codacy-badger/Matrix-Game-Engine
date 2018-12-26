package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {
	

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	public  static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	//private static final float TERRAIN_HEIGHT =0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed =0; 
	
	private float upwardsSpeed = 0;   
	
	private boolean inAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}
	
	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0,currentTurnSpeed * DisplayManager.getFrameTimeSeconds()  , 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds()  ;
		
		//Now we will find the dx and dz with sin and cos function for finding player translation   
	
		float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY()))) ;
		float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY()))) ;
		super.increasePosition(dx, 0, dz);
		
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		
		super.increasePosition(0,upwardsSpeed *DisplayManager.getFrameTimeSeconds() , 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		
		if(super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			inAir = false;
			super.getPosition().y = terrainHeight;
		}
		
	}
	
	private void jump() {
		if(!inAir) {
		this.upwardsSpeed = JUMP_POWER;
		inAir = true;
		}
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			this.currentSpeed = RUN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
			
		}else {
			
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
			
		}else {
			
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			
			jump();
		}
		
		
		
		
	}
	
	
	

}
