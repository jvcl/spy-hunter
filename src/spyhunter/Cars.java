package spyhunter;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * Cars class. This class is the blueprint for all of the car objects and obstacles.
 * All cars objects have a color, speed, and point to be awarded. In addtion, each
 * instance of this class will have a X and Y position
 * Three static variables are introduced to keep track of the total score of the game,
 * number of crashes and number of cars dodged.
 */
public class Cars {

    //Constant variables
    private String COLOR;
    private int POINTS;
    private int SPEED;
    //Type 0 for cars, 1 for obstacles, 2 for men at work;
    private int TYPE;
    protected int y = -40;
    private int x = 0;
    //Static Variables
    private static int score = 0;
    private static int nOfCrashes = 0;
    private static int dodged = 0;

    //Constructor to set the car type and all of the defult propierties of
    //the car
    public Cars(int SPEED, String COLOR, int POINTS) {

        this.SPEED = SPEED;
        this.COLOR = COLOR;
        this.POINTS = POINTS;
        TYPE = 0;
    }

    //Constructor to set the obtucle type and all of the defult propierties of
    //the obstucle
    public Cars(int TYPE) {

        this.TYPE = TYPE;
        SPEED = 10;
        POINTS = 0;
    }

    /*
     * *************************************************************************
     * MUTATORS METHODS 
     ***************************************************************************
     */
    
    //Set value of Y to -40 (initial value of Y)
    public void setY() {

        y = -40;
    }
    //Set the X position of the car or obstucle in the screen. This value range from
    //0 to 13 which represent in which line the cars or obstucle is running
    public void setX(int pos) {

        x = pos;
    }
    //Static method to reset the number of cars dodged to 0
    public static void setDodged() {

        dodged = 0;
    }
    //Increase the number of cars dodges by 1
    public void addDodged() {
        dodged++;
    }
    //Increase the number of cars crashes dodges by 1
    public static void crash() {

        nOfCrashes++;
    }

    //Increase the score when a car is dodged
    public void addScore() {

        score += POINTS;
    }

    //Increase the score by the value point. This value is determinated
    //when the user shoot an enemy car in the arcade mode
    public void updateScore(int point) {

        score += point;
    }
    //Reset score to 0
    public static void resetScore() {

        score = 0;
    }
    //Increment the position of the car in the Y coordinate in relation to the
    //speed of the car
    public void incrementY() {

        y = y + SPEED;
    }

    /*
     * *************************************************************************
     * ACCESSOR METHODS 
     ***************************************************************************
     */
    
    //Get the number of crashes in the game
    public static int getCrashes() {

        return nOfCrashes;
    }
    //Get the location of the car or obstacle in the X coordinate
    public int getX() {

        return x;
    }
    //Get the location of the car or obstacle in the Y coordinate
    public int getY() {

        return y;
    }
    //Get the current score of the game.
    public static int getScore() {

        return score;
    }
    //Get the speed of the car
    public int getSpeed() {

        return SPEED;
    }
    //Get the points of the car to be awarded to the user when dodged
    public int getPoints() {

        return POINTS;
    }
    //Get the color of the car
    public String getColor() {

        return COLOR;
    }
    //Get the type of the car
    public int getType() {
        return TYPE;
    }
    //Get the number of cars dodged
    public static int getDodged() {
        return dodged;
    }
}
