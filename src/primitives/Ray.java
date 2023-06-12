package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class that represents Ray
 *
 * @author michal slutzkin & sheina korem
 */
public class Ray {
    private static final double DELTA = 0.1;

    private final Point p0;
    private final Vector dir;

    /**
     * Constructor for ray with offset
     *
     * @param point     original point laying on the surface of the geometry
     * @param direction normal vector from the geometry
     */
    public Ray(Point point, Vector direction, Vector n) {
        // Compute the offset vector based on the orientation of the normal
        double nl = direction.dotProduct(n);
        Vector offset = n.scale(nl > 0 ? DELTA : -DELTA);
        this.p0 = point.add(offset);
        this.dir = direction.normalize();

    }


    /**
     * constructor that accepts a point and a vector
     *
     * @param p =point
     * @param v = normalized vector
     */
    public Ray(Point p, Vector v) {
        this.p0 = p;
        this.dir = v.normalize();
    }

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
        if(isZero(t)){
            return p0;
        }
        return p0.add(dir.scale(t));
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

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream()
                .map(p -> new GeoPoint(null, p))
                .toList())
                .point;
    }


    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        double minDistance = Double.MAX_VALUE;
        double d;

        if (intersections == null) {
            return null;
        }

        GeoPoint closePoint = null;
        for (GeoPoint geoP : intersections) {

            d = geoP.point.distance(p0);
            //check if the distance of p is smaller then minDistance
            if (d < minDistance) {
                minDistance = d;
                closePoint = geoP;
            }
        }

        return closePoint;
    }

}
