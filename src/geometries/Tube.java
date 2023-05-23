package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * class that inherits from RationalGeometry and represents Tube
 *
 * @author michal slutzkin & sheina korem
 */
public class Tube extends RadialGeometry {
    protected final Ray axisRay;

    /**
     * constructor for Tube. gets radius and axisRay .uses the RadialGeometry constructor
     *
     * @param r=radius
     * @param aRay=axisRay
     */
    public Tube(double r, Ray aRay) {
        super(r);
        if (r <= 0) {
            throw new IllegalArgumentException("The radius should be grater than 0");
        }

        radius = r;
        axisRay = aRay;
    }

    /**
     * override function to return the normal(null)
     *
     * @param point = point
     * @return normalized vector
     */

    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();

        double t = v.dotProduct(point.subtract(p0));

        //if t=0, then t*v is the zero vector and o=p0.
        Point o = p0;

        if (!isZero(t)) {
            o = p0.add(v.scale(t));
        }

        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
            Vector v = ray.getDir();
            Vector v0 = axisRay.getDir();

            // Calculating temp1 = v - v0 * (v,v0)
            Vector temp1 = v;
            double vv0 = v.dotProduct(v0);
            if (!isZero(vv0)) {
                Vector v0vv0 = v0.scale(vv0);
                if (v.equals(v0vv0)) {
                    return null;
                }
                temp1 = v.subtract(v0vv0);
            }

            // Calculating temp2 = dp - v0 * (dp,v0) where dp = p0 - p
            double temp1DotTemp2 = 0;
            double squaredTemp2 = 0;
            if (!ray.getP0().equals(axisRay.getP0())) {
                Vector dp = ray.getP0().subtract(axisRay.getP0());
                Vector temp2 = dp;
                double dpv0 = dp.dotProduct(v0);
                if (isZero(dpv0)) {
                    temp1DotTemp2 = temp1.dotProduct(temp2);
                    squaredTemp2 = temp2.lengthSquared();
                }
                else {
                    Vector v0dpv0 = v0.scale(dpv0);
                    if (!dp.equals(v0dpv0)) {
                        temp2 = dp.subtract(v0dpv0);
                        temp1DotTemp2 = temp1.dotProduct(temp2);
                        squaredTemp2 = temp2.lengthSquared();
                    }
                }
            }

            // Getting the quadratic equation: at^2 +bt + c = 0
            double a = temp1.lengthSquared();
            double b = 2 * temp1DotTemp2;
            double c = alignZero(squaredTemp2 - radius * radius);

            double squaredDelta = alignZero(b * b - 4 * a * c);
            if (squaredDelta <= 0) {
                return null;
            }

            double delta = Math.sqrt(squaredDelta);
            double t1 = alignZero((-b + delta) / (2 * a));
            double t2 = alignZero((-b - delta) / (2 * a));




            if (t1 > 0 && t2 > 0 ) {
                return List.of(
                        new GeoPoint(this, ray.getPoint(t1)),
                        new GeoPoint(this, ray.getPoint(t2))
                );
            }
            if (t1 > 0 ) {
                 return List.of(new GeoPoint(this, ray.getPoint(t1)));
            }
            if (t2 > 0 ) {
                return List.of(new GeoPoint(this, ray.getPoint(t2)));            }

            return null;
        }
}
