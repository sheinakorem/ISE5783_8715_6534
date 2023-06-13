package renderer;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;


public class RayTracerBasic extends RayTracerBase {


    /**
     * constant number for size moving first rays for shading rays
     */

    private static final Double3 INITIAL_K = Double3.ONE;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * A builder
     *
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {
       //find the closent intersaction points from the ray
        GeoPoint closestPoint = findClosestIntersection(ray);
        //if no points returning the background
        if (closestPoint == null) {
            return scene.getBackground();
        }
        // if there are points send to calcColor to find the color
        return calcColor(closestPoint, ray);

    }

    /* Calculate the local effect of light sources on a point
     *
     * @param intersection the point
     * @param ray          the ray from the viewer
     * @return the color
     */
    private Color calcLocalEffect(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);

        double nv = alignZero(n.dotProduct(v)); //nv=n*v
        if (nv == 0) {
            return Color.BLACK;
        }

        int nShininess = intersection.geometry.getMaterial().getnShininess();
        Double3 kd = intersection.geometry.getMaterial().getkD();
        Double3 ks = intersection.geometry.getMaterial().getkS();
        Color color = Color.BLACK; //base color

        //for each light source in the scene
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(intersection.point); //the direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); //nl=n*l

            //if the light hits the point add it

            if (nl * nv > 0) {
               Double3 ktr = transparency(intersection, lightSource, l, n);
                if (!ktr.product(INITIAL_K).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, n, l, iL),
                            calcSpecular(ks, l, n, v, nShininess, iL));
                }
            }

        }
        return color;

    }


    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that caused the intersection
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.getkR()).
                add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.getkT()));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) {
            return scene.getBackground().scale(kx);
        }
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK :
                calcColor(gp, ray, level - 1, kkx).scale(kx);
    }


    /* returns the color at a certain point
     *
     * @param intersection with the geometry
     * @param ray   the ray from the viewer
     * @return Color of the point
     */

    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Geometry geometry = intersection.geometry;
        Color color = geometry.getEmission().add(calcLocalEffect(intersection, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.getAmbientLight().getIntensity());

    }

    /* Calculate the diffuse light effect on the point
     * @param kd diffuse attenuation factor
     * @param l the direction of the light
     * @param n normal from the point
     * @param lightIntensity the intensity of the light source at this point
     * @return the color
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
//        double ln = alignZero(abs(l.dotProduct(n))); //ln=|l*n|
//        return lightIntensity.scale(kd.scale(ln)); //Kd * |l * n| * Il
        double nl = n.dotProduct(l);
        double abs_nl = Math.abs(nl);
        Double3 amount = kd.scale(abs_nl);
        return lightIntensity.scale(amount);
    }

    /* Calculate the specular light at this point
     * @param ks specular attenuation factor
     * @param l the direction of the light
     * @param n normal from the point
     * @param v direction of the viewer
     * @param nShininess shininess factor of the material at the point
     * @param lightIntensity the intensity of the light source at the point
     * @return the color of the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {

        double nl = n.dotProduct(l);
        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0)
            return Color.BLACK; // view from direction opposite to r vector
        Double3 amount = ks.scale(Math.pow(minusVR, nShininess));
        return lightIntensity.scale(amount);
    }

    /**
     * find the closest intersection point of the ray with the geometry
     *
     * @param ray on the geometry
     * @return the closest geo point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null || intersections.size() == 0) {
            return null;
        } else {
            return ray.findClosestGeoPoint(intersections);
        }
    }


    /**
     * Checks if a point is unshaded by finding any intersections between the point and the light source.
     *
     * @param gp The GeoPoint representing the point to check.
     * @param l  The Vector representing the direction from the point to the light source.
     * @param n  The Vector representing the normal at the point.
     * @param nl The dot product between the normal vector and the direction vector from the point to the light source.
     * @return {@code true} if the point is unshaded, {@code false} otherwise.
     */

    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
        // Compute the direction from the point to the light source
        Vector lightDirection = l.scale(-1);

        // Create  ray from  offset point to  light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        //get the distance
        double maxdistance = light.getDistance(gp.point);
        // Find  intersections between  ray and  geometries

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxdistance);

        // Check if the intersections list is empty
        if (intersections == null || intersections.isEmpty()) {
            return false;
        }

        return true;
    }

//    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
//        return new Ray(point, n, v);
//    }

    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n) {
        //r = v - 2.(v.n).n
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
       // return new Ray(pointGeo, n, r);

    }

    /**
     * Calculates the transparency factor for a given point, light source, and surface normal.
     * This method determines the transparency of the point by checking if there are any intersections
     * between the point and the light source, taking into account the transparency of the intersected geometries.
     *
     * @param gp The point on the surface of the geometry.
     * @param light The light source illuminating the geometry.
     * @param l The direction from the point to the light source.
     * @param n The surface normal at the point.
     * @return The transparency factor as a Double3 vector. If the transparency is negligible, it returns Double3.ZERO.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        // Compute the direction from the point to the light source
        Vector lightDirection = l.scale(-1);

        // Create a shadow ray from the offset point to the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        // Get the maximum distance to the light source
        double maxDistance = light.getDistance(gp.point);

        // Find intersections between the shadow ray and geometries
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        // Check if the intersections list is empty
        if (intersections == null || intersections.isEmpty()) {
            return Double3.ONE;
        }

        Double3 ktr = Double3.ONE;

        // Loop over the intersections and calculate transparency
        for (GeoPoint intersection : intersections) {

            // Multiply ktr by the transparency coefficient of the geometry
              ktr= ktr.product( intersection.geometry.getMaterial().getkT()); // Multiply by kT of the geometry


            // Check if ktr is close to 0
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }

        return ktr;


    }

}