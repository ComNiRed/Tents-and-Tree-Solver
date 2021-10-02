package View.Panels;

import View.View;
import View.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class TutorialPanel extends JPanel {

    private View view;
    private JLabel image;
    private JButton back;
    private JButton next;
    private JLabel counter;

    private int current;
    private HashMap<Integer,ImageIcon> images = new HashMap<>();

    public TutorialPanel(View view) {
        this.view = view;
        // Images
        images.put(1,new ImageIcon(new ImageIcon("img/tuto/1.jpg").getImage().getScaledInstance(300,438,Image.SCALE_SMOOTH)));
        images.put(2,new ImageIcon(new ImageIcon("img/tuto/2.jpg").getImage().getScaledInstance(300,438,Image.SCALE_SMOOTH)));
        images.put(3,new ImageIcon(new ImageIcon("img/tuto/3.jpg").getImage().getScaledInstance(300,438,Image.SCALE_SMOOTH)));
        images.put(4,new ImageIcon(new ImageIcon("img/tuto/4.jpg").getImage().getScaledInstance(300,438,Image.SCALE_SMOOTH)));
        images.put(5,new ImageIcon(new ImageIcon("img/tuto/5.jpg").getImage().getScaledInstance(300,438,Image.SCALE_SMOOTH)));

        // Panel Creation
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(236, 213, 192));

        this.add(Box.createRigidArea(new Dimension(0, 20)));
        // Home Button Creation
        JButton home = new JButton("Home");
        home.addActionListener(e -> view.changePanel(Panel.Home));
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(home);

        this.add(Box.createRigidArea(new Dimension(0, 20)));
        // Image Creation
        image = new JLabel(new ImageIcon());
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(image);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        // Navigate Bar Creation
        Container navBar = new Container();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.LINE_AXIS));
        this.add(navBar);

        // Back Button Creation
        back = new JButton("<");
        back.addActionListener(e -> setImage(current-1));
        back.setPreferredSize(new Dimension(75,30));
        back.setMinimumSize(new Dimension(75,30));
        back.setMaximumSize(new Dimension(75,30));
        navBar.add(back);
        // Next Button Creation
        next = new JButton(">");
        next.addActionListener(e -> setImage(current+1));
        next.setPreferredSize(new Dimension(75,30));
        next.setMinimumSize(new Dimension(75,30));
        next.setMaximumSize(new Dimension(75,30));
        navBar.add(next);

        this.add(Box.createRigidArea(new Dimension(0, 20)));
        // Counter Creation
        counter = new JLabel();
        counter.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(counter);

    }

    public void init() {
        setImage(1);
    }

    private void setImage(int newPos) {
        current = newPos;
        image.setIcon(images.get(current));
        counter.setText(current + " / 5");
        next.setEnabled(true);
        back.setEnabled(true);
        if(current == 5) next.setEnabled(false);
        else if(current == 1) back.setEnabled(false);
    }

}
