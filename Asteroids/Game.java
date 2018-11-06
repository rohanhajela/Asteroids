import java.lang.Object;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

 public class Game extends JPanel implements ActionListener{
        private int DisplayHeight = 800;
        private int DisplayWidth = 1280;
        private int ButtonHeight = 50;
        private int ButtonWidth = 200;
        public boolean[] pressed;

        private Button play = new Button("PLAY");
        private Button back = new Button("BACK");
        private Button HighScore = new Button("HIGH SCORE");
        private Button Quit = new Button("QUIT");
        private Button playAgain = new Button("PLAY AGAIN");
        private Label scoreLabel;
        private Label Score;
        private JLabel over = new JLabel("GAME OVER");
        private Label scoreText[] = new Label[10];
        private boolean playing = false;
        private boolean showScores = false;

        private Label currentScore = new Label("Score: ");

        private final int astCount = 20;
        ArrayList<Asteroid> asts = new ArrayList<Asteroid>();
        ArrayList<Laser> lasers = new ArrayList<Laser>();
        Spaceship player;
        private int scoreList[] = new int[10];

        private int score = 0;

        public Game() {
            loadpics();
            loadScores();

            Timer timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //System.out.println(score);
                        currentScore.setText("Score: " + score);
                        currentScore.setVisible(true);
                        currentScore.setSize(100,40);
                        currentScore.setLocation(0,0);
                        currentScore.setForeground(Color.WHITE);
                        currentScore.setFont(new Font("Serif", Font.PLAIN, 18));
                        add(currentScore);
                    moveAsteroids();

                    if (playing) {
                        moveLasers();
                        movePlayer();
                        destroyLasers();
                        destroyAsteroids();
                        checkHit();

                        if (asts.size() == 0) {
                            initAsteroids();
                        }

                    }
                    repaint();
                }
            });
            timer.start();

            //keeps track of what keys are pressed
            pressed = new boolean[4];
            for (int i=0; i<4; i++) {
                pressed[i] = false;
            }
           
        }

        public void loadScores() {

            try {
                Scanner sc = new Scanner(new File("src/Resources/scores.txt"));
                for (int i=0; i<10; i++) {
                    scoreList[i] = Integer.parseInt(sc.nextLine());
                    scoreText[i] = new Label("#" + (i+1) + ": " + scoreList[i]);
                    scoreText[i].setVisible(false);
                    scoreText[i].setSize(1280,50);
                    scoreText[i].setLocation(600,100+50*i);
                    scoreText[i].setForeground(Color.WHITE);
                    scoreText[i].setFont(new Font("Serif", Font.PLAIN, 36));
                    add(scoreText[i]);
                }
                sc.close();
                //printScores();
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        public void actionPerformed(ActionEvent action){
            if(action.getSource() == play) {
                over.setVisible(false);
                playing = true;
                player = new Spaceship(DisplayWidth/2, DisplayHeight/2, 0);
                play.setVisible(false);
                HighScore.setVisible(false);
                Quit.setVisible(false);
                for (int i=asts.size()-1; i>=0; i--) {
                    asts.remove(i);
                    //System.out.println("removed");
                }
                score = 0;
                initAsteroids();
            }
            else if(action.getSource() == HighScore) {
                showScores = true;
                currentScore.setVisible(false);
                over.setVisible(false);
                play.setVisible(false);
                HighScore.setVisible(false);
                Quit.setVisible(false);
            }
            else if(action.getSource() == back) {
                showScores = false;
                for (int i=0; i<scoreList.length; i++) {
                    scoreText[i].setVisible(false);
                }
                playing = false;
                play.setVisible(true);
                HighScore.setVisible(true);
                Quit.setVisible(true);
            }
            else {
                System.exit(0);
            }
        }

        //radius = how far from the centers of object should collison be detected
        private int radius = 50;
        private double deltaX;
        private double deltaY;
        
        public void checkHit() {
            for (int i = 0; i < asts.size(); i++) {
                deltaX = asts.get(i).getX() - player.getX();
                deltaY = asts.get(i).getY() - player.getY();

                //if the absolute value of difference (distance) in x and y is less than radius then it is a collision
                if (Math.abs(deltaX) < radius && Math.abs(deltaY) < radius) {
                    gameOver();
                }

                for (int j=0; j<lasers.size(); j++) {
                    deltaX = asts.get(i).getX() - lasers.get(j).getX();
                    deltaY = asts.get(i).getY() - lasers.get(j).getY();

                    if (Math.abs(deltaX) < radius && Math.abs(deltaY) < radius) {
                        asts.get(i).destroy();
                        lasers.get(j).destroy();
                        score += 100;
                    }
                }
            }
        }

        //add gameOver text stuff here

        private void gameOver() {
            if (player.getDestroyed() == false) { 
                updateScores();
                loadScores();
                //printScores();
            }
            player.destroy();
            over.setVisible(true);
            over.setSize(1280,800);
            over.setLocation(540,-200);
            over.setForeground(Color.WHITE);
            over.setFont(new Font("Serif", Font.PLAIN, 36));
            add(over);
            
            play.setVisible(true);
            HighScore.setVisible(true);
            Quit.setVisible(true);

        }

        private void moveLasers() {
            for (int i=0; i<lasers.size();i++) {
                lasers.get(i).move();
           }
        }

        private void moveAsteroids() {
           for (int i=0; i<asts.size();i++) {
                asts.get(i).move();
           }
        }

        //prints what keys are pressed (debugging)
        public void printPressed() {
            for (int i=0; i<4; i++) {
                System.out.print(pressed[i] + " ");
            }
            System.out.println();
        }

        //prints scores (debugging) 
        public void printScores() {
            for (int i=0; i < scoreList.length; i++) {
                System.out.println("#" + (i+1) + ": " + scoreList[i]);
            }
        }

        public void updateScores() {
            for (int i=0; i<scoreList.length; i++) {
                if (score > scoreList[i]) {
                    for (int j=scoreList.length-1; j>i; j--) {
                        scoreList[j] = scoreList[j-1];
                    }
                    scoreList[i] = score;
                    break;
                }
            }

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("scores.txt")));

            for (int i=0; i<scoreList.length; i++) {
                out.println(scoreList[i]);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }

        public void movePlayer() {
            
            if(pressed[0]) {
                player.updateSpeed(-1);
            }
            if(pressed[1]) {
                player.updateAngle(-0.2);
            }
            if(pressed[2]) {
                player.updateSpeed(1);
            }
            if(pressed[3]) {
                player.updateAngle(0.2);
            }   
        
            player.move();
        
            //check out of bounds -> wrap around if player is out of bounds
            if (player.getX() < 0) {
                player.setX(DisplayWidth);
            }
            else if (player.getX() > DisplayWidth) {
                player.setX(0);
            }

            if (player.getY() < 0) {
                player.setY(DisplayHeight);
            }
            else if (player.getY() > DisplayHeight) {
                player.setY(0);
            }

    }



        @Override
        public Dimension getPreferredSize() {
            return new Dimension(DisplayWidth, DisplayHeight);
        }

        //creates asteroids
        public void initAsteroids() {
            for (int i=0; i<astCount; i++) {
                if (playing) {
                    asts.add(new Asteroid(3, player));
                }
                else {
                 asts.add(new Asteroid(3));   
                }
            }
        }

        public void run (DisplayMode dm) {
            setBackground(Color.BLACK);
            setForeground(Color.BLACK);
            setFont(new Font("Arial", Font.PLAIN, 24));
            picsLoaded = false;

            setSize(DisplayWidth,DisplayHeight);
            setLayout(null);
            
            if (!playing) {
                centerButton(play, DisplayWidth/2,300,ButtonWidth,ButtonHeight);
                centerButton(HighScore, DisplayWidth/2,360,ButtonWidth,ButtonHeight);
                centerButton(Quit, DisplayWidth/2,420,ButtonWidth,ButtonHeight);
                centerButton(back, 120,50,ButtonWidth,ButtonHeight);
            }
            back.setVisible(false);
            play.addActionListener(this);
            Quit.addActionListener(this);
            HighScore.addActionListener(this);
            back.addActionListener(this);
            
            initAsteroids();

            loadpics();

        }

        //image details
        private Image logo;
        private Image ship;
        private Image asteroidPic;
        private Image laserPic;

        int logo_width;
        int logo_height;
        int asteroid_width;
        int asteroid_height;
        int player_width;
        int player_height;
        int laser_width;
        int laser_height;
        private boolean picsLoaded;

        public void loadpics() {
            logo = new ImageIcon("src/Resources/logo.png").getImage();
            logo_width = 516;
            logo_height = 132;

            asteroidPic = new ImageIcon("src/Resources/asteroid.png").getImage();
            asteroid_width = 100;
            asteroid_height = 100;

            ship = new ImageIcon("src/Resources/spaceship.png").getImage();
            player_width = 50;
            player_height = 50;

            laserPic = new ImageIcon("src/Resources/laser.png").getImage();
            laser_width = 25;
            laser_height = 25;            

            picsLoaded = true;
            repaint();
        }

        public void showAsteroids(Graphics g2d) {
            for (int i=0; i<asts.size(); i++) {

                //check is asteroids are out of bounds -> wrap around if out of bounds
                if (asts.get(i).getX() < 0) {
                    asts.get(i).setX(DisplayWidth);
                }
                else if (asts.get(i).getX() > DisplayWidth) {
                    asts.get(i).setX(0);
                }

                if (asts.get(i).getY() < 0) {
                    asts.get(i).setY(DisplayHeight);
                }
                else if (asts.get(i).getY() > DisplayHeight) {
                    asts.get(i).setY(0);
                }

                //display the asteroid image centered around asteroid coordinates
                centerImg(g2d, asteroidPic, (int) asts.get(i).getX(), (int) asts.get(i).getY(), asteroid_width, asteroid_height);
            }
        }

        public void destroyLasers() {
            for (int i = lasers.size()-1; i>=0; i--) {
                if (lasers.get(i).getDestroyed() == true) {
                    lasers.remove(i);
                }
            }
        }

        public void destroyAsteroids() {
            for (int i = asts.size()-1; i>=0; i--) {
                if (asts.get(i).getDestroyed() == true) {
                    asts.remove(i);
                }
            }
        }

        public void showLasers(Graphics g) {

            for (int i=0; i<lasers.size(); i++) {

                //check if lasers are out of bounds -> destroy them if they are
                if (lasers.get(i).getX() < 0) {
                    lasers.get(i).destroy();
                }
                else if (lasers.get(i).getX() > DisplayWidth) {
                    lasers.get(i).destroy();
                }

                if (lasers.get(i).getY() < 0) {
                    lasers.get(i).destroy();
                }
                else if (lasers.get(i).getY() > DisplayHeight) {
                    lasers.get(i).destroy();
                }
                Graphics2D las = (Graphics2D) g.create();

                //create laser with image centered around laser coordinates

                centerImg(las, laserPic, (int) lasers.get(i).getX(), (int) lasers.get(i).getY(), laser_width, laser_height, lasers.get(i).getAngle());
                las.dispose();

            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            Graphics2D ply = (Graphics2D) g.create();
           
            //if highscore has been hit show scores
            if (showScores) {
                for (int i=0; i<scoreText.length; i++) {
                    play.setVisible(false);
                    HighScore.setVisible(false);
                    Quit.setVisible(false);
                    over.setVisible(false);
                    scoreText[i].setVisible(true);
                    currentScore.setVisible(false);
                }
                back.setVisible(true);
            }
            //else if game hasnt started draw the main screen
            else if (!playing) {
                back.setVisible(false);
                currentScore.setVisible(false);
                showAsteroids(g2d);
                g2d.drawImage(logo, DisplayWidth/2 - logo_width/2, 100 - logo_height/2, null);
            }
            //otherwise display the game screen
            else {
               if (player.getDestroyed() == false) {
                    ply.rotate(player.getAngle(), player.getX(), player.getY());
                    centerImg(ply, ship, player.getX(), player.getY(), player_width, player_height);
                }
                showLasers(g);
                showAsteroids(g2d);
                back.setVisible(false);
            }


            g2d.dispose();
            ply.dispose();
        }

        public void centerImg (Graphics g, Image i, int x, int y, int w, int h) {
            g.drawImage(i, x - w/2, y - h/2, null);
        }

        public void centerImg (Graphics2D g, Image i, int x, int y, int w, int h, double theta) {
            g.rotate(theta + Math.PI / 2, x, y);

            g.drawImage(i, x + w/2 - player_height, y - h/2, null);
        }


        public void centerButton (Button b, int x, int y, int w, int h) {
            b.setBounds(x - w/2,y - h/2, w, h);
            add(b);
        }
    }

    
