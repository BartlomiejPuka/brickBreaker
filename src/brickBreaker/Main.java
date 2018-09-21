package brickBreaker;

import javax.swing.*;

public class Main {

    public static void config(JFrame obj, Gameplay gamePlay){
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }

    public static void main(String[] args){
        JFrame obj = new JFrame();
        Gameplay gamePlay = new Gameplay();
        config(obj, gamePlay);
    }
}
