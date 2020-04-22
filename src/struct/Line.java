package struct;

import java.text.DecimalFormat;

public class Line {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private double a;
    private double b;
    private double minY;
    private double maxY;
    private Point start;
    private Point end;

    public Line(Point p1, Point p2, double minY, double maxY){
        this.minY = minY;
        this.maxY = maxY;
        this.start = p1;
        this.end = p2;
        setLine(start, end);
    }


    public void setLine(Point p1, Point p2){
        if(p2.getX() - p1.getX() != 0)
         a = (p2.getY()- p1.getY())/(p2.getX() - p1.getX());
        else
            a = 0;
        b = p1.getY()- (a*p1.getX());
    }

    public String  getPatternLine(){
        String pattern = "y = ";
        double y2 = Math.abs(end.getY()-298);
        double y1 = Math.abs(start.getY()-298);
        if(end.getX() - start.getX() != 0) {
            pattern +=df2.format( (y2 - y1) / (end.getX() - start.getX()));
            pattern +=" * x";
        }
        double b = y1 - (a*start.getX());
        if(b < 0)
            pattern += df2.format(b);
        else
            pattern += " + "+ df2.format(b);
        return pattern;
    }

    public Point getP1() {
        if(a==0)
            return new Point(0,minY);
        return new Point((minY-b)/a,minY);
    }

    public Point getP2() {
        if(a==0)
            return new Point(0,minY);
        return new Point(((maxY - b)/a),maxY);
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public int whichSide(Point p) {
        if (a* p.getX() - p.getY() + b < 0)
            return  -1;
        else if (a * p.getX() - p.getY() + b > 0)
            return 1;
        else
            return 0;
    }


}
