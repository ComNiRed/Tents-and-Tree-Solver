package View.Panels;

import Model.Case;
import Model.Board;
import Model.Solver;
import View.View;
import View.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SolvedPanel extends JPanel {

    private View view;
    private Container cGame;

    private Solver solver;
    private HashMap<Case,ImageIcon> images = new HashMap<>();

    public SolvedPanel(View view) {
        this.view = view;
        solver = new Solver();
        // Images
        images.put(Case.Empty,new ImageIcon(new ImageIcon("img/empty.png").getImage().getScaledInstance(45, 45,Image.SCALE_SMOOTH)));
        images.put(Case.Garden,new ImageIcon(new ImageIcon("img/garden.png").getImage().getScaledInstance(45, 45,Image.SCALE_SMOOTH)));
        images.put(Case.Tent,new ImageIcon(new ImageIcon("img/tent.png").getImage().getScaledInstance(45, 45,Image.SCALE_SMOOTH)));
        images.put(Case.Tree,new ImageIcon(new ImageIcon("img/tree.png").getImage().getScaledInstance(45, 45,Image.SCALE_SMOOTH)));

        // Panel Creation
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(new Color(236, 213, 192));

        // Banner Container Creation
        Container bannerC = new Container();
        bannerC.setLayout(new FlowLayout(FlowLayout.CENTER));
        bannerC.setPreferredSize(new Dimension(400, 50));
        this.add(bannerC);

        // Home Button Creation
        JButton home = new JButton("Home");
        home.addActionListener(e -> view.changePanel(Panel.Home));
        bannerC.add(home);

        // Game Container Creation
        cGame = new Container();
        this.add(cGame);

    }

    public void init(Board board) {
        // Reset Displayed Board
        cGame.removeAll();

        // Init Things
        Font font = new Font("Trebuchet MS",Font.BOLD,20);
        int size = board.getSize();
        cGame.setLayout(new GridLayout(size+1,size+1,0,0));
        board = solver.solveGame(board);

        // Display Board
        cGame.add(new JLabel(""));
        for (int i = 0; i < size; i++) {
            JLabel jLabel = new JLabel(String.valueOf(board.getY(i)),null,SwingConstants.CENTER);
            jLabel.setFont(font);
            cGame.add(jLabel);
        }
        for (int i = 0; i < size; i++) {
            JLabel jLabel = new JLabel(String.valueOf(board.getX(i)),null,SwingConstants.CENTER);
            jLabel.setFont(font);
            cGame.add(jLabel);
            for (int j = 0; j < size; j++) {
                //cGame.add(new JLabel(null,images.get(board.getCase(i,j)),SwingConstants.CENTER));
                JButton button = new JButton(images.get(board.getCase(i,j)));
                button.setPreferredSize(new Dimension(45, 45));
                cGame.add(button);
            }
        }
    }
}
