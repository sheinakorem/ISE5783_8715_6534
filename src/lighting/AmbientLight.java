package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    private Color intensity;
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
    /**
     * create AmbientLight of the scene
     * @param Ia the color of ambientLight
     * @param Ka factor of the ambientLight
     */
    public AmbientLight(Color Ia , Double3 Ka) {
        intensity=Ia.scale(Ka);
    }
    public AmbientLight(Color Ia , double Ka) {

        intensity=Ia.scale(Ka);
    }


    /**
     * default constructor that create ambientLight in black
     */
    public AmbientLight( ) {
        Color Ia=Color.BLACK;
    }

    protected AmbientLight(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns intensity of the light.
     * @return A shallow copy of the color.
     */
    public Color getIntensity() {
        return intensity;
    }


}
