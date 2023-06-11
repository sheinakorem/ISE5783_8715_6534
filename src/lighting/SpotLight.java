package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight{
    private final  Vector direction;


    public SpotLight(Color intensity, Vector direction) {
        super(intensity,direction);
        this.direction = direction.normalize();
    }

    public SpotLight(Color colorIntensity, Point position, Double3 kC, Double3 kL, Double3 kQ, Vector direction) {
        super(colorIntensity, position, kC, kL, kQ);
        this.direction = direction.normalize();
    }

    public SpotLight(Color colorIntensity, Point position, Vector direction)  {
        super(colorIntensity, position);
        this.direction = direction.normalize();
    }

    public PointLight setNarrowBeam(int angle) {
        //todo
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
         Color color =super.getIntensity(p);
         Vector v =super.getL(p);
         double Max= max(0,direction.dotProduct(v));
         return color.scale(Max);

    }
}
