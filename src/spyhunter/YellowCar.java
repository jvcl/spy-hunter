package spyhunter;

import java.util.Random;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * YellowCar Class. This class extends the Cars class to create a new child YellowCar
 * The child class override the incrementY method for a custom one.
 */
public class YellowCar extends Cars {

    private int POINTS = 4;

    public YellowCar(int SPEED, String COLOR, int points) {
        super(SPEED, COLOR, points);
    }

    @Override
    //Override the parent method and set the value of Y coordinate to a random increment
    public void incrementY() {

        y = y + new Random().nextInt(20) + 1;
    }
}
