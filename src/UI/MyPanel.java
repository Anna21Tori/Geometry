package UI;
import UI.button.BtnDraw;
import UI.button.BtnRandom;
import UI.canvas.*;
import UI.layout.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener{

    private CanvasAbstract canvas;
    private TwoLines lines;
    private OneLine line;
    private Triangle triangle;
    private JPanel canvasPanel;
    private Circle circle;
    private MyCanvasPolygon polygon;
    private JComboBox <String> menu;
    private JTextArea text;
    private BtnDraw btnDraw ;
    private BtnRandom btnRandom;
    public MyPanel(){

        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        canvasPanel = new JPanel();
        mainPanel.add(canvasPanel);
        add(mainPanel, new BorderLayout().EAST);
        initCanvas();
        initComboBox();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(canvas != null)
            canvasPanel.remove(canvas);
        else {
            GridBagLayout layout = new GridBagLayout();
            canvasPanel.setLayout(layout);
            text.setEditable(false);
            text.setBorder(BorderFactory.createLineBorder(Color.black));
            canvasPanel.add(text, new GBC(0,0).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));
            canvasPanel.add(btnDraw, new GBC(0,1).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));
            canvasPanel.add(btnRandom, new GBC(0,2).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));

        }
        switch (menu.getSelectedIndex()) {
            case 1:
                canvas = lines;
                break;
            case 0:
               canvas = line;
                break;
            case 2:
                canvas = triangle;
                break;
            case 3:
                canvas = circle;
                break;
            case 4:
               canvas = polygon;
                break;

        }
        btnDraw.setCanvas(canvas);
        btnRandom.setCanvas(canvas);
        canvasPanel.add(canvas, new GBC(1,0, 1,6).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));;

        canvasPanel.repaint();
        repaint();
        validate();
    }

    private void initComboBox(){
        menu = new JComboBox<>();
        menu.addActionListener(this);
        menu.addItem("Line");
        menu.addItem("Two Lines");
        menu.addItem("Triangle");
        menu.addItem("Circle");
        menu.addItem("Polygon");
        add(menu, new BorderLayout().NORTH);
    }

    private void initCanvas(){
        text = new JTextArea(10,20);
        line = new OneLine(text);
        lines = new TwoLines(text);
        triangle = new Triangle(text);
        circle = new Circle(text);
        polygon = new MyCanvasPolygon(text);
        btnDraw = new BtnDraw();
        btnRandom = new BtnRandom();
    }
}
