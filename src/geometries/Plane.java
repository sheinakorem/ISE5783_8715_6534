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
public final class Plane extends Geometry {

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
        this.normal = vector.normalize();
    }

    public Point getQ0() {
        return q0;
    }

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        if(q0.equals(ray.getP0())){
            throw new IllegalArgumentException(" ray starts from point Q ");
        }
        Vector p0Q = q0.subtract(ray.getP0());

        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        //check that the intersection point is closer to ray origin

        double t = alignZero(normal.dotProduct(p0Q) / nv);

        if (t > 0 && alignZero(t - maxDistance) < 0) {
            GeoPoint geo = new GeoPoint(this, ray.getPoint(t));
            return List.of(geo);
        }
        return null;

    }

}