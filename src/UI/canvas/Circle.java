package UI.canvas;

import UI.dialog.DialogCircle;
import struct.Line;
import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;

public class Circle extends CanvasAbstract {
    private struct.Circle circle;
    private Line line;
    private final int maxRadius = 100;

    private final int maxRect = 200;

    private JTextArea text;
    public Circle(JTextArea text) {
        setCircle();
        setLine();
        this.text=text;
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.white);
        g2d.fill(new Rectangle.Double(0, 0, 398, 298));
        g2d.setPaint(Color.black);
        g2d.draw(new Rectangle.Double(0, 0, 398, 298));
        g2d.setPaint(Color.BLUE);
        Ellipse2D e = new Ellipse2D.Double(circle.getTranslationX(), circle.getTranslationY(), circle.getRadius()*2,circle.getRadius()*2);
        g2d.draw(e);
        g2d.setPaint(Color.red);
        g2d.draw(new Line2D.Double(line.getP1().getX(), line.getP1().getY(), line.getP2().getX(),line.getP2().getY()));
        g2d.drawString(line.getPatternLine(), 0, 320);
        checkCollision();
    }
    private void setCircle(){
        circle = new struct.Circle(new Point(generateRect(maxRect),generateRect(maxRect)), generateRect(maxRadius));
    }

    private void setLine(){
        line = new Line(new Point(generateRect(298),generateRect(298)), new Point(generateRect(maxRect),generateRect(maxRect)),0,298);
    }

    private double generateRect(int bound){
        double rect;
        do {
            Random generator = new Random();
            rect = generator.nextDouble() * 1000;
        }while(rect > bound || rect < 10);
        return rect;
    }

    public void random(){
        text.setText("");
        circle.setPoint(new Point(generateRect(maxRect), generateRect(maxRect)));
        circle.setRadius(generateRect(maxRadius));
        line = new Line(new Point(generateRect(maxRect),generateRect(maxRect)), new Point(generateRect(maxRadius),generateRect(maxRect)), 0, 298);
        repaint();
    }

    public void setParameters(){
        DialogCircle dialog = new DialogCircle();
        if(dialog.showDialog(Circle.this)) {
            line = dialog.getLine();
            circle.setPoint(dialog.getPoint());
            circle.setRadius(dialog.getRadius());
            repaint();
        }
    }

    private void checkCollision(){
       float [][] rect =  circle.circleEquation(line);
            if(rect == null)
                text.setText("Outside");
            else if(rect.length == 2)
                text.setText("Intersect:"+"\nx1 = "+super.df2.format(rect[0][0])+" y1 = "+super.df2.format(Math.abs(rect[0][1]-298))+"\nx2 = "+super.df2.format(rect[1][0])+" y2 = "+super.df2.format(Math.abs(rect[1][1]-298)));

            else
                text.setText("Touch"+"\nx1 = "+super.df2.format(rect[0][0])+" y1 = "+super.df2.format(Math.abs(rect[0][1]-298)));
    }
}
