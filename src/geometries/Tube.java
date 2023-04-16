package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;


/** class that inherits from RationalGeometry and represents Tube
 * @author michal slutzkin & sheina korem
 */
public class Tube extends RadialGeometry {
    protected final Ray axisRay;

    /**
     * constructor for Tube. gets radius and axisRay .uses the RadialGeometry constructor
     * @param r=radius
     * @param axisRay=axisRay
     */
    public Tube(double r, Ray aRay) {
        super(r);
        if(r <= 0)
        {
            throw new IllegalArgumentException("The radius should be grater than 0");
        }

        radius=r;
        axisRay = aRay;
    }

    /**
     * override function to return the normal(null)
     * @param point = point
     * @return null
     */

    @Override
    public Vector getNormal (Point point) {
        Vector v= axisRay.getDir();
        Point p0 =axisRay.getPoint();

        double t= v.dotProduct(point.subtract(p0));

        //if t=0, then t*v is the zero vector and o=p0.
        Point o=p0;

        if(!isZero(t))
        {
            o=p0.add(v.scale(t));
        }

        return point.subtract(o).normalize();
    }
}
