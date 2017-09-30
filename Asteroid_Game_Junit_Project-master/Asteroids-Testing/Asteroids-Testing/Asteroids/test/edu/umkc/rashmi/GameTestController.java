package edu.umkc.rashmi;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.psnbtech.Game;
import org.psnbtech.entity.Asteroid;
import org.psnbtech.entity.Bullet;
import org.psnbtech.entity.Entity;
import org.psnbtech.entity.Player;
import org.psnbtech.util.Vector2;

public class GameTestController {
 
	Game asteroidGame;
	Player player;
	
	@Before
	public void setUp() throws Exception {
		asteroidGame=new Game();
		asteroidGame.initGame();
		player=asteroidGame.getPlayer();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdateGameLevel() throws InterruptedException {		
		
		System.out.println(" inside level test case");
		//remove existing asteroid
		removeAsteroids();		
		//first update to remove dead entities
		asteroidGame.updateGame();		
		//second call to make level up
		asteroidGame.updateGame();		
		//as all existing asteroids are removed the level should be automatically updated
		 
		System.out.println("removevel info:"+asteroidGame.getLevel()+" "+asteroidGame.getEntities());
		assertEquals(1, asteroidGame.getLevel());
		
	}
	
	@Test
	public void testUpdateGameScore() throws InterruptedException {	
		 System.out.println("Inside score test case");
		 //removing all asteroids so that we can check score by hitting one large asteroid		
		  
		 //check whether the score getting updated properly or not
		 int oldScore=asteroidGame.getScore();
		
		 System.out.println("player position"+player.getPosition().x+" "+player.getPosition().y);
		 System.out.println("Score :"+asteroidGame.getScore());				
		 System.out.println("Total entities before hit:"+asteroidGame.getEntities());
				
		Asteroid asteroid=new Asteroid(new Vector2(player.getPosition().x,100),player.getVelocity());
		asteroidGame.registerEntity(asteroid);		
		asteroidGame.updateGame();
		
		Double expectedScore=(double)oldScore+asteroid.getKillScore();
		System.out.println("Score before hit:"+asteroidGame.getScore()+asteroidGame.getEntities());
				
		//start firing
		for(int i=0;i<20;i++)
		{   
			Thread.sleep(100);
			Entity bullet=new Bullet(player, player.getRotation());
			player.update(asteroidGame);
			asteroidGame.registerEntity(bullet);
			asteroidGame.updateGame();
			
		}			
		
		System.out.println("Score after adding bullets"+asteroidGame.getScore()+" "+asteroidGame.getEntities());	    
		Double currentScore=(double)asteroidGame.getScore();
		System.out.println("Score after hit:"+asteroidGame.getScore()+" "+expectedScore+" "+asteroidGame.getEntities().size());
		
		assertEquals(expectedScore,currentScore);
	}
	
	@Test
	public void testUpdateGameCollision() throws InterruptedException {
		
		System.out.println(" inside collision test case");

		/* checking whether update method calling collision method properly or not
		 * I am adding player at one of the asteroid position and then called update game
		 * as their postions are same. Updategame will call handlecollision and then decrement 
		 */
		Double oldScore= (double)asteroidGame.getScore();
		Entity asteroid=new Asteroid(new Random());		 
		asteroidGame.registerEntity(asteroid);		
		asteroidGame.player=new Player(asteroid.getPosition());	
		asteroidGame.registerEntity(asteroidGame.player);		
		
		asteroidGame.player.update(asteroidGame);
		
		//before collision i should be three
		assertEquals(3, asteroidGame.lives);
		
		asteroidGame.updateGame();
		
		System.out.println("lives after collision: "+asteroidGame.lives);
	    assertEquals(2, asteroidGame.lives);
	    
	    Double newScore=(double)asteroidGame.getScore();
	    
	    //as only one asteroid and player got collided, score should not update
	    //assertEquals(oldScore, newScore);
	    
		
	}
	
	@Test
	public void testUpdateGameEntities() throws InterruptedException {
		       System.out.println(" inside entities test case");
		        //adding more asteroids
		
				for(int i=0;i<5;i++)
				{
					Asteroid a=new Asteroid(new Random());
					asteroidGame.registerEntity(a);
				}
							
				asteroidGame.updateGame();
				System.out.println("entities: "+asteroidGame.entities+" "+asteroidGame.pendingEntities.size());
				
				assertEquals(0, asteroidGame.pendingEntities.size());
				//five asteroids we have added  and one player
				assertEquals(6, asteroidGame.entities.size());
	}
	

	public void removeAsteroids()
	{
		for(Entity e:asteroidGame.entities)
		{
			//System.out.println("class"+e.getClass());
			if(e.getClass().equals(Asteroid.class))
			{	e.needsRemoval=true;
			    System.out.println("removed"+e.getClass());
			}
			
		}
		
		asteroidGame.updateGame();
	}
	
	public void removePlayer()
	{
		for(Entity e:asteroidGame.entities)
		{
			//System.out.println("class"+e.getClass());
			if(e.getClass().equals(Player.class))
			{	e.needsRemoval=true;
			    System.out.println("removed"+e.getClass());
			}
			
		}
		
		asteroidGame.updateGame();
	}
}
