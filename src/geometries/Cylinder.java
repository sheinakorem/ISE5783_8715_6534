package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class inherits from tube and returns null
 *
 * @author michal slutzkin & sheina korem
 */
public class Cylinder extends Tube {

    protected final double height;
    /**
     * Cylinder's bottomCap.
     */
    protected final Plane bottomCap;
    /**
     * Cylinder's topCap.
     */
    protected final Plane topCap;

    /**
     * constructor for cylinder
     *
     * @param radius  = radius of cylinder
     * @param axisRay = axisRay from Tube
     * @param h  = height of Cylinder
     */
    public Cylinder(double h, Ray axisRay, double radius) {
        super(radius, axisRay);

        if (h <= 0)
        {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        height = h;
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);
        bottomCap = new Plane(p0, axisRay.getDir().scale(-1) /* Sets the normal directed outside of the cylinder */);
        topCap = new Plane(p1, axisRay.getDir());
    }

    /**
     * gets point and returns null(as for now)
     *
     * @param p= point
     * @return null
     */
    @Override
    public Vector getNormal(Point p) {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        if (p.equals(p0))
            return v;

        // projection of P-p0 on the ray:
        Vector u = p.subtract(p0);

        // distance from p0 to the o who is in from of point
        double t = alignZero(u.dotProduct(v));

        // if the point is at a base
        if (t == 0 || isZero(height - t))
            return v;

        //the other point on the axis facing the given point
        Point o = p0.add(v.scale(t));

        return p.subtract(o).normalize();
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);
        List<GeoPoint> result = null;

        // Find the tube's intersections
        List<GeoPoint> tubePoints = super.findGeoIntersections(ray);
        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                // Checks if the intersection points are on the cylinder
                GeoPoint q0 = tubePoints.get(0);
                GeoPoint q1 = tubePoints.get(1);
                boolean q0Intersects = isBetweenCaps(q0.point);
                boolean q1Intersects = isBetweenCaps(q1.point);

                if (q0Intersects && q1Intersects) {
                    return tubePoints;
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(q0);
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(q1);
                }
            }

            if (tubePoints.size() == 1) {
                // Checks if the intersection point is on the cylinder
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersections(ray);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap0Point.get(0);
            if (gp.point.distanceSquared(p0) < radius * radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.add(gp);
                if (result.size() == 2) {
                    return result;
                }
            }
        }

        // Finds the top cap's intersections
        List<GeoPoint> cap1Point = topCap.findGeoIntersections(ray);
        if (cap1Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap1Point.get(0);
            if (gp.point.distanceSquared(p1) < radius * radius) {
                if (result == null) {
                    return List.of(gp);
                }

                result.add(gp);
            }
        }

        return result;
    }


    /**
     * Helper function that checks if a points is between the two caps (not on them, even on the edge)
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point p) {
        Vector v0 = axisRay.getDir();
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);

        // Checks against zero vector...
        if (p.equals(p0) || p.equals(p1)) {
            return false;
        }

        return v0.dotProduct(p.subtract(p0)) > 0 &&
                v0.dotProduct(p.subtract(p1)) < 0;
    }

}
