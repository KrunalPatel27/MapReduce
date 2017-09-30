package edu.umkc.rashmi;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.psnbtech.Game;
import org.psnbtech.entity.*;
import org.psnbtech.util.Clock;
import org.psnbtech.util.Vector2;

public class EntityTestController {
 
	Game asteroidGame;
	@Before
	public void setUp() throws Exception 
	{
		
	    asteroidGame=new Game();
	    asteroidGame.initGame();
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdateForPlayer() {
		System.out.println("Inside update of player: ");
		
		Entity asteroid=new Asteroid(new Random());		
		asteroidGame.registerEntity(asteroid);		
		
		Vector2 playerOldPos=asteroidGame.player.getPosition();
		Double oldX=playerOldPos.x;
		Double oldY=playerOldPos.y;
		
		Double oldVelocityX=asteroidGame.player.getVelocity().x;
		System.out.println("Player position :"+playerOldPos.x+" "+playerOldPos.y);
		System.out.println("Player velocity :"+oldVelocityX);
				
		asteroidGame.player.setThrusting(true);
		asteroidGame.player.setRotateLeft(true);
		asteroidGame.player.update(asteroidGame);		
		asteroidGame.updateGame();
		
		Vector2 playerNewPos=asteroidGame.player.getPosition(); 
		Double newX=playerNewPos.x;
		Double newY=playerNewPos.y;
		
		Double velocityXAfterThrust=asteroidGame.player.getVelocity().x;
		Double velocityYAfterThrust=asteroidGame.player.getVelocity().y;

		System.out.println("Player position :"+playerNewPos.x+" "+playerNewPos.y);
		System.out.println("Player velocity :"+velocityXAfterThrust+" "+velocityYAfterThrust);
		
		Double playerNewExpectedPosforX=oldX+velocityXAfterThrust;
		Double playerNewExpectedPosforY=oldY+velocityYAfterThrust;
		System.out.println("Player expected position :"+playerNewExpectedPosforX+" "+playerNewExpectedPosforY);		
		
		//old and new positions of the player after thrust should not be same
		assertNotEquals(oldX,newX);
		assertNotEquals(oldY,newY);
		
		//the new position of the player should move by just one notch of veocity
		//assertEquals(playerNewExpectedPosforX,newX);
		//assertEquals(playerNewExpectedPosforY,newY);
			
	}
	
	@Test
	public void testUpdateForAsteroid() {
		
		System.out.println("Inside update of asteroid: ");
		
		Entity asteroid=new Asteroid(new Random());		
		asteroidGame.registerEntity(asteroid);		
		
		Vector2 asteroidOldPos=asteroid.getPosition();
		Double oldX=asteroidOldPos.x;
		Double oldY=asteroidOldPos.y;
		
		Double oldVelocityX=asteroid.getVelocity().x;
		System.out.println("Asteroid position :"+asteroidOldPos.x+" "+asteroidOldPos.y);
		System.out.println("Asteroid velocity :"+oldVelocityX);
				
		asteroid.update(asteroidGame);	
		asteroidGame.updateGame();
		
		Vector2 asteroidNewPos=asteroid.getPosition(); 
		Double newX=asteroidNewPos.x;
		Double newY=asteroidNewPos.y;
		
		Double velocityXAfterThrust=asteroid.getVelocity().x;
		Double velocityYAfterThrust=asteroid.getVelocity().y;

		System.out.println("Asteorid position :"+asteroidNewPos.x+" "+asteroidNewPos.y);
		System.out.println("Asteorid velocity :"+velocityXAfterThrust+" "+velocityYAfterThrust);
		
		Double asteroidNewExpectedPosforX=oldX+velocityXAfterThrust;
		Double asteroidNewExpectedPosforY=oldY+velocityYAfterThrust;
		System.out.println("Asteorid expected position :"+asteroidNewExpectedPosforX+" "+asteroidNewExpectedPosforY);		
		
		//old and new positions of the player after thrust should not be same
		assertNotEquals(oldX,newX);
		assertNotEquals(oldY,newY);
		
		//the new position of the player should move by just one notch of veocity
		//assertEquals(asteroidNewExpectedPosforX,newX);
		//assertEquals(asteroidNewExpectedPosforY,newY);
}


	@Test
	public void testCheckCollision()
	{	
		System.out.println("Inside test case 3 no of lives: ");
		Entity asteroid=new Asteroid(new Random());		 
		asteroidGame.registerEntity(asteroid);
		
		Vector2 asteroidPos=asteroid.getPosition();
	    Vector2 playerPos=asteroidGame.player.getPosition();		
		
		System.out.println("Asteroid Postion: "+asteroidPos.x+" "+asteroidPos.y);
		System.out.println("Player position :"+playerPos.x+" "+playerPos.y);
		System.out.println("collision state :"+asteroidGame.player.checkCollision(asteroid));
		
		//as positions are different first time, collision method should return false 
		assertNotEquals(asteroidPos.x, playerPos.x);
		assertNotEquals(asteroidPos.y, playerPos.y);
		assertFalse(asteroidGame.player.checkCollision(asteroid));	
				
		asteroidGame.player=new Player(asteroid.getPosition());
		asteroidGame.registerEntity(asteroidGame.player);
		asteroidGame.player.update(asteroidGame);
		asteroidGame.updateGame();
				
		asteroidPos=asteroid.getPosition();
	    playerPos=asteroidGame.player.getPosition();		
		
		System.out.println("Asteroid Postion: "+asteroidPos.x+" "+asteroidPos.y);
		System.out.println("Player position :"+playerPos.x+" "+playerPos.y);
		System.out.println("collision state :"+asteroidGame.player.checkCollision(asteroid));
		
		assertTrue(asteroidGame.player.checkCollision(asteroid));
	
	}

}
