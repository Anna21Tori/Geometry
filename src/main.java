import UI.MyFrame;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new MyFrame();
                frame.setSize(new Dimension(800, 560));
                frame.setMinimumSize(new Dimension(800, 560));
                frame.setVisible(true);
            }
        });
    }
}
