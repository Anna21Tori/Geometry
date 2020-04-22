package UI.canvas;

import UI.dialog.DialogTriangle;
import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Triangle extends CanvasAbstract{
    private struct.Triangle triangle;
    private Point point;
    private JTextArea text;
    private double area;

    public Triangle(JTextArea text) {
        setPoint();
        triangle = new struct.Triangle(setTriangle());
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.white);
        g2d.fill(new Rectangle.Double(0, 0, 398, 298));
        g2d.setPaint(Color.black);
        g2d.draw(new Rectangle.Double(0, 0, 398, 298));
        g2d.setPaint(Color.RED);
        Rectangle2D rect = new Rectangle.Double(point.getX(), point.getY(), 4, 4);
        g2d.fill(rect);
        g2d.setPaint(Color.blue);
        g2d.draw(new Line2D.Double(triangle.getX()[0], triangle.getY()[0], triangle.getX()[1],triangle.getY()[1]));
        g2d.draw(new Line2D.Double(triangle.getX()[0], triangle.getY()[0], triangle.getX()[2],triangle.getY()[2]));
        g2d.draw(new Line2D.Double(triangle.getX()[1], triangle.getY()[1], triangle.getX()[2],triangle.getY()[2]));
        g2d.drawString("Area Triangle = "+super.df2.format(area), 0, 320);
        isInside();
    }
    private void setPoint(){
        point = new Point(generateRect(),generateRect());
    }

    private Point [] setTriangle(){
        Point [] points = new Point[3];
        do {
            for (int i = 0; i < 3; i++)
                points[i] = new Point(generateRect(), generateRect());
        }while(!struct.Triangle.isTriangle(points));
        area = struct.Triangle.area(points[0], points[1], points[2]);
        return points;
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
        text.setText("");
        point.setX(generateRect());
        point.setY(generateRect());
        triangle.setRect(setTriangle());
        repaint();
    }

    public void setParameters() {
        DialogTriangle dialog = new DialogTriangle();
        if(dialog.showDialog(Triangle.this)) {
            Point [] points = dialog.getPoints();
            area = struct.Triangle.area(points[0], points[1], points[2]);
            triangle = new struct.Triangle(points);
            repaint();
        }
    }

    private void isInside(){
        if(struct.Triangle.isInside(triangle.getPoints(), point))
            text.setText("Point is inside.");
        else
            text.setText("Point is outside.");
    }

}
