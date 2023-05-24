package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Light Source is an interface that is implemented only by lights that are light sources.
 */
public interface LightSource  {
    /**
     * Get intensity of light from light source on point
     * @param p point
     * @return intensity of light from light source on point
     */
    public Color  getIntensity(Point p);

    /**
     * Get L- the vector from the light source to received point
     * @param p point
     * @return L- the vector from the light source to received point
     */
    public Vector getL(Point p);

}
