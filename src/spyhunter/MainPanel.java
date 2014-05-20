package spyhunter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * MainPanel Class. This class extends Jpanel and is the main container of the GamePanel
 * and ControlPanel. this class uses a BorderLayout. This class contains several methods
 * to allaw communication between the classes
 */
public class MainPanel extends JPanel {

    //Instances variables
    private GamePanel gamePanel;
    private HighScorePanel highScorePanel;
    private JDialog dialog;
    private HighRanking highScoreRanking;

    public MainPanel(JFrame frame) {

        //Set-up Panel
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(720, 400));
        ControlPanel controlPanel = new ControlPanel(this, frame);

        //Set up components
        gamePanel = new GamePanel(controlPanel, frame, this);
        highScoreRanking = new HighRanking();
        highScorePanel = new HighScorePanel(this, 1);
        dialog = new JDialog(frame, "TOP 10 Ranking");

        //Add components to the Panel
        add(highScorePanel, BorderLayout.WEST);
        add(controlPanel, BorderLayout.EAST);
    }

    //Method to update ranking, it invokes the updateLabels methof of the 
    //HighScorePanel class, also invokes the showScore method
    public void updateRanking() {

        highScorePanel.updateLabels();
        showScore();
    }

    //This methos add the highScorePanel component to this panel and display the 
    //curent scores
    public void showScore() {

        dialog.add(highScorePanel);
        dialog.setLocation(10, 50);
        dialog.setSize(new Dimension(350, 350));
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    //Method to pause game, it invokes the pauseGame method of the GamePanel class
    public void pauseGame() {

        gamePanel.pauseGame();
    }

    //Method to play game, it invokes the playGame method of the GamePanel class
    public void playGame() {

        gamePanel.playGame();
    }

    //Method to set the difficulty of the game, it invokes the setDifficulty method
    //of the GamePanel class
    public void setDifficulty(int value) {
        gamePanel.setDifficultyValue(value);
    }

    //Method to set the game, it invokes the several methods of the GamePanel class
    //In addition, it removes the score panel and add the gamePanel component
    public void setGame() {

        remove(highScorePanel);
        add(gamePanel);
        gamePanel.requestFocus();
        gamePanel.removeAll();
        gamePanel.resetGame();
        gamePanel.repaint();
    }

    //Method return the minimum score of the top 10, it invokes the getMinScore method 
    //of the HighScoreRanking class
    public int getMinScore() {

        return highScoreRanking.getMinScore();
    }

    //Method return an string array of names of the top 10 socores, it invokes 
    //the getNamesArray method of the HighScoreRanking class
    public String[] getNamesArray() {

        return highScoreRanking.getNamesArray();
    }

    //Method return an int array of scores of the top 10 socores, it invokes 
    //the getScoreArray method of the HighScoreRanking class
    public int[] getScoreArray() {

        return highScoreRanking.getScoreArray();
    }

    //Method invokes the addNewScore method of the HighScoreRanking class
    public void addNewScore(int i, String s) {

        try {
            highScoreRanking.addNewScore(i, s);
        } catch (IOException ex) {
            System.out.println("ERROR ADDING A NEW SCORE AT MAINPANEL " + ex);
        }
    }

    //Method set the arcade mode, it invokes the setArcadeMode method of the GamePanel class
    public void setArcadeMode(boolean b) {

        gamePanel.setAracadeMode(b);
    }

    //Method to top game, it invokes the pauseGame method of the GamePanel class
    //In addition, it removes the gamepanel compononent and add the HighScorePanel
    //component
    public void stopGame() {

        gamePanel.pauseGame();
        remove(gamePanel);
        highScorePanel.updateLabels();
        add(highScorePanel);
        repaint();
    }
}