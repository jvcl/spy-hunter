package spyhunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * HighScorePanelclass. This class extends JPanel and show to the user the top 10
 * ranking
 */
public class HighScorePanel extends JPanel {

    //Instance variables
    private MainPanel mainPanel;
    private JLabel label;
    private JLabel[] labelScoreArray = new JLabel[10];
    private JLabel[] labelNameArray = new JLabel[10];

    public HighScorePanel(MainPanel panel, int source) {

        mainPanel = panel;
        
        //Setting for the panel
        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(520, 400));
        //Grid layout is used to display the data
        setLayout(new GridLayout(0, 4));
                    
        //Label setup
        label = new JLabel("TOP 10");
        setLabels();
        addLabelsScore();
    }
    //Private method that creates all of the labels for the ranking
    //Labels are scored in two arrays
    private void setLabels() {

        String[] names = mainPanel.getNamesArray();
        int[] score = mainPanel.getScoreArray();

        for (int i = 0; i < 10; i++) {
            labelNameArray[i] = new JLabel(names[i]);
            labelScoreArray[i] = new JLabel(Integer.toString(score[i]));
        }
    }

    //Update the labels in the panel when a new score is recorded
    public void updateLabels() {

        String[] names = mainPanel.getNamesArray();
        int[] score = mainPanel.getScoreArray();

        for (int i = 0; i < 10; i++) {

            labelNameArray[i].setText(names[i]);
            labelScoreArray[i].setText(Integer.toString(score[i]));
        } 
    }

    //Add the labels to the panel
    private void addLabelsScore() {

        add(new JLabel(""));
        add(new JLabel(""));
        add(label);
        add(new JLabel(""));

        add(new JLabel(""));
        add(new JLabel("Ranking"));
        add(new JLabel("Name"));
        add(new JLabel("Score"));

        for (int i = 0; i < 10; i++) {

            add(new JLabel(""));
            add(new JLabel(" N." + (i + 1)));
            add(labelNameArray[i]);
            add(labelScoreArray[i]);
        }
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
    } 
}
