package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private final Point position;

    private Double3 kC = Double3.ONE; // Constant attenuation
    private Double3 kL = Double3.ZERO; // Linear attenuation
    private Double3 kQ = Double3.ZERO; // Quadratic attenuation


    /**
     * constructor for point light
     * by default, the constant attenuation value is 1 and the other two values are 0
     *
     * @param colorIntensity intensity (color) of light
     * @param position       position of light
     */
    public PointLight(Color colorIntensity, Point position) {
        super(colorIntensity);
        this.position = position;
    }

    public PointLight setKc(Double3 kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKc(double kC) {
        this.kC = new Double3(kC);
        return this;
    }

    public PointLight setKl(Double3 kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = new Double3(kL);
        return this;
    }

    public PointLight setKq(Double3 kQ) {
        this.kQ = kQ;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }


    /**
     * overriding LightSource getIntensity(Point3D)
     *
     * @param p point
     * @return intensity of light from light source on certain point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(position);

        return (getIntensity().reduce(kC.add(kL.scale(d)).add(kQ.scale(d * d))));
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

    /**
     * return distance between light source and given point
     *
     * @param point to calculate distance
     * @return distance
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
