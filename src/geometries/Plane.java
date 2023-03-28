package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

public class Plane implements Geometry {

public Point q0;
public Vector normal;
    public Plane(Point p1, Point p2, Point p3) {

        q0= p1;
        if (p1.equals (p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException ("Two of the points are identical");
        normal=null;

    }

    public Plane (Point p0, Vector vector) {
        this.q0=p0;
        if(! (isZero(vector.length()-1d))){ this.normal = vector.normalize();
        }
        this.normal= vector;
    }

    ;


    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    //because polygon
    public Vector getNormal() {
        return getNormal(null);
    }
}
