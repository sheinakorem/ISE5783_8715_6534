package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements  LightSource{

    Point position;

    double kC=1; // Constant attenuation
    double kL=0; // Linear attenuation
    double kQ=0; // Quadratic attenuation

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    /**
     * Constructor for light
     *
     * @param intensity intensity (color) of light
     */
    public PointLight(Color intensity) {
        super(intensity);
    }

    public PointLight(Color colorIntensity, Point position, double kC, double kL, double kQ) {
        super(colorIntensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    /**
     * constructor for point light
     * by default, the constant attenuation value is 1 and the other two values are 0
     * @param colorIntensity intensity (color) of light
     * @param position position of light
     */
    public PointLight(Color colorIntensity, Point position) {

        this(colorIntensity, position, 1d, 0d, 0d);
    }

    /**
     * dummy overriding Light getIntensity()
     * @return light intensity
     */
    @Override
    public Color getIntensity() {
        return super.getIntensity();
     }

    /**
     * overriding LightSource getIntensity(Point3D)
     * @param p point
     * @return intensity of light from light source on certain point
     */
    @Override
    public Color getIntensity(Point p) {
        double dsquared = p.distanceSquared(position);
        double d = p.distance(position);

        return (getIntensity().reduce(kC + kL * d + kQ * dsquared));
    }

    /**
     * @param p point
     * @return Light vector
     */
    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            return null;
        }
        return p.subtract(position).normalize();
    }
}
