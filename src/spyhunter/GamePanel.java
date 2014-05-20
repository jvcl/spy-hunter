package spyhunter;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JApplet;

/**
 * @author Jorge Andres Valdivia_Quezada Flinders University Student ID: 2058295
 */

/*
 * GamePanel class. This class is handles all grapics of the game (cars, bullets, and roads).
 * PaintComponent method is in this class. In addition, this class set-up the keyboard,
 * mouse listeners and objects cars.
 */
public class GamePanel extends JPanel {

    //Instance Variables for the class GamePanel
    private JFrame frame;
    private MainPanel mainPanel;
    private ControlPanel controlPanel;
    //Array Cars[] will store the cars objects for the game.
    //The maximum numbers of cars will be 13 at the time.
    //The number of cars in each game is determinate by the difficulty selected
    //by the user
    private Cars[] cars = new Cars[13];
    //RoadLanes[] is an array that stores the current state of each road lane.
    //If the current line is free and not cars are in the line a value of 0 is
    //stored. However, when a car is currenlty in the lane a number 1 is stored.
    private int[] roadLanes = new int[13];
    //BulletList is an ArrayList that handles all of the bullets objects
    private ArrayList<Bullet> bulletList;
    //whitePosition will store the current position of the white car in the game
    private int whitePosition;
    //numCarsonRoad represent the current number of cars in the game
    //this value is defined depending on the deifficulty selected by the user.
    //delay represent the timer delay for the game
    //this value is defined depending on the deifficulty selected by the user.
    //tempDelay represent the new delays for the timer when the user acelerate 
    //or break in the arcade modee.
    //offSetDelay represent the new rate of acelaration or break of each car in the
    //arcade modee.    
    private int numCarsonRoad, delay, tempDelay, offSetDelay;
    //Support instances variables/objects
    private int difficulty;
    private Timer timerGame;
    private Timer timerKeyboard;
    private int panelWidth = 520, panelHeight = 400;
    private KeyboardLis keyboard;
    private AudioClip audioClip, audioClip2, audioClip3, audioClip4;
    private URL url, url2, url3, url4;
    private boolean aracadeMode = false;

    //Constructor for the class GamePanel, required instances varianbles are instantiated
    public GamePanel(ControlPanel controlpanel, JFrame frame, MainPanel p) {
        super();

        //The default difficulty when the game starts is 1. This will change when
        //user selects a new difficulty.
        difficulty = 1;

        this.frame = frame;
        this.controlPanel = controlpanel;
        mainPanel = p;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        //BulletList is an ArrayList that will store all bullets objects to be
        //displayed in the screen when the user shoot to cars.
        bulletList = new ArrayList<Bullet>();

        //Timer object instantation 
        timerGame = new Timer(delay, new TimerListener());

        //Listener for Keybaord and Mouse
        addMouseListener(new MouseListe());
        keyboard = new KeyboardLis();

        //Set up of sound clips for the game. For the sound handling the class 
        //AudioCLips is used

        try {
            url = new URL("file", "localhost", "src/spyhunter/shoot.wav");
            url2 = new URL("file", "localhost", "src/spyhunter/explosion.wav");
            url3 = new URL("file", "localhost", "src/spyhunter/error.wav");
            url4 = new URL("file", "localhost", "src/spyhunter/score.wav");

        } catch (MalformedURLException ex) {
            System.out.println("Error in URL format " + ex);
        }
        audioClip = JApplet.newAudioClip(url);
        audioClip2 = JApplet.newAudioClip(url2);
        audioClip3 = JApplet.newAudioClip(url3);
        audioClip4 = JApplet.newAudioClip(url4);
    }

    //Public method to set the difficulty of the game as per user selected
    public void setDifficultyValue(int i) {

        difficulty = i;
    }

    // Public methos to reset the game. ALl variables and objects are 
    //updated to ther initial values for a new game.
    public final void resetGame() {

        //Position is the current position of the white car. For default
        //the white car will be at the center when the game starts.
        whitePosition = 250;

        //Reset the score
        Cars.resetScore();

        //Update the number of cars dodged in the current game.
        Cars.setDodged();

        //Set all the road lanes to 0 (no cars in the road)
        for (int i = 0; i < roadLanes.length; i++) {

            roadLanes[i] = 0;
        }
        setDifficulty();
        setCarArray();

        //Call to to remove all bullets, restart the game and setup new delay
        //as per difficulty selected
        bulletList.removeAll(bulletList);
        timerGame.restart();
        timerGame.setDelay(delay);
        timerGame.start();
    }

    //Private method that populate the Cars array with new cars instances
    //The array will only have a certain number of instances as per the number
    //of cars in the road as per difficulty selected
    private void setCarArray() {

        for (int i = 0; i < numCarsonRoad; i++) {

            cars[i] = setCar();

            cars[i].setX(getFreeLine());
        }
    }

    //PaintComponent that handleas all of the drawing of cars, bullets and lines.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Call the drawLines method to draw white lines for the game representing the number of roads
        drawLines(g);

        //Call the paintBullet method to draw the bullets in the arcade mode
        paintBullet(g);

        //Call to paintWhiteCar method to draw white car at the current position
        paintWhiteCar(g);

        //Call to paintEnemyCars method to draw all of the current enemy cars
        paintEnemyCars(g);
    }

    //Public methos to Pause the game. It will call the stop() methof of the Timer class
    public void pauseGame() {

        timerGame.stop();
        timerKeyboard.stop();
    }
    //This method is in charge of setup new game and request focus to the GamePanel

    public void playGame() {
        timerGame.restart();
        requestFocus();
    }
    //This method will update the boolean variable for the ArcadeMode,
    //In addition, it will set up the keyboard listener

    public void setAracadeMode(boolean aracadeMode) {
        this.aracadeMode = aracadeMode;

        if (aracadeMode) {
            addKeyListener(keyboard);
            requestFocus();
        } else {
            removeKeyListener(keyboard);
        }
    }

    //This method will set up the difficulty of the game as per specifications.
    //It will set the difficulty as per selected by the user. The variable difficulty
    //is update every time the user selects a new dificulty
    public void setDifficulty() {

        switch (difficulty) {

            case 1: {
                numCarsonRoad = 3;
                delay = 100;
                break;
            }

            case 2: {
                numCarsonRoad = 5;
                delay = 100;
                break;
            }

            case 3: {
                numCarsonRoad = 10;
                delay = 100;
                break;
            }
            case 4: {
                numCarsonRoad = 5;
                delay = 50;
                break;
            }
            case 5: {
                numCarsonRoad = 10;
                delay = 50;
                break;
            }

            case 6: {
                numCarsonRoad = 13;
                delay = 50;
                break;
            }

            case 7: {
                numCarsonRoad = 10;
                delay = 25;
                break;
            }

            case 8: {
                numCarsonRoad = 13;
                delay = 25;
                break;
            }
            case 9: {
                numCarsonRoad = 10;
                delay = 1;
                break;
            }
            case 10: {
                numCarsonRoad = 13;
                delay = 1;
                break;
            }

        }
        //tempDelay initial value is the current delay as per selected difficulty.
        //This value will change when the user acelerates or break during the 
        //arcade mode.
        tempDelay = delay;
    }

    //This method will return the Color of the current car (index i) to be paint in the screen.
    private Color getColorCar(int i) {

        Color color = new Color(0);

        if (cars[i].getColor().contentEquals("red")) {
            color = Color.red;
        }

        if (cars[i].getColor().contentEquals("blue")) {
            color = Color.blue;
        }

        if (cars[i].getColor().contentEquals("yellow")) {
            color = Color.yellow;
        }

        if (cars[i].getColor().contentEquals("green")) {
            color = Color.green;
        }

        if (cars[i].getColor().contentEquals("brown")) {
            color = new Color(156, 93, 82);
        }
        return color;
    }

    //This methos will create a new Car instance returned to the Cars array to be stored
    //There are 5 type of cars(including brown car) and two types of obstacles
    //The initial speed and points are as per specifications.
    //Cars instances are randomly created depeing if the arcade mode is on or off
    private Cars setCar() {

        Cars car = null;

        switch (new Random().nextInt(aracadeMode ? 7 : 4) + 1) {

            case 1: {

                car = new Cars(5, "blue", 1);

                break;
            }

            case 2: {

                car = new Cars(8, "green", 2);

                break;
            }

            case 3: {

                car = new Cars(10, "red", 3);

                break;
            }

            case 4: {

                car = new YellowCar(new Random().nextInt(20) + 1, "yellow", 4);

                break;
            }

            case 5: {

                car = new Cars(10, "brown", 0);

                break;
            }

            case 6: {

                car = new Cars(1);

                break;
            }

            case 7: {

                car = new Cars(2);

                break;
            }

        }

        return car;
    }

    //This method is in charge of return a free line road to put a new car instance on it.
    //It scan all of the roads checking wich ones are available and store them in
    //a temp Array. After checking all lines it randomly selects a line to put  a new car
    //and mark that line as not available.
    //All available lines are stored in roadLines arrat as 0, not available ones are
    //stored with a 1
    private int getFreeLine() {

        int count = 0;
        int pos;
        int[] temp = new int[13];

        //For loop to check all available lines
        for (int j = 0; j < roadLanes.length; j++) {

            if (roadLanes[j] == 0) {

                temp[count] = j;
                count++;

            }
        }
        //Assing a new location randomly 
        pos = temp[new Random().nextInt(count)];

        roadLanes[pos] = 1;

        return pos;
    }

    //Method to draw white lines in the screen that represent the road lines
    //when called this method needs an instance of the Graphics class to
    //draw the lines.
    private void drawLines(Graphics g) {

        g.setColor(Color.white);
        for (int i = panelWidth / 13; i < panelWidth; i = i + 40) {
            for (int j = 0; j < panelHeight; j += 20) {

                g.drawLine(i, j, i, j + 10);
            }
        }
    }

    //Method to paint the bullets in the screen
    //when called this method needs an instance of the Graphics class to
    //draw the lines.
    private void paintBullet(Graphics graphics) {

        //If to check if the user has shooted at the game
        if (!bulletList.isEmpty()) {

            //Foor loop to check all the bulleta to be displayed and draw them 
            //in the screen
            for (int i = 0; i < bulletList.size(); i++) {

                graphics.setColor(Color.red);
                graphics.drawLine(bulletList.get(i).getxPosition(),
                        bulletList.get(i).getyPosition(), bulletList.get(i).getxPosition(),
                        bulletList.get(i).getyPosition() - 20);
            }
        }
    }

    //Method that paint the white car in the screen as per mouse position when
    //interacting with the user.
    //when called this method needs an instance of the Graphics class to
    //draw the lines.
    private void paintWhiteCar(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(whitePosition, 360, 30, 40);
        g.setColor(Color.black);
        g.fillRect(whitePosition + 5, 365, 20, 10);
        g.fillRect(whitePosition + 5, 385, 20, 10);

    }

    //Method that paint the enemy cars in the scree as per difficulty selection
    //when called this method needs an instance of the Graphics class to
    //draw the lines.
    //Type of cars are difined in the Cars class
    //0 is a Normal car (it can be the colors as specified in the specification
    //1 is a men at work sign represented as black rectangle.
    //2 is a hole represented as a black rectangle
    private void paintEnemyCars(Graphics g) {

        for (int i = 0; i < numCarsonRoad; i++) {

            if (cars[i].getType() == 0) {

                g.setColor(getColorCar(i));
                g.fillRect(cars[i].getX() * 40 + 5, cars[i].getY(), 30, 40);
                g.setColor(Color.black);
                g.fillRect(cars[i].getX() * 40 + 10, cars[i].getY() + 5, 20, 10);
                g.fillRect(cars[i].getX() * 40 + 10, cars[i].getY() + 25, 20, 10);
            }

            if (cars[i].getType() == 1) {
                g.setColor(Color.black);
                g.fillRect(cars[i].getX() * 40 + 10, cars[i].getY(), 20, 20);

            }

            if (cars[i].getType() == 2) {
                g.setColor(Color.black);
                g.fillRect(cars[i].getX() * 40, cars[i].getY(), 40, 10);
            }
        }
    }

    //Check the current postion of the white car and restrict the withe car to the
    //boundaries of the game panel.
    private int checkX(int x) {

        if (x <= 15) {
            x = 0;

        } else if (x >= 505) {
            x = 490;

        } else {

            x = x - 15;
        }

        return x;
    }

    //Private class that uses a JOptionPane to display a dialog ands request the
    //user to enter his or her name.
    //The string entered will be checked in a infinite while loop until the user
    //enter a correct name or not empty
    private String checkNameInput() {

        String name;

        while (true) {

            name = JOptionPane.showInputDialog(frame,
                    "Ouchh you crash!, but you are in the "
                    + "TOP 10\n\nEnter Your Name", "Ouchh you crash!",
                    JOptionPane.INFORMATION_MESSAGE);

            //Check if the user has entered a set of characters, a minimum of
            //1 charachter is requiered for the name
            if (name != null && name.length() > 0 && !name.trim().isEmpty()) {

                break;

            } else {
                //if the user do not satisfies the name requirements the user
                //will be warned with and asked to enter the name again.
                JOptionPane.showMessageDialog(frame, "Enter your name again! "
                        + "I did not get your name!", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        return name;
    }

    //This method assist with setting up when to track with a collision with the
    //white car. As the cars are different sizes (cars and obstacles) they have 
    //they need to track for a collision at diferentes positions.
    private int getYcoordinate(int carType) {

        int y = 0;

        if (carType == 0) {
            y = 320;
        }
        if (carType == 1) {
            y = 340;
        }
        if (carType == 2) {
            y = 350;
        }
        return y;
    }


    /**
     * *************************************************************************
     * KEYBOAD, MOUSE AND TIMER LISTENER CLASSES
     * *************************************************************************
     */
    //Keyboars listener in the arcade mode to enable the acelaration and break of 
    //white car. The acelaration and break is achieved by decrasing or increasing
    //the timer delay of the game.
    private class KeyboardLis implements KeyListener {

        public KeyboardLis() {

            //timer Keybaord is an instante of the Timer class that will track 
            //how much time the user acelarate or break. It will decrease or increase 
            //the timer every 100 miliseconds
            timerKeyboard = new Timer(100, new KeyboardTimerLis());
        }

        @Override
        public void keyPressed(KeyEvent e) {

            //When user press the "W" in the keyboard the timer fires up and 
            //and it will drecrease the Game delay by 3
            if (e.getKeyCode() == KeyEvent.VK_W) {
                offSetDelay = -3;
                timerKeyboard.start();
            }

            //When user press the "S" in the keyboard the timer fires up and 
            //and it will Iincrease the Game delay by 3
            if (e.getKeyCode() == KeyEvent.VK_S) {
                offSetDelay = 10;
                timerKeyboard.start();
            }
            //When user press the "Space" in the keyboard a new bullet object
            //will be created and a sound will be played
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                audioClip.play();
                bulletList.add(new Bullet(whitePosition + 15));
            }
        }

        //Method that stop the keyboard timer when the user released the key when
        //acelerating or breaking
        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                timerKeyboard.stop();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    //Class of the Keyboard Timer. This class is created when the user
    //acelerates or break and updated every 100 miliseconds
    private class KeyboardTimerLis implements ActionListener {

        @Override
        //En each interation the game will be updated to a new delay. This will
        //set the game speed. When acelaratin the minimum delay is 1
        public void actionPerformed(ActionEvent e) {

            tempDelay = tempDelay + offSetDelay;

            if (tempDelay <= 1) {
                tempDelay = 1;
            }
            //Set the game delay to the new 
            timerGame.setDelay(tempDelay);
        }
    }
   
    //Private class that implements MouseListener. This class set up the
    //MouseMotion listener. This was made to control the position of the white
    //car in the screen. The white car only will move when the user click and drag
    //the mouse on top of the current location of the car. If the user clicks and 
    //drags the mouse in other location of the screen the white car will not move.
    private class MouseListe implements MouseListener {

        private MouseMotion mouseMotion;

        public MouseListe() {
            
            mouseMotion = new MouseMotion();

        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getX() > whitePosition - 5 && e.getX() <= whitePosition + 35) {
                addMouseMotionListener(mouseMotion);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            removeMouseMotionListener(mouseMotion);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }    
    
    //Class of the Mouse Listener, it will update the postion of the white car
    //when the user click the mouse and drag it.
    private class MouseMotion implements MouseMotionListener {

        public MouseMotion() {
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            //The position of the white car will be checked with the boundaries
            //of the game panel and updated.
            whitePosition = checkX(e.getX());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    //Game Listener class for the timerGamer object created in the GamePanel constructor.
    //This class will handle all of the score update, number of cars dodged, check if 
    //an enemy car has crash with the white car and awards points when dodged.
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //For loop that will go throught every car in the came and will
            //check for crash with bullets, crash with white car, update scores
            //update the Y position of the car, etc.
            for (int i = 0; i < numCarsonRoad; i++) {

                //An Array of is created to store the number of crashes with bullets 
                //this list will include the car type (car or obstacle) and the position
                //in the screen where the crash was found
                int[] bulletCrashList = CrashCheck.bulletCrash(cars[i].getX(),
                        cars[i].getY(), bulletList, cars[i].getType());

                //If the array at the position 0 equals -1 os becuase there was not
                //found a crash with a bullet
                if (bulletCrashList[0] != -1) {

                    //This will check if a crash with a bullet was with a car.
                    //If not the crash was with an obstacle
                    if (bulletCrashList[0] == 0) {

                        //If the bullet crash was with a brown car, the user is get additional 20 points
                        //and play a sound
                        if (cars[i].getColor().contains("brown")) {

                            cars[i].updateScore(20);
                            audioClip4.play();

                        } else {

                            //if the bullet crash is with a car that is not brown 
                            //the user get -5 points
                            cars[i].updateScore(-5);
                            audioClip3.play();
                        }
                    }
                    //The bullet is removed from the bullet list
                    bulletList.remove(bulletCrashList[1]);

                    //This if, will remove the car from the game and set up a new
                    //car in the available position                    
                    if (cars[i].getType() != 1 && cars[i].getType() != 2) {
                        roadLanes[cars[i].getX()] = 0;
                        cars[i] = setCar();
                        cars[i].setX(getFreeLine());
                    }
                }

                //This will get the cordinate to check for collitions with the white car
                int yCoordinate = getYcoordinate(cars[i].getType());

                //Check if the current position is greater than the requiered to check
                //for the type of car if so will check for crashs and awards points 
                //when the white car dodge this car
                if (cars[i].getY() >= yCoordinate && cars[i].getY() < 400) {

                    //Check if there is a crash with the current position of the
                    //white car. if a crash is found the gamer timer will stop and
                    //play a sound
                    if (CrashCheck.carCrash(whitePosition, cars[i].getX())) {

                        audioClip2.play();
                        timerGame.stop();

                        //Check if the current score in the game is greater
                        //than the minimum of the top 10 scores.
                        //If so, will get the current score and ask the name to the user
                        //through the checkNameInput method
                        if (Cars.getScore() > mainPanel.getMinScore()) {

                            mainPanel.addNewScore(Cars.getScore(), checkNameInput());
                        }
                        //After added the score to the database, will asl the user 
                        //if he wants to continue and play a new game
                        controlPanel.checkIfContinue();
                    }
                }

                //This if, checks if the current car has passed the 400 coordinate Y
                //in the screen, if so It will update the score, update the number of cars dodged
                //set the current line to available and create a new car object with 
                //a new random position.
                if (cars[i].getY() >= 400) {

                    cars[i].addScore();

                    if (cars[i].getType() != 1 && cars[i].getType() != 2) {
                        cars[i].addDodged();
                    }
                    roadLanes[cars[i].getX()] = 0;
                    cars[i] = setCar();
                    cars[i].setX(getFreeLine());
                }

                //If the car was not in a crash will increase the position of the 
                //car in the screen
                cars[i].incrementY();
            }

            //This loop will remove all bullets objects that are outside of the top
            //of the game panel and increase the position by invoking the updateYposition 
            //in the bullet class
            for (int i = 0; i < bulletList.size(); i++) {

                bulletList.get(i).updateYPosition();

                if (bulletList.get(i).getyPosition() < 0) {

                    bulletList.remove(i);
                }
            }
            //Set the new scores
            controlPanel.setScoreLabel();
            //Repaint the screen
            repaint();
        }
    }
}
