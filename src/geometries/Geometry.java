package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * An interface called  Geometry for geometric shapes
 * @author michal slutzkin & sheina korem
 */
public interface Geometry extends Intersectable{
    /**
     * function that gets point and returns a normalized vector
     * @param p = point
     * @return normalized vector
     */
    public Vector getNormal(Point p);

}
