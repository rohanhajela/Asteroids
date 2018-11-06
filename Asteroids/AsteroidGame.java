import java.lang.Object;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class AsteroidGame {
    DisplayMode dm = new DisplayMode(800,600,16, DisplayMode.REFRESH_RATE_UNKNOWN);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AsteroidGame();
        

    }
    
    public AsteroidGame() {
    //addKeyListener(this);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Asteroids");
                JPanel panel = new JPanel();
                frame.getContentPane().add(panel);
                Game a = new Game();

                panel.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                            
                    }

                    //checks when an arrow key is released
                    @Override
                    public void keyReleased(KeyEvent e) {
                          if (e.getKeyCode() == KeyEvent.VK_UP) {
                            a.pressed[0] = false;
                       }
                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            a.pressed[1] = false;
                       }
                       if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        
                        a.pressed[2] = false;
                       }
                       if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            a.pressed[3] = false;
                       }

                    }

                    //checks when an arrow key OR space is hit
                    @Override
                    public void keyPressed(KeyEvent e) {
                        //System.out.println(e.getKeyChar());
                       if (e.getKeyCode() == KeyEvent.VK_UP) {
                            a.pressed[0] = true;
                       }
                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            a.pressed[1] = true;
                       }
                       if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        
                        a.pressed[2] = true;
                       }
                       if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            a.pressed[3] = true;
                       }
                       if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                       		if (a.player.getDestroyed() == false) {
                            	a.lasers.add(a.player.shoot());
                        	}
                       }

                    }
                });

                panel.setFocusable(true);
                panel.requestFocusInWindow();


                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                frame.add(a);
                a.run(dm);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}