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

    @Override
    public List<Point> findIntersections(Ray ray) {
            Point p0 = ray.getP0();
            Vector v = ray.getDir();


        if (p0.equals(center)) {
            return List.of(ray.getPoint(radius));
        }

        Vector u = center.subtract(p0);
       


            double tm =v.dotProduct(u);
            double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

            if (d >= radius) {
                return null;
            }

            double th = alignZero(Math.sqrt(radius * radius - d * d));
            double t1 = alignZero(tm - th);
            double t2 = alignZero(tm + th);

            if (t1 > 0 && t2 > 0) {
                return List.of(ray.getPoint(t1),ray.getPoint(t2));
            }

            if (t1 > 0) {
                return List.of(ray.getPoint(t1));
            }

            if (t2 > 0) {
                return List.of(ray.getPoint(t2));
            }
            return  null;
        }
}


