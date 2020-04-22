package UI.canvas;

import UI.dialog.DialogOneLine;
import struct.Line;
import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class OneLine extends CanvasAbstract{
    private Point point;
    private Line line;
    private JTextArea text;

    public OneLine(JTextArea text) {
        line = setLine(new Point(generateRect(), generateRect()), new Point(generateRect(), generateRect()));
        point = new Point(generateRect(), generateRect());
        this.text = text;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                point = new Point(mouseEvent.getX(), mouseEvent.getY());
                repaint();
            }});
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.white);
        g2d.fill(new Rectangle.Double(0, 0, 398, 298));
        g2d.setPaint(Color.black);
        g2d.draw(new Rectangle.Double(0, 0, 398, 298));
        g2d.setPaint(Color.BLUE);
        g2d.drawString(line.getPatternLine(), 0, 320);
        g2d.draw(new Line2D.Double(line.getP1().getX(), line.getP1().getY(), line.getP2().getX(),line.getP2().getY()));
        g2d.setPaint(Color.RED);
        Rectangle2D rect = new Rectangle.Double(point.getX(), point.getY(), 4, 4);
        g2d.fill(rect);
        whichSide();

    }

    private Line setLine(Point p1, Point p2){
        return new Line(p1, p2,0,298);
    }

    private double generateRect(){
        double rect;
        do {
            Random generator = new Random();
            rect = generator.nextDouble() * 1000;
        }while(rect > 298 || rect < 10);
        return rect;
    }

    public void random(){
        line = setLine(new Point(generateRect(),generateRect()), new Point(generateRect(),generateRect()));
        point = new Point(generateRect(), generateRect());
        repaint();
    }

    private void whichSide(){
        switch(line.whichSide(point)){
            case 1:
                text.setText("Point lies on the left side");
                break;
            case 0:
                text.setText("Point lies on a straight line");
                break;
            case -1:
                text.setText("Point lies on the right side");
        }
    }

    public void setParameters(){
        DialogOneLine dialog = new DialogOneLine();
        if(dialog.showDialog(OneLine.this)) {
            line = dialog.getLine();
            repaint();
        }
    }
}
