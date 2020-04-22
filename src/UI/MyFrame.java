package UI;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{

    private JPanel panel;

    public MyFrame() {
        super("GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        panel = new MyPanel();
        add(panel);
        pack();
    }



}
