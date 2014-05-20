package spyhunter;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * Bullet class. This class is the blueprint for all of the bullets shoot by the
 * user in the arcade mode. Everytime the user shoot an instance of this class 
 * will be created. Each bullet will have an x and y position in the screen.
 * X position is determinated when the user shoot the bullet.
 */
public class Bullet {

    //The initial value of the x is the location of the white car at the moment of
    //shooting. This is set up at the constructor.
    //The default Y position is a 360.
    private int xPosition, yPosition = 360;

    public Bullet(int xPosition) {

        this.xPosition = xPosition;
    }

    //Accesot methof for X position
    public int getxPosition() {
        return xPosition;
    }

    //Accesot methof for Y position
    public int getyPosition() {
        return yPosition;
    }
    //Update Y position in the screen.
    public void updateYPosition() {
        yPosition = yPosition - 20;
    }
}
