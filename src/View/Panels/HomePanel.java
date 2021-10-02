package View.Panels;

import View.Panel;
import View.View;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private View view;

    public HomePanel(View view) {
        this.view = view;

        // Centred Panel Creation
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(236, 213, 192));

        this.add(Box.createRigidArea(new Dimension(0, 50)));
        // Image Container
        JLabel image = new JLabel(new ImageIcon( "img/Tents and Trees.png"));
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(image);

        this.add(Box.createRigidArea(new Dimension(0, 50)));
        // Play Button Creation
        JButton start = new JButton("Start");
        start.addActionListener(e -> view.changePanel(Panel.SetBoard));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setMaximumSize(new Dimension(150, 40));
        this.add(start);

        this.add(Box.createRigidArea(new Dimension(0, 20)));
        // Play Button Creation
        JButton tuto = new JButton("Tutorial");
        tuto.addActionListener(e -> view.changePanel(Panel.Tutorial));
        tuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        tuto.setMaximumSize(new Dimension(150, 40));
        this.add(tuto);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        // Quit Button Creation
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> view.quit());
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setMaximumSize(new Dimension(150, 40));
        this.add(quit);

    }

}
