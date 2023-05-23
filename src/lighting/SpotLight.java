package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private final  Vector direction;


    public SpotLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    public SpotLight(Color colorIntensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(colorIntensity, position, kC, kL, kQ);
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
}
