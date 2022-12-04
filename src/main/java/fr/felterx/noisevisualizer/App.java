package fr.felterx.noisevisualizer;

import javax.swing.*;

public class App {


    private App() {
        JFrame frame = new JFrame("Perlin Noise Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setContentPane(new MainPanel());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        new Thread(() -> {
            while (true) {
                frame.repaint();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new App();
    }

}
