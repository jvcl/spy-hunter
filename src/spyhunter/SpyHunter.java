package spyhunter;

import javax.swing.JFrame;

/**
 * @author Jorge Andres Valdivia_Quezada
 * Flinders University
 * Student ID: 2058295
 */

/*
 * Spyhynter Class. This class extends creates the frame that contains the MainPanel
 * and set it visible.
 */
public class SpyHunter {

    public static void main(String[] args) {

        JFrame frame = new JFrame("SpyHunter Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainPanel(frame));

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
