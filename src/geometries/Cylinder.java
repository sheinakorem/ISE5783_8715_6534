package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Cylinder class inherits from tube and returns null
 * @author michal slutzkin & sheina korem
 */
public class Cylinder extends Tube {

    double height;

    /**
     * constructor for cylinder
     * @param radius = radius of cylinder
     * @param axisRay = axisRay from Tube
     * @param height = height of Cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {

        super(radius, axisRay);
        this.height = height;
    }

    /**
     * gets point and returns null(as for now)
     * @param p= point
     * @return null
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
