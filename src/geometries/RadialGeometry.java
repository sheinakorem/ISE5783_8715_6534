package geometries;

import primitives.Ray;

/**
 * class for geometric shapes with radius
 * @author michal slutzkin & sheina korem
 */
public abstract class RadialGeometry extends Geometry {
     protected double radius;

     /**
      * construct for RadialGeometry
      * @param radius=radius
      */
     public RadialGeometry(double radius) {
          this.radius = radius;
     }


}
