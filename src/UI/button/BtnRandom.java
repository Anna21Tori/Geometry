package UI.button;



import UI.canvas.CanvasAbstract;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtnRandom extends JButton implements ActionListener {

    private CanvasAbstract canvas;
    public BtnRandom() {
        super("Draw random");
        addActionListener(this);

    }
    public void setCanvas(CanvasAbstract canvas){
        this.canvas = canvas;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.random();
    }
}
