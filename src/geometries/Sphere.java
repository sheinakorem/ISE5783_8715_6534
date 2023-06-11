package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that inherits from RationalGeometry and represents sphere
 *
 * @author michal slutzkin & sheina korem
 */
public class Sphere extends RadialGeometry {
    private  final  Point center;

    protected final double radius;
    /**
     * constructor for sphere. gets radius and a center point .uses the RadialGeometry constructor
     *
     * @param r=radius
     * @param c=central point
     */
    public Sphere(double r, Point c) {
        super(r);
        if (r <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }
        radius = r;
        center = c;
    }

    /**
     * override function to return the normal(null)
     *
     * @param point = point
     * @return normalized point
     */
    @Override
    public Vector getNormal(Point point) {
        //return point.subtract(center).normalize();

        return point.subtract(center).normalize().scale(-1);

    }

    /**
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector cTOs;
        try {
            //vector from camera to center of sphere
            cTOs = center.subtract(p0);
            //if p0 == _center it is illegal
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this,(ray.getPoint(radius))));
        }
        double tm = alignZero(v.dotProduct(cTOs));
        double dSquared = (tm == 0) ? cTOs.lengthSquared() : cTOs.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        Point P1 =ray.getPoint(t1);
        Point P2 =ray.getPoint(t2);

        // ray constructed outside sphere
        // two intersection points
        if (t1 > 0 && t2 > 0 ) {

            return List.of(new GeoPoint(this,P1), new GeoPoint (this,P2));
        }
        // ray constructed inside sphere and intersect in back direction
        if (t1 > 0 && alignZero(t1-maxDistance) <= 0 ) {
            return List.of(new GeoPoint(this,P1));
        }
        // ray constructed inside sphere and intersect in forward direction
        if (t2 > 0 && alignZero(t2-maxDistance) <= 0 ) {

            return List.of(new GeoPoint (this,P2));
        }
        // no intersection points found - assurance return
        // code should not be reaching this point
        return null;
    }
}




