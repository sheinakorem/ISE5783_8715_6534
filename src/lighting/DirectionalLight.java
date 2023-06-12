package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private final Vector direction;

    /**
     * Constructor for directional light
     *
     * @param intensity intensity of directional light (color of light)
     * @param direction direction of light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Get intensity of light from light source on point
     *
     * @param p point
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
     *
     * @param p point
     * @return L- the vector from the light source to received point
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * return dustance between light source and given point
     *
     * @param point to calculate distance
     * @return dustance
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;

    }


}
