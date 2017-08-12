package com.company;

import com.company.controller.Controller;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Model model = new Model();
//        Controller controller = new Controller(model);
        Controller controller = new Controller();
        JFrame game = new JFrame();
        game.setTitle("SPACE WAR2 - alpha");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(1280, 720);
        game.setResizable(false);

        game.add(controller.getView());

        game.setLocationRelativeTo(null);
        game.setVisible(true);

        controller.run();
    }
}
