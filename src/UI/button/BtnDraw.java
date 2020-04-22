package UI.button;

import UI.canvas.CanvasAbstract;
import UI.canvas.MyCanvasPolygon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtnDraw extends JButton implements ActionListener {
    private CanvasAbstract canvas;
    public BtnDraw() {
        super("Draw");
        addActionListener(this);
    }
    public void setCanvas(CanvasAbstract canvas){
        this.canvas = canvas;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setParameters();
    }

}
