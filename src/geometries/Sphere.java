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
     public Sphere(double radius, Point center) {
          super(radius);
          this.center = center;
     }

     /**
      * override function to return the normal(null)
      * @param point = point
      * @return null
      */
     @Override
     public Vector getNormal (Point point) { return null; }
}
