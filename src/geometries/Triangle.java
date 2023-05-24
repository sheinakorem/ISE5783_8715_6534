package geometries;
import primitives.* ;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that inserts from polygon and represents a Triangle
 * @author michal slutzkin & sheina korem
 */
public class Triangle extends Polygon {
    /**
     * constructor for Triangle . uses the Polygon constructor
     * @param p1=p1
     * @param p2=p2
     * @param p3=p3
     */
   public Triangle(Point p1,Point p2,Point p3){
       super(p1,p2,p3);
   }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray);
        if (intersections == null) return null;//there are no intersection points

        Point p0 = ray.getP0();//the start ray point
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(p0);//vector from the ray start point to the polygon vertices
        Vector v2 = vertices.get(1).subtract(p0);//vector from the ray start point to the polygon vertices
        Vector v3 = vertices.get(2).subtract(p0);//vector from the ray start point to the polygon vertices

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;//the point is out of triangle
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;//the point is out of triangle
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;//the point is out of triangle

        //for GeoPoint
        intersections.get(0).geometry = this;

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;

    }

}
