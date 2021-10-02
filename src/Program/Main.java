package Program;

import View.View;
import View.Panel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        // Window Creation
        JFrame window = new JFrame();
        // Set Up Window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Tents and Trees");
        window.setIconImage(new ImageIcon("img/Tents and Trees.png").getImage());
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);

        // Set Up Frame
        View view = new View(window);

        // Display Window
        view.changePanel(Panel.Home);
    }
}