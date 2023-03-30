package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


/** class that inherits from RationalGeometry and represents Tube
 * @author michal slutzkin & sheina korem
 */
public class Tube extends RadialGeometry {
    Ray axisRay;

    /**
     * constructor for Tube. gets radius and axisRay .uses the RadialGeometry constructor
     * @param radius=radius
     * @param axisRay=axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * override function to return the normal(null)
     * @param point = point
     * @return null
     */

    @Override
    public Vector getNormal (Point point) { return null; }
}
