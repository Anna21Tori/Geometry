package struct;

import java.io.PipedOutputStream;

public class Triangle {
    private Point []points;

    public Triangle(Point []point){
        this.points = point;
    }

    public double[] getX(){
        double [] tabX = {points[0].getX(),points[1].getX(),points[2].getX()};
        return  tabX;
    }

    public double[] getY(){
        double [] tabY = {points[0].getY(), points[1].getY(), points[2].getY()};
        return  tabY;
    }

    public void setRect(Point [] points){
        this.points = points;
    }

    public static boolean isTriangle(Point [] points){
        double a = Math.pow((points[0].getX() - points[1].getX()),2) +  Math.pow((points[0].getY() - points[1].getY()),2);
        double b = Math.pow((points[1].getX() - points[2].getX()),2) +  Math.pow((points[1].getY() - points[2].getY()),2);
        double c = Math.pow((points[2].getX() - points[0].getX()),2) +  Math.pow((points[2].getY() - points[0].getY()),2);
        if(a+b > c && a+c > b && b+c > a)
            return true;
        return false;
    }

    public static double area(Point p0, Point p1, Point p2){
        return Math.abs((p1.getX() - p0.getX())*(p2.getY() - p0.getY()) - (p2.getX() - p0.getX())*(p1.getY() - p0.getY()))/2;
    }

    public Point[] getPoints(){
        return points;
    }
    public static boolean isInside(Point [] points, Point point)
    {
        double area0 = area(points[0], points[1], points[2]);
        double area1 = area(points[0],point,points[2]);
        double area2 = area(points[0], points[1], point);
        double area3 = area(point, points[1], points[2]);
        return ((float) area0 == (float) (area1 + area2 + area3));
    }
}
