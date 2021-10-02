package View;

import View.Panels.HomePanel;
import View.Panels.SetBoardPanel;
import View.Panels.SolvedPanel;
import View.Panels.TutorialPanel;

import javax.swing.*;
import java.util.HashMap;

public class View {

    private JFrame window;

    private HashMap<Panel,JPanel> panels = new HashMap<>();

    public View(JFrame window) {
        this.window = window;
        panels.put(Panel.Home,new HomePanel(this));
        panels.put(Panel.Tutorial,new TutorialPanel(this));
        panels.put(Panel.SetBoard,new SetBoardPanel(this));
        panels.put(Panel.Solved, new SolvedPanel(this));
    }

    public void quit() {
        window.dispose();
    }

    public void changePanel(Panel panel) {
        switch(panel) {
            case Home:
                setSize(450,700);
                break;
            case Tutorial:
                ((TutorialPanel)panels.get(Panel.Tutorial)).init();
                break;
            case SetBoard:
                ((SetBoardPanel)panels.get(Panel.SetBoard)).init();
                break;
            case Solved:
                ((SolvedPanel)panels.get(Panel.Solved)).init(((SetBoardPanel)panels.get(Panel.SetBoard)).getBoard());
                break;
        }
        window.setContentPane(panels.get(panel));
        update();
    }

    public void setSize(int width, int height) {
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
    }

    public void update() {
        window.repaint();
        window.revalidate();
    }
}
