package struct;


public class Circle {
    private Point point;
    private double radius;
    public Circle(Point point, double radius) {
        this.point = point;
        this.radius = radius;
    }

    public Point getPoint() {
        return point;
    }

    public double getRadius() {
        return radius;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getTranslationX(){
        return point.getX()-radius;
    }

    public double getTranslationY(){
        return point.getY()-radius;
    }

    public float[][] circleEquation(Line line){
        Point p1 = line.getP1();
        Point p2 = line.getP2();
        float [][] rect;

        double a =  Math.pow(p1.getX(),2) + Math.pow(p1.getY(),2) + Math.pow(p2.getX(),2) + Math.pow(p2.getY(),2) - (2*((p1.getX()*p2.getX())+(p1.getY()*p2.getY())));
        double b = 2*(point.getX()*(p2.getX()-p1.getX())+(point.getY()*(p2.getY()-p1.getY()))+(p1.getX()*p2.getX())+(p1.getY()*p2.getY()) - Math.pow(p2.getX(),2) - Math.pow(p2.getY(),2));
        double c = -Math.pow(radius,2) + Math.pow(p2.getX(),2) + Math.pow(p2.getY(),2) + Math.pow(point.getX(),2) + Math.pow(point.getY(),2) - (2*((point.getX()*p2.getX()) + (point.getY()*p2.getY())));
        double delta = Math.pow(b,2)  - (4*a*c);

        if(delta == 0){
            rect = new float[1][1];
            double u = -b / (2 * a);
            rect[0][0] = (float) (u * p1.getX() + (1.0 - u) * p2.getX());
            rect[1][0] = (float) (u * p1.getY() + (1.0 - u) * p2.getY());
            return rect;
        }
        else if(delta > 0) {

                double u = -b / (2 * a);
                double v = Math.sqrt(delta) / (2 * a);
                rect = new float[2][2];
                rect[0][0] = (float) ((u + v) * p1.getX() + (1.0 - u - v) * p2.getX());
                rect[1][0]= (float)((u + v) * p1.getY() + (1.0 - u - v) * p2.getY());
                rect[0][1] = (float) ((u - v) * p1.getX() + (1.0 - u + v) * p2.getX());
                rect[1][1]= (float)((u - v) * p1.getY() + (1.0 - u + v) * p2.getY());
                return rect;

        }
        return null;

    }
}
