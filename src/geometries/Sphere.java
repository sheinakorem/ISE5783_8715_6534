package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

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
        return point.subtract(center).normalize();
    }



    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector u;
        try {
            u = center.subtract(p0);//vector from camera to center of sphere
        } catch (IllegalArgumentException e) { //if p0 == _center it is illegal
            return List.of(new GeoPoint(this,(ray.getPoint(radius))));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radius * radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) {
            return List.of(
                    new GeoPoint(this,(ray.getPoint(t1)))
                    ,new GeoPoint(this,(ray.getPoint(t2)))); //P1 , P2
        }
        if (t1 > 0)
            return List.of(new GeoPoint(this,(ray.getPoint(t1))));
        else
            return List.of(new GeoPoint(this,(ray.getPoint(t2))));
    }

}


