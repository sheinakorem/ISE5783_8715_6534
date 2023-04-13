package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class for plane
 *
 * @author michal slutzkin & sheina korem
 */
public class Plane implements Geometry {

    public Point q0;
    public Vector normal;

    /**
     * Constructor for plane. Gets three points and calculates the normal vector.
     * In addition, a single point is saved.
     *
     * @param p1= q0
     * @param p2=point
     * @param p3=point
     */
    public Plane(Point p1, Point p2, Point p3) {

        q0 = p1;
        Vector U =p1.subtract(p2);
        Vector V = p1.subtract(p3);
        Vector N =U.crossProduct(V);
        this.normal=N.normalize();
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
        }else {
            this.normal = vector;
        }
    }


    /**
     * getNormal function for Polygon
     *
     * @return normal
     */
    public Vector getNormal() {return normal;}


    /**
     * override function to return the normal(null)
     *
     * @param p = point
     * @return null
     */

    @Override
    public Vector getNormal(Point p) {return getNormal();}
}