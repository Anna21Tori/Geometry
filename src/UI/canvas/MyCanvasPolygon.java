package UI.canvas;

import UI.dialog.DialogPolygon;
import struct.KDTree;
import struct.Node;
import struct.Point;
import struct.TPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class MyCanvasPolygon extends CanvasAbstract {
    private TPolygon polygon;
    private int size;
    private Point point;
    private JTextArea text;
    private ArrayList<Rectangle2D> points;
    private KDTree kd;
    private ArrayList<Color> colorsPoint;
    private Rectangle2D currentPoint;
    private Rectangle2D currentNeighbour;
    public MyCanvasPolygon(JTextArea text) {
        point = new Point(generateRect(), generateRect());
        size = generateSize();
        polygon = new TPolygon(generateTab(), generateTab());
        this.text = text;
        points = new ArrayList<>();
        kd = new KDTree();
        currentPoint = null;
        currentNeighbour = null;
        colorsPoint = new ArrayList<>();
        for(int i= 0;i<size; i++)
            colorsPoint.add(Color.blue);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                 currentPoint = find(mouseEvent.getPoint(), Color.RED);
                 defaultColors();
                if(currentPoint == null) {
                    point = new Point(mouseEvent.getX(), mouseEvent.getY());
                }else{
                    double [] tab = {currentPoint.getX()+((5*Math.sqrt(2))/2), currentPoint.getY()+((5*Math.sqrt(2))/2)};
                    currentNeighbour = null;
                    searchNeighbour(tab);

                }
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
        g2d.setPaint(Color.BLUE);
        polygon.generateContainers();
        double []x = polygon.getX();
        double []y = polygon.getY();
        points.clear();
        upDateKDtree(x, y);
        for(int i=0;i<size-1;i++) {
            g2d.setPaint(colorsPoint.get(i));
            rect = new Rectangle.Double(x[i]-((5*Math.sqrt(2))/2), y[i]-((5*Math.sqrt(2))/2), 5, 5);
            points.add(rect);
            g2d.fill(rect);
            double [] p1 = {x[i],y[i]};
            g2d.setPaint(Color.blue);
            Line2D l = new Line2D.Double(x[i], y[i], x[i+1], y[i+1]);
            g2d.draw(l);
        }
        g2d.setPaint(colorsPoint.get(size-1));
        rect = new Rectangle.Double(x[size-1]-((5*Math.sqrt(2))/2), y[size-1]-((5*Math.sqrt(2))/2), 5, 5);
        points.add(rect);
        g2d.fill(rect);
        double [] p2 = {x[size-1],y[size-1]};
        g2d.setPaint(Color.blue);
        g2d.draw(new Line2D.Double(x[size-1], y[size-1], x[0], y[0]));
        if(currentNeighbour != null && currentPoint!=null)
        {
            g2d.setPaint(Color.magenta);
            g2d.draw(new Line2D.Double(currentNeighbour.getX()+((5*Math.sqrt(2))/2), currentNeighbour.getY()+((5*Math.sqrt(2))/2), currentPoint.getX()+((5*Math.sqrt(2))/2), currentPoint.getY()+((5*Math.sqrt(2))/2)));
        }
        isInside();
    }

    private void defaultColors(){
        colorsPoint.clear();
        for(int i = 0; i<size;i++)
            colorsPoint.add(Color.blue);
    }

    //genorowanie współrzednej
    private double[] generateTab() {
        double[] tab = new double[size];
        double x = 0;
        for (int i = 0; i < size; i++) {
            do {
                x = generateRect();
            }while(existRect(tab, x, i));
            tab[i] = x;
        }
        return tab;
    }
    //sprawdzanie czy dana współrzędna nie wystapiła
    private boolean existRect(double []tab, double x, int i){
        for(int j=0;j<i;j++){
            if(tab[j] == x)
                return true;
        }
        return false;
    }

    //generowanie ilość wierzchołków
    private int generateSize() {
        int size;
        Random generator = new Random();
        do {
            size = generator.nextInt(10);
        }while(size<=3);
        return size;
    }


    private double generateRect() {
        double rect;
        Random generator = new Random();
        do {
            rect = generator.nextDouble()*1000;
        } while (rect < 0 || rect > 298);
        return rect;
    }

    private void upDateKDtree(double []x, double []y){
        kd = new KDTree();
        for(int i=0;i<x.length;i++) {
            double[] p = {x[i], y[i]};
            kd._insert(p, 0);
        }
    }
    @Override
    public void random() {
        text.setText("");
        currentPoint = null;
        currentNeighbour = null;
        size = generateSize();
        point.setX(generateRect());
        point.setY(generateRect());
        defaultColors();
        double [] x = generateTab();
        double [] y = generateTab();
        polygon.setXY(x,y);
        repaint();
    }

    @Override
    public void setParameters() {
        DialogPolygon dialog = new DialogPolygon();
        if(dialog.showDialog(MyCanvasPolygon.this)) {
            double [] x = dialog.getTabX();
            double [] y = dialog.getTabY();
            currentPoint = null;
            currentNeighbour = null;
            size = x.length;
            polygon.setXY(x,y);
            defaultColors();
            repaint();
        }
    }

    private void isInside(){
        if(polygon.isInside(point, polygon.getX(),polygon.getY()))
            text.setText("Point is inside");
        else
            text.setText("Point is outside");
    }

    private void searchNeighbour(double tab[]){
        Node pom = kd._findNearest(tab);
        if(pom != null){
            int i = 0;
            for(Rectangle2D r :points){
                if(r.getX()+((5*Math.sqrt(2))/2) == pom.getP()[0] && pom.getP()[1] == r.getY()+((5*Math.sqrt(2))/2)){
                    currentNeighbour = r;
                    colorsPoint.set(i,Color.GREEN);
                }
                i++;
            }
        }
    }

    private Rectangle2D find(Point2D point, Color color){
        int i=0;
        for(Rectangle2D r: points) {
            if (r.contains(point)) {
                colorsPoint.set(i,Color.magenta);
                return r;
            }
            i++;
        }
        return null;

    }
}