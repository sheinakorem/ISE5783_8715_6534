package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * Class Ambient Light represents the ambient light in a scene, the basic lighting.
 */

public class AmbientLight extends Light{
    /**
     * constructor for Ambient Light
     * @param Ia intensity of light
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
    public AmbientLight(){super(Color.BLACK);}


    /**
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    /**
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point p) {
        return null;
    }
}
