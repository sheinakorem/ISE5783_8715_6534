package primitives;

import java.util.List;

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
     * @param p =point
     * @param v = normalized vector
     */
    public Ray(Point p, Vector v) {
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

   public Point findClosestPoint (List<Point> points){
       double minDistance = Double.MAX_VALUE;
       double d;
       Point closePoint = null;

       if(points==null){
           return null;
       }

       for (Point p : points) {

           d = p.distance(p0);
           //check if the distance of p is smaller then minDistance
           if (d < minDistance) {
               minDistance = d;
               closePoint = p;
           }
       }
       return closePoint;
   }

}
