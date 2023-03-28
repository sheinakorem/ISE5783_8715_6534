package geometries;
import primitives.* ;
public class Sphere extends RadialGeometry {
     Point center;

     public Sphere(double radius, Point center) {
          super(radius);
          this.center = center;
     }

     @Override
     public Vector getNormal (Point point) { return null; }
}
