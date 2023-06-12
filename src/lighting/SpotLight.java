package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;
import static primitives.Util.isZero;

public class SpotLight extends PointLight {
    private final Vector direction;


    public SpotLight(Color intensity, Vector direction) {
        super(intensity, direction);
        this.direction = direction.normalize();
    }

    public SpotLight(Color colorIntensity, Point position, Vector direction) {
        super(colorIntensity, position);
        this.direction = direction.normalize();
    }


    public PointLight setNarrowBeam(int angle) {
        //todo
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Color color = super.getIntensity(p);
        Vector v = super.getL(p);
        double projection = direction.dotProduct(v);
        if(isZero(projection)){
            return Color.BLACK;
        }
        double Max = max(0, direction.dotProduct(v));
        return color.scale(Max);

    }
}
