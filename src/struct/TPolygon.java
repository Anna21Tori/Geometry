package struct;

import java.util.ArrayList;
import java.util.List;

public class TPolygon {
    private double[] x;
    private double[] y;
    private Point minX;
    private Point maxX;
    private int a;  //index min X
    private int b; // index max x
    private ArrayList<Point> containersB; //punkty które leżą lewej lub na prostej wyznaczonej przez punkty z największą wartościć x i minimalną wartością x
    private ArrayList<Point> containersS; // punkty po prawej

    public TPolygon(double[] x, double[] y) {
        this.x = x;
        this.y = y;
        this.minX = minX();
        this.maxX = maxX();
        containersB = new ArrayList<>();
        containersS = new ArrayList<>();
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public void setXY(double[] x, double[] y) {
        this.x = x;
        this.y = y;
        this.minX = minX();
        this.maxX = maxX();
    }

    /////podziała punktu ze względu na prostą wyznaczoną przez współrzędne zawierające min X i max X
    public void generateContainers() {
        containersB.clear();
        containersS.clear();
        Line line = new Line(minX, maxX, minX().getY(), maxX().getY());
        for (int i = 0; i < x.length; i++) {
            if (a != i) {
                if (b != i) {
                    Point current = new Point(x[i], y[i]);
                    if (line.whichSide(current) == -1)
                        containersS.add(current);
                    else
                        containersB.add(current);
                }
            }
        }
        sort();
    }

    private void sort() {
        //sortowanie zbioru leżącego po prawej stronie, rosnaco
        for (int i = 0; i < containersS.size() - 1; i++) {
            for (int j = 0; j < containersS.size() - i - 1; j++) {
                if (containersS.get(j).getX() > containersS.get(j + 1).getX()) {
                    Point temp = containersS.get(j);
                    containersS.set(j, containersS.get(j + 1));
                    containersS.set(j + 1, temp);
                }
            }
        }
        //sortowanie zbioru lezącego po lewej stronie lub na prostej malejąco
        for (int i = 0; i < containersB.size() - 1; i++) {
            for (int j = 0; j < containersB.size() - i - 1; j++) {
                if (containersB.get(j).getX() < containersB.get(j + 1).getX()) {
                    Point temp = containersB.get(j);
                    containersB.set(j, containersB.get(j + 1));
                    containersB.set(j + 1, temp);
                }
            }
        }
        mergeContainers();
    }

    private void mergeContainers() {
        int i = 0;
        x[i] = minX.getX();
        y[i] = minX.getY();
        i++;
        for (int j = 0; j < containersS.size(); j++, i++) {
            x[i] = containersS.get(j).getX();
            y[i] = containersS.get(j).getY();
        }
        x[i] = maxX.getX();
        y[i] = maxX.getY();
        i++;
        for (int j = 0; j < containersB.size(); j++, i++) {
            x[i] = containersB.get(j).getX();
            y[i] = containersB.get(j).getY();
        }
    }

    private Point minX() {
        double min = x[0];
        double y1 = 0;
        a = 0;
        for (int i = 1; i < x.length; i++) {
            if (x[i] < min) {
                min = x[i];
                y1 = y[i];
                a = i;
            }
        }
        return new Point(min, y1);
    }

    private Point maxX() {
        double max = x[0];
        double y1 = 0;
        b = 0;
        for (int i = 1; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
                y1 = y[i];
                b = i;
            }
        }
        return new Point(max, y1);
    }

    private boolean doIntersect(Point p0, Point p1, Point p2, Point ray) {
        Line l0 = new Line(p0, ray, p0.getY(), 300);
        Line l1 = new Line(p1, p2, p1.getY(), p2.getY());
        if (l0.whichSide(p1) !=l0.whichSide(p2)) {
            if (l1.whichSide(p0) != l1.whichSide(ray)) {
                return true;
            } else
                return false;
        }
        return false;
    }

    public boolean isInside(Point p, double [] x, double [] y) {

        int [] counter  = {0,0, 0, 0};
        Point [] rays = {new Point(p.getX(),550), new Point(550,p.getY()), new Point(p.getX(),0), new Point(0,p.getY())};
        for(int j=0;j<counter.length;j++) {
            for (int i = 0; i < x.length; i++) {
                if (i + 1 != x.length) {
                    if (doIntersect(p, new Point(x[i], y[i]), new Point(x[i + 1], y[i + 1]), rays[j]))
                        counter[j]++;
                } else {
                    if (doIntersect(p, new Point(x[i], y[i]), new Point(x[0], y[0]), rays[j]))
                        counter[j]++;
                }
            }
        }


     for(int i = 0; i<counter.length;i++){
          if(counter[i] % 2 == 0)
              return false;
      }

        return true;
    }
}
