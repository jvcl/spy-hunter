package spyhunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * ControlPanel class. This class handles all of the game settings selected by the user.
 * Buttons, sliders, labels, check bottons and theirs respctive listeners are in this class * 
 */
public class ControlPanel extends JPanel {

    //Intances variables/objects declaration
    private JLabel labelScore;
    private JLabel carsDodged;
    private JLabel nCrashes;
    private JLabel difficulty;
    private JSlider slider;
    private JButton playPaButton;
    private JButton quitButton;
    private JButton highScoreButton;
    private JButton newGamebuButton;
    private JCheckBox arcadeCheckBox;
    private MainPanel mainPanel;
    private JFrame frame;

    public ControlPanel(MainPanel mainPanel, JFrame frame) {

        this.frame = frame;
        this.mainPanel = mainPanel;

        //JPanel set-up. A BoxLayout is used 
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(200, 300));
        Border lineBorder = BorderFactory.createTitledBorder("Control Panel");
        setBorder(lineBorder);
        setBackground(Color.green);


        //Labels creation
        labelScore = new JLabel("Score: 0");
        carsDodged = new JLabel("Cars dodged: 0");
        nCrashes = new JLabel("Number of Crashes: 0");
        difficulty = new JLabel("Difficulty");

        //Slider creating and settings
        slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setBackground(Color.green);
        slider.setSize(100, 100);
        slider.setToolTipText("Set Difficulty for the game");
        slider.addChangeListener(new SliderListener());

        //Buttons listener class created
        ButtonsListener bl = new ButtonsListener();

        //Buttons creation
        playPaButton = new JButton("Pause");
        quitButton = new JButton("Quit");
        highScoreButton = new JButton("High Score Ranking");
        newGamebuButton = new JButton("New Game");

        //Buttons set-up
        playPaButton.addActionListener(bl);
        playPaButton.setMnemonic('p');
        playPaButton.setToolTipText("Pause or Resume game");
        playPaButton.setEnabled(false);
        quitButton.addActionListener(bl);
        quitButton.setMnemonic('q');
        quitButton.setToolTipText("Quit Game");
        highScoreButton.addActionListener(bl);
        highScoreButton.setMnemonic('h');
        highScoreButton.setToolTipText("View Score Rankings");
        highScoreButton.setEnabled(false);
        newGamebuButton.addActionListener(bl);
        newGamebuButton.setMnemonic('n');
        newGamebuButton.setToolTipText("Start new Game");
        newGamebuButton.setEnabled(true);

        //Checkbox listener class created
        CheckBoxLis listenerCheckBox = new CheckBoxLis();

        //Checkbox creation and set-up
        arcadeCheckBox = new JCheckBox("Arcade Mode");
        arcadeCheckBox.addActionListener(listenerCheckBox);
        arcadeCheckBox.setEnabled(true);
        arcadeCheckBox.setBackground(Color.green);
        arcadeCheckBox.setToolTipText("Activate Arcade Mode");

        //Add components to the control Panel
        add(new JLabel("\n"));
        add(labelScore);
        add(carsDodged);
        add(nCrashes);
        add(new JLabel("\n"));
        add(difficulty);
        add(slider);
        add(arcadeCheckBox);
        add(newGamebuButton);
        add(playPaButton);
        add(highScoreButton);
        add(quitButton);
    }

    //Method that update the labels for the score, number of cars dodged
    //and the number of crashes
    public void setScoreLabel() {

        labelScore.setText("Score is: " + Cars.getScore());
        carsDodged.setText("Cars dodged: " + Cars.getDodged());
        nCrashes.setText("Number of Crashes: " + Cars.getCrashes());
    }

    //Method to call a JOptionPane dialog when the user crash with an enemy car.
    //The method will request the user if he or her wishes to play a new game.
    //If user wants to play a new game, a new game is set up, otherwise the game closes.
    public void checkIfContinue() {

        int n = JOptionPane.showConfirmDialog(
                frame,
                "Ouchh you crash!, Would you like to play again?",
                "Ouchh you crash!",
                JOptionPane.YES_NO_OPTION);
        //Check if the user selected to play again
        if (n == 0) {
            //Set up a new game
            mainPanel.stopGame();
            newGamebuButton.setText("New Game");
            arcadeCheckBox.setEnabled(true);
            slider.setEnabled(true);
            playPaButton.setEnabled(false);
            Cars.crash();

        } else {
            //Thank you message to the user for playing the game
            JOptionPane.showMessageDialog(frame, "I hope you enjoy the game",
                    "Thank you for playing",
                    JOptionPane.INFORMATION_MESSAGE);
            //Close game
            frame.dispose();
        }
    }
    //Class for the checkbok listener.

    private class CheckBoxLis implements ActionListener {

        @Override
        //The action perform method will check if the arcade mode is
        //turn on or off. It will invoke the MainPanel method to notify the Game Panel
        //class and set up a new game
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(arcadeCheckBox)) {

                if (arcadeCheckBox.isSelected()) {
                    //Arcade mode on
                    mainPanel.setArcadeMode(true);

                } else {
                    //Arcade mode off
                    mainPanel.setArcadeMode(false);
                }
            }
        }
    }
    //Class of the slider listener. This class will notify the MainPanel class 
    //of a change of difficulty. MainPanels invokes the GamePanel to set up a new
    //game with the selected difficulty

    private class SliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {

            playPaButton.setText("Pause");
            playPaButton.setEnabled(false);
            newGamebuButton.setEnabled(true);
            mainPanel.setDifficulty(slider.getValue());
        }
    }

    //Class of the Button listener. Each Button have diferent functions as
    //pause the game, stop the game, resume, etc.
    private class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //Play Button instructions. Play the game from a pause state.
            //Pause game from a running state
            if (e.getSource().equals(playPaButton)) {

                //Resume Game
                if (playPaButton.getText().equalsIgnoreCase("Resume")) {
                    mainPanel.playGame();
                    playPaButton.setText("Pause");
                } else {
                    //Pause Game
                    mainPanel.pauseGame();
                    playPaButton.setText("Resume");
                    playPaButton.setMnemonic('r');
                }
            }
            //New Game Button, set up a new game
            if (e.getSource().equals(newGamebuButton)) {
                //Set up a new game
                if (newGamebuButton.getText().contains("New Game")) {
                    playPaButton.setText("Pause");
                    playPaButton.setEnabled(true);
                    highScoreButton.setEnabled(true);
                    slider.setEnabled(false);
                    arcadeCheckBox.setEnabled(false);
                    newGamebuButton.setText("Stop Game");
                    mainPanel.setGame();
                    //Stop the current game
                } else {
                    mainPanel.stopGame();
                    newGamebuButton.setText("New Game");
                    slider.setEnabled(true);
                    arcadeCheckBox.setEnabled(true);
                }
            }
            //Quit Game Button
            if (e.getSource().equals(quitButton)) {
                mainPanel.pauseGame();
                JOptionPane.showMessageDialog(frame, "I hope you enjoy the game",
                        "Thank you for playing",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
            //Show the high score panel and pause the game
            if (e.getSource().equals(highScoreButton)) {

                mainPanel.pauseGame();
                playPaButton.setText("Resume");
                highScoreButton.setEnabled(false);
                mainPanel.updateRanking();
                highScoreButton.setEnabled(true);
                setFocusable(false);
            }
        }
    }
}
