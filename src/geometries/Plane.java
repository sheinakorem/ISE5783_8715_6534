package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for plane
 *
 * @author michal slutzkin & sheina korem
 */
public class Plane implements Geometry {

    private final Point q0;
    private final Vector normal;

    /**
     * Constructor for plane. Gets three points and calculates the normal vector.
     * In addition, a single point is saved.
     *
     * @param p1=      q0
     * @param p2=point
     * @param p3=point
     */
    public Plane(Point p1, Point p2, Point p3) {

        q0 = p1;
        Vector U = p1.subtract(p2);
        Vector V = p1.subtract(p3);
        Vector N = U.crossProduct(V);
        this.normal = N.normalize();
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("Two of the points are identical");

    }

    /**
     * Constructor for plane.Updates the point and normalizes the vector if it is not normalized
     *
     * @param p0=q0
     * @param vector=novelized vector
     */
    public Plane(Point p0, Vector vector) {
        this.q0 = p0;
        if (!(isZero(vector.length() - 1d))) {
            this.normal = vector.normalize();
        } else {
            this.normal = vector;
        }
    }

    public Point getPoint() {
        return q0;
    }

    ;

    /**
     * getNormal function for Polygon
     *
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }


    /**
     * override function to return the normal(null)
     *
     * @param p = point
     * @return function getNormal
     */

    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }

    /**
     * Plane
     *
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        Vector n = normal;

        if (q0.equals(p0)) {
            return null;
        }

        Vector P0_Q0 = q0.subtract((p0));

        //numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0)) {
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        //ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        if (t <= 0) {
            return null;
        }

        Point point = ray.getPoint(t);
        return List.of(point);

  /*      Vector v = ray.getDir();
        Point p0 = ray.getP0();

        //Ray on the plane
        if (q0.equals(p0)) {
            return null;
        }

        double nqp = normal.dotProduct(q0.subtract(p0));

        //Ray on the plane
        if (isZero(nqp)) {
            return null;
        }

        double nv = normal.dotProduct(v);

        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(nqp / nv);

        //Ray after the plane
        if (t < 0) {
            return null;
        }

        Point P = ray.getPoint(t);


        //Ray crosses the plane
        return List.of(P);
    }*/

    }
}