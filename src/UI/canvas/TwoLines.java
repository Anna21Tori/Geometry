package UI.canvas;

import UI.dialog.DialogTwoLine;
import struct.Line;
import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.Random;

public class TwoLines extends CanvasAbstract {
    private Point point;
    private Line line1;
    private Line line2;
    private JTextArea text;

    public TwoLines(JTextArea text) {
        line1 = setLine(new Point(generateRect(), generateRect()), new Point(generateRect(), generateRect()));
        line2 =  setLine(new Point(generateRect(), generateRect()), new Point(generateRect(), generateRect()));
        this.text = text;
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
        g2d.setPaint(Color.GREEN);
        g2d.draw(new Line2D.Double(line1.getP1().getX(), line1.getP1().getY(), line1.getP2().getX(),line1.getP2().getY()));
        g2d.drawString(line1.getPatternLine(), 0, 320);
        g2d.setPaint(Color.BLUE);
        g2d.drawString(line2.getPatternLine(), 0, 340);
        g2d.draw(new Line2D.Double(line2.getP1().getX(), line2.getP1().getY(), line2.getP2().getX(),line2.getP2().getY()));
        crossPoint();
        if(point != null) {
            g2d.setPaint(Color.RED);
            Rectangle2D rect = new Rectangle.Double(point.getX()-(2*Math.sqrt(2)), point.getY()-(2*Math.sqrt(2)), 4, 4);
            g2d.fill(rect);
        }
    }

    private Line setLine(Point p1, Point p2){
       return new Line(p1, p2,0,298);
    }

    private double generateRect(){
        double rect;
        do {
            Random generator = new Random();
            rect = generator.nextDouble() * 1000;
        }while(rect > 298 || rect < 0);
        return rect;
    }

    public void random(){
        line1 = setLine(new Point(generateRect(),generateRect()), new Point(generateRect(),generateRect()));
        line2 = setLine(new Point(generateRect(),generateRect()), new Point(generateRect(),generateRect()));
        repaint();
    }

    private void crossPoint(){
        double W = line1.getA() * (-1) - line2.getA() * (-1);
        double Wx = (-line1.getB()) * (-1) - (-line2.getB()) * (-1);
        double Wy = line1.getA() * (-line2.getB()) - line2.getA() * (-line1.getB());
        if (W == 0 && Wx!=0 && Wy!=0) {
            point = null;
            text.setText("UKLAD JEST SPRZECZNY");
        }
        else if (W == 0 && Wx == 0 && Wy == 0) {
            point = null;
            text.setText("UKLAD MA NIESKONCZENIE WIELE ROZWIAZAN");
        }
        else {
            double x = Wx / W;
            double y = Wy / W;
            point = new Point(x, y);
            text.setText("Cross point\n x = "+super.df2.format(x)+"\n y = "+super.df2.format(Math.abs(y-298)));
        }
    }

    public void setParameters(){
        DialogTwoLine dialog = new DialogTwoLine();
        if(dialog.showDialog(TwoLines.this)) {
            line1 = dialog.getLine1();
            line2 = dialog.getLine2();
            repaint();
        }
    }
}
