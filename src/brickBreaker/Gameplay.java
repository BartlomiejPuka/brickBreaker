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
    private int delay = 8;

    private int playerX = 320;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -2;
    private int ballYdir = -3;

    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
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

        //
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString(""+score,592,30);

        // the paddle
        g.setColor(Color.BLUE);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);

        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You won, score: "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }

        if(ballPosY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores: "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            A:
            for(int i = 0; i < map.map.length;i++){
                for(int j = 0; j < map.map[0].length;j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY,20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=20;

                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else{
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }


            ballPosX+=ballXdir;
            ballPosY+=ballYdir;
            if(ballPosX < 0 || ballPosX > 670){
                ballXdir = -ballXdir;
            }
            if(ballPosY < 5){
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
        if(keyEvent.getKeyCode() == keyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -2;
                ballYdir = -3;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
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
