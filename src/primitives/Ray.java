package primitives;

import java.util.*;

import static primitives.Util.isZero;

/**
 * class that represents Ray
 *
 * @author michal slutzkin & sheina korem
 */
public class Ray {
    private final Point p0;
    private final Vector dir;


    public Vector getDir() {
        return dir;
    }

    /**
     * Gets a point on the ray by calculating p0 + t*v.
     *
     * @param t A scalar to calculate the point.
     * @return A point on the ray.
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * constructor that accepts a point and a vector
     *
     * @param v= normalized vector
     * @param p  =point
     */
    public Ray(Vector v, Point p) {
        this.p0 = p;
        this.dir = v.normalize();
    }
           /**
     * override function for equal operation
     *
     * @param o= object o
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * override function of toString
     *
     * @return print of p0 and direction of the vector
     */
    @Override
    public String toString() {
        return "Ray" + "p0=" + p0 + ", dir=" + dir;
    }

    public Point getP0() {
        return p0;
    }

}
