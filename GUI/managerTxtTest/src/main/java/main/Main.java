package main;

import gui.Gui;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui view = new Gui();
                Controller controller = new Controller(view);
                view.setController(controller);
            }
        });
    }


}
