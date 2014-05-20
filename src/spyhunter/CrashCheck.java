/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spyhunter;

import java.util.ArrayList;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * CrashCheckPanel class. This class has two static methods that check for
 * collitions between the white car and the enemy cars (carCrash method)
 * and check for collitions between the bullets and the enemy cars (bulletCrash method)
 */
public class CrashCheck {

    //Static method that check for crashed between a car and the user car. The
    //formal parameters are the postion of the white car of the screen, the position
    //opf the enemy car in the screen
    public static boolean carCrash(int whiteX, int carX) {

        boolean crash = false;
        //CarX represent the index of the car to be analysed. The index can be from 0 to 12
       
        //Position of the car in the screeen
        carX = carX * 40 + 5;

        //Check for crash in the left side of the white car
        if (whiteX >= carX && whiteX <= carX + 30) {
            crash = true;
        }

        //Check for crash in the right side of the white car
        if (whiteX + 30 >= carX && whiteX + 30 <= carX + 30) {
            crash = true;
        }
        return crash;
    }

    //Static method that returns a integer Array. This method check for a collition 
    //between an enemy car and a bullets. The formal parameters are the location of the 
    //enemy car and an ArrayList of all of the bullets inm the current screen.
    //It checks if one bullets is in the range of an enmy car.
    //The method return an Array with the position of the car where a crash was found and
    //the type of car.
     //if not crash found return -1
    public static int[] bulletCrash(int carX, int carY, ArrayList<Bullet> bulletArray, int type) {
       
        carX = carX * 40 + 5;
        int[] returnList = new int[2];
        returnList[0] = -1;

        //Check if there are bullets in the screen
        if (!bulletArray.isEmpty()) {

            for (int i = 0; i < bulletArray.size(); i++) {

                //Check type of car
                switch (type) {

                    case 0: {
                        //Check with a crash with a car
                        if ((bulletArray.get(i).getxPosition() >= carX 
                                && bulletArray.get(i).getxPosition() <= carX + 30)
                                && bulletArray.get(i).getyPosition() <= carY + 40) {
                            
                            returnList[0] = 0;
                            returnList[1] = i;
                        }
                        break;
                    }
                    case 1: {
                        //Check with a obstavcle type 1 with the bullets in the screen.                        if ((bulletArray.get(i).getxPosition() >= carX 
                        if ((bulletArray.get(i).getxPosition() >= carX 
                                && bulletArray.get(i).getxPosition() <= carX + 20)
                                && bulletArray.get(i).getyPosition() <= carY + 20) {

                            returnList[0] = 1;
                            returnList[1] = i;
                        }

                        break;
                    }
                    case 2: {
                        //Check with a obstavcle type 2 with the bullets in the screen.
                        if ((bulletArray.get(i).getxPosition() >= carX 
                                && bulletArray.get(i).getxPosition() <= carX + 40)
                                && bulletArray.get(i).getyPosition() <= carY + 10) {
                            returnList[0] = 2;
                            returnList[1] = i;
                        }

                        break;
                    }
                }
            }
        }
        //Returnt the car type where a crash was found and the position in the sceen
        return returnList;
    }
}
