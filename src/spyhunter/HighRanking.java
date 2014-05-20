package spyhunter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * HighRanking class. This class read and writes to the external file to store
 * the top 10 scores. This class cointans method to sort the the data in the file 
 * if required.
 */
public class HighRanking {

    //Instance variables
    private int minScore;
    private String[] namesArray = new String[10];
    private int maxScore;
    private int[] scoreArray = new int[10];
    
    //Constructor that invokes two methos to read data from the external file
    //and sort the file by higher to lower points
    public HighRanking() {
        
        try {
            readData();
            setminMax();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR READING DATA FROM EXTERNAL FILE" + ex);
        }
    }
   
    //Return the score array sorted by points
    public int[] getScoreArray() {
        return scoreArray;
    }
    //Return the minimum score of the 
    public int getMinScore() {
        return minScore;
    }
    //Rturn the name of the players in a String Array
    public String[] getNamesArray() {
        return namesArray;
    }

    //Method to read external file and store the data in arrays
    private void readData() throws FileNotFoundException {

        Scanner fileScan;
        String scoreLine;
        fileScan = new Scanner(new File("src/spyhunter/scores.dat"));
        int i = 0;

        while (fileScan.hasNext()) {

            scoreLine = fileScan.nextLine();
            String del = "[/]";
            String[] line = scoreLine.split(del);
            namesArray[i] = line[0];
            scoreArray[i] = new Integer(line[1]);
            i++;
        }
    }

    //Method to sort the arrays with the highest to the lowest score.
    private void setminMax() {

        int pos = 0;
        int tempMax;
        String tempName;
        boolean match = false;

        //Sort array by Selection
        for (int i = 0; i < scoreArray.length; i++) {

            tempMax = scoreArray[i];

            for (int j = i + 1; j < scoreArray.length; j++) {

                if (scoreArray[j] > tempMax) {
                    tempMax = scoreArray[j];
                    pos = j;
                    match = true;
                }
            }

            if (match) {
                tempName = namesArray[pos];
                scoreArray[pos] = scoreArray[i];
                namesArray[pos] = namesArray[i];
                scoreArray[i] = tempMax;
                namesArray[i] = tempName;
            }
        }
        maxScore = scoreArray[0];
        minScore = scoreArray[9];
    }

    //Method to add a new score to the array, value is stored in the last index.
    //After the value is inserted, the arrays are sorted invoking the setminMax method
    //and then the external file is writting invoking the writeScore method.
    public void addNewScore(int value, String name) throws IOException {

        scoreArray[9] = value;
        namesArray[9] = name;

        setminMax();
        writeScore();
    }

    //Write array of Scores and names to external file
    public void writeScore() throws IOException {

        FileWriter fw = new FileWriter("src/spyhunter/scores.dat");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter outFile = new PrintWriter(bw);

        for (int i = 0; i < 10; i++) {
            outFile.print(namesArray[i] + "/" + scoreArray[i] + "\n");
        }
        outFile.close();
    }
}
