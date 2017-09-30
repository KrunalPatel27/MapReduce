# AsteroidJunitProject


###  Assumptions:
The player and asteroid should not move at high speed as it seems to be.Score should be updated if bullet and asteroid get hit accordingly.Any collision between asteroid and player will result in life loss

As part of this project seven test cases are done and out of which three test cases are failing. These are
described below:

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/testcasesresult.JPG)


Here are the bugs found in the code as per my test cases logic: 

### Failures Scenario:

1) The score get updated when asteroid and player collides. I have found it through test case 2.

As part of this I have added Player at one position and asteroid at the same position as player. Then I have called updateGame method of game which will check for collision and then lives got decremented. However the score should not have updated by 20 . The application added the large asteroid kill value to total score which is not correct.

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/testcase2.JPG)



[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/GameTestController.java)



2) Asteroid is moving at high speed than expected. This is tested by Test case 5.

As part of this I have added asteroid , got its old velocity and position. After that I updated the game.
The new positions expectation are
New position = old position +velocity;
However for X coordinate , it will getting 3 point up and on Y axis it is 1 point up. Because of which asteroids are moving with very high speed before the player can even fire.

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/testcase5.JPG)


[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/EntityTestController.java)



3) When player thrust is enabled, the player moves by 3 times as velocity is multiplied by 3 while adding it to position. This is tested by test case 6.
As part of this I have thrusted the player and then called update game. After this the old and new positions should not be same.
Also the new position expected coordinated should move by one notch of velocity which did not happen.


![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/testcase6.JPG)

[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/EntityTestController.java)


### Success Scenario:

1) Collision between bullet and large asteroid yielding correct score
As part of this I have created asteroid at same position as player on X axis and then fired bullets. Then called updateGame method . It checked for collision and updated score.

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/success1.JPG)


[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/GameTestController.java)


2) Test collision check flag between asteroid and player
As part of this I have added player an asteroid at different position and checked for collision. It did not happen.
Also I have added player and asteroid at same position. The checkCollision method returned true.

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/success2.JPG)

[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/EntityTestController.java)


3) Test Game level after all enemies are dead

As part of this I have killed all asteroids and then checked the game level. It should go one notch up.

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/success3.JPG)

[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/GameTestController.java)

4) Checking for Game entities.

As part of this I have added five asteroids and updated game. Then checked for existing entities and pending entities.

![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/success4.JPG)

[Source Code](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/Asteroids-Testing/Asteroids-Testing/Asteroids/test/edu/umkc/rashmi/GameTestController.java)


### The following functions are performed by updateGame method of Entity class.:

o Setting and resetting entities list and pending entities list

o Handle collisions and in reverse update score

o Check for collisions

o Making level up in case all enemies are dead

o Checking for is game over or not and taking corresponding actions.

Following four test case were checked for the same: 


The following process was followed to test each case:

### testUpdateGameEntities

Added five asteroids on my own and then checking for all entities and pending entities.
As updateGame() clear the pendingEntities and add all the pendingEntities to entities, the same get updated.
                       
                      assertEquals(0, asteroidGame.pendingEntities.size());
                      assertEquals(6, asteroidGame.entities.size());

### testUpdateGameCollision

Created one asteroid at random position and added player at the same position as asteroid. As the distance between them is zero, update method will kill player hence decrementing the number of lives. Also as player and asteroid collision should have resulted in player life loss and no score update.Hence this is a bug that when a player and asteroid collide, the score get updated. This is tested by this usecase.
                            assertEquals(2, asteroidGame.lives);
                            assertEquals(oldScore, newScore);

### testUpdateGameScore

Added one large asteroid. The position for the same is decided by player position.Asteroid X axis position is same as player and Y position is given by me. Now we have created bullets.After update the bullets should hit the asteroid and the score should get updated to killed asteroid kill value.

                          Double expectedScore=(double)oldScore+asteroid.getKillScore();
                          //after kill
                          assertEquals(expectedScore,currentScore);
### testUpdateGameLevel

As part of this testcase , we have first killed all the asteroids and then checked whether the level got up or not.
To kill asteroids we need to make property of entity i.e needsRemoval as true.Update game method will remove all dead entities and on updateGame method call again as there as no enemies the level should go up.
                                    //remove existing asteroid
                                    removeAsteroids();
                                    assertEquals(1, asteroidGame.getLevel());
                                    

## screenshot of the result of running your test suite.


![](https://github.com/rashmitripathi/Asteroid_Game_Junit_Project/blob/master/screenshots/smttestsuite.jpg)
