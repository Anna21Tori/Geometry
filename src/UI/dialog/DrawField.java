package UI.dialog;

import struct.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawField extends JPanel {
    public DrawField() {
    }

    ArrayList<Point> points = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.blue);
        for(Point p: points)
            g2d.fill( new Rectangle.Double(p.getX(), p.getY(), 4, 4));

        g2d.setPaint(Color.black);
        g2d.draw(new Rectangle.Double(0, 0, 398, 298));
    }

    public void addRect(Point p) {
        points.add(p);
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
}
