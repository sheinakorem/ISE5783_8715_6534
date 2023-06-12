package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

abstract class Light {
    private final Color intensity;

    /**
     * Constructor for light
     *
     * @param intensity intensity (color) of light
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }


    /**
     * Get intensity of light
     *
     * @return intensity of light
     */
    public Color getIntensity() {
        return intensity;
    }

    //public abstract Color getIntensity(Point p);


}
