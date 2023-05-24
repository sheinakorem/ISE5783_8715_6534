package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private final  Vector direction;

    /**
     * Constructor for directional light
     * @param Intensity intensity of directional light (color of light)
     * @param Direction direction of light
     */
    public DirectionalLight(Color Intensity, Vector Direction)
    {
        super(Intensity);
        this.direction = Direction;
    }

    /**
     * Get intensity of light from light source on point
     * @param  p point
     * @return intensity of light in point
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
      }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Get L- the vector from the light source to received point
     * @param p point
     * @return L- the vector from the light source to received point
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }


}
