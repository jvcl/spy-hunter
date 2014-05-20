1. Introduction
	This is a Test Plan for the Java game SpyHunter. This is a classic arcade game where the objective is to avoid collisions with others cards and obstacles in the way. In addition, players can have the option to destroy cars in the road with a special gun. 
	This game was ported to a Java version by Jorge Valdivia which tries to simulate the basic functions of the game.

	This document describes the testing strategy and approaches. It also contains various resources required , schedules and risk associated.

2. Testing objectives
	The focus of the this test plan is to document the testing schedule of existing feature to find software defects. In addition, this plan documents testing to support new features of the game that will allow extends the game for future releases. 

3. Required Resources 
	* Computer System with Java VM 1.7 installed.
	* Netbeans 8.0 installed
	* Internet access
	* Github Account, Github Local Repository and Remote Repository.
	* Student to write Test and source code.
	* Features to be tested 
	
	The features to be tested are:
	* Game Flow
		* Game starts
		* Top scores load
		* Game pauses
		* Game controls
		* Keyboard input
		* Mouse Input
	* Collision detection
		* Collision between player car and generated cars
		* Collisions between player car and obstacles
		* Collisions between bullets and generated cars
	* Cars positions
		* Player car is always in the the game panel
		* Generated car are in the game panel
	* Cars speeds
		* Speed of cars is incremented when player accelerates
	* Bullets positions
		* Bullet moves in the same direction of player’s car 
		* Bullet velocity
		* Bullets do not destroy obstacles
	* Score System
		* When cars are dodge score is incremented
		* When wrong cars are destroyed score is decremented
	* Ranking System
		* Scores of player are saved
		* Scores of players are loaded when game starts
		* Scores are sorted by greater scores to lower
	* Random creation of cars
		* Random creation of cars in the screen
		* Different cars are created in the screen
	* Random creation of obstacles
		* Obstacles are randomly created in the screen
	* Strings input
		* Names of players are correctly entered to the game

4. Testing Methodology
	* White box testing
	* Unit Testing
	* Functional Testing
	* Regression Testing
	* Usability Testing
	* Performance Testing
	* Install/Uninstall testing
	* Security Testing
	* Compatibility Testing

5. Testing Methodologies not considered
	* Stress Testing
	* Recovery Testing
	* Beta Testing
	* Comparison Testing
	* Load Testing

6. Features not to be tested
	* File I/O
	* Java Swing Interfaces
	* Sound interface
	* Graphics interface
	* Multiplayer
	* Online Play

7. Deliverables
	* Game instructions
	* Software specification
	* Tests Log
	* Commit Log

8. Risk and Dependences
	* Risks
		* Unable to acquire the necessary level the skill and become ready to test
		* Unable to acquire some of the necessary hardware and software required for integration and system testing
		* Timming issues, delay in writing tests. 
		* Bad project management 
		* Lack of prioritization
		* Misunderstanding of requirements.
		* Hardware Failure 

9. Dependences
	* Student to write test cases
	* Java virtual machine installed
	* System with access to internet


