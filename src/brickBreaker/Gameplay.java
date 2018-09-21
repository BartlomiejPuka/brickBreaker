package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;

    private int totalBricks = 21;

    private int score = 0;

    private Timer timer;
    private int delay = 12;

    private int playerX = 320;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(this.delay, this);
        timer.start();

    }

    public void paint(Graphics g){
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);


        map.draw((Graphics2D)g);

        // borders
        g.setColor(Color.cyan);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // the paddle
        g.setColor(Color.BLUE);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            ballPosX+=ballXdir;
            ballPosY+=ballYdir;
            if(ballPosX < 0 || ballPosX > 670){
                ballXdir = -ballXdir;
            }
            if(ballPosY < 5 || ballPosY > 550){
                ballYdir = -ballYdir;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_LEFT){
            if(playerX <= 10){
                playerX = 10;
            } else {
                moveLeft();
            }
        }
    }

    public void moveRight(){
        play = true;
        playerX+=20;
    }

    public void moveLeft(){
        play = true;
        playerX-=20;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}
