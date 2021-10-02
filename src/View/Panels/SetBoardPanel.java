package View.Panels;

import Model.Case;
import Model.Board;
import View.View;
import View.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SetBoardPanel extends JPanel {

    private View view;
    private JTextField[] x;
    private JTextField[] y;
    private JButton[][] boardB;

    private Container cGame;
    private Container bannerC;
    private JTextField sizeFT;
    private JButton submit;

    private HashMap<Case,ImageIcon> images = new HashMap<>();
    private Board board;

    public SetBoardPanel(View view) {
        this.view = view;
        // Images
        images.put(Case.Empty,new ImageIcon(new ImageIcon("img/empty.png").getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH)));
        images.put(Case.Tree,new ImageIcon(new ImageIcon("img/tree.png").getImage().getScaledInstance(45, 45,Image.SCALE_SMOOTH)));

        // Panel Creation
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(new Color(236, 213, 192));

        // Banner Container Creation
        bannerC = new Container();
        bannerC.setLayout(new BoxLayout(bannerC, BoxLayout.LINE_AXIS));
        bannerC.setPreferredSize(new Dimension(400, 50));
        this.add(bannerC);

        // Home Button Creation
        JButton home = new JButton("Home");
        home.addActionListener(e -> view.changePanel(Panel.Home));
        bannerC.add(home);

        bannerC.add(Box.createHorizontalGlue());

        // Size Text Field Creation
        sizeFT = new JTextField("10");
        sizeFT.setPreferredSize( new Dimension(100,30));
        sizeFT.setMaximumSize( new Dimension(100,30));
        sizeFT.setMinimumSize( new Dimension(100,30));
        bannerC.add(sizeFT);

        // Load Size Button Creation
        JButton load = new JButton("Load");
        load.addActionListener(e -> load());
        bannerC.add(load);

        // Game Container Creation
        cGame = new Container();
        this.add(cGame);

        // Submit Button Creation
        submit = new JButton("Submit");
        submit.addActionListener(e -> view.changePanel(Panel.Solved));
        submit.setPreferredSize(new Dimension(200, 30));
        submit.setVisible(false);
        this.add(submit);

    }

    public void init() {
        cGame.removeAll();
        submit.setVisible(false);
        bannerC.setPreferredSize(new Dimension(400, 50));
        view.update();
    }

    private void load() {
        int size = 0;
        try { size = Integer.parseInt(sizeFT.getText()); } catch (Exception ignored) {}
        size = (size < 5) ? 5 : (Math.min(size, 22));
        board = new Board(size);
        cGame.removeAll();
        cGame.setLayout(new GridLayout(size+1,size+1,0,0));
        bannerC.setPreferredSize(new Dimension((size+1)*45, 50));
        submit.setVisible(true);
        JTextField jTextField;

        y = new JTextField[size];
        x = new JTextField[size];
        boardB = new JButton[size][size];

        cGame.add(new JLabel(""));
        for (int i = 0; i < size; i++) {
            jTextField = new JTextField();
            y[i] = jTextField;
            cGame.add(jTextField);
        }
        for (int i = 0; i < size; i++) {
            jTextField = new JTextField();
            x[i] = jTextField;
            cGame.add(jTextField);
            for (int j = 0; j < size; j++) {
                int iT = i;
                int jT = j;
                JButton button = new JButton(images.get(Case.Empty));
                button.setPreferredSize(new Dimension(45, 45));
                button.addActionListener(e -> click(iT,jT));
                boardB[i][j] = button;
                cGame.add(button);
            }
        }

        view.setSize(Math.max(450,(size+1)*45+100), Math.max(700,(size+1)*45+150));
        view.update();
    }

    private void click(int i, int j) {
        Case newType = (board.getCase(i,j) == Case.Tree) ? Case.Empty : Case.Tree;
        board.setCase(i, j, newType);
        boardB[i][j].setIcon(images.get(newType));
        view.update();
    }

    public Board getBoard() {
        for (int i = 0; i < board.getSize(); i++) {
            board.setX(i, Integer.parseInt(x[i].getText()));
            board.setY(i, Integer.parseInt(y[i].getText()));
        }
        return board;
    }

}
