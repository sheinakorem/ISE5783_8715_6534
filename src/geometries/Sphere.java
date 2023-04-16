package geometries;
import primitives.* ;

/**
 * class that inherits from RationalGeometry and represents sphere
 * @author michal slutzkin & sheina korem
 */
public class Sphere extends RadialGeometry {
     Point center;

     /**
      * constructor for sphere. gets radius and a center point .uses the RadialGeometry constructor
      * @param radius=radius
      * @param center=central point
      */
     public Sphere(double r, Point c) {
          super(r);
          if (r <= 0) {
               throw new IllegalArgumentException("The radius should be greater then 0");
          }
          radius=r;
          center = c;
     }

     /**
      * override function to return the normal(null)
      * @param point = point
      * @return normalized point
      */
     @Override
     public Vector getNormal (Point point) {return point.subtract(center).normalize();}
}
