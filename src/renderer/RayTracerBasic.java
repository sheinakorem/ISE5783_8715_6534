package renderer;

import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import geometries.Intersectable.GeoPoint;

import static java.lang.Math.*;
import static primitives.Util.*;


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

        GeoPoint closestPoint = findClosestIntersection(ray);
        if(closestPoint==null) {
            return scene.getBackground();
        }
        return calcColor(closestPoint,ray);

       // return closestPoint == null ? scene.getBackground() :
        //        calcColor(closestPoint, ray);

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

        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK; //base color

        //for each light source in the scene
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(intersection.point); //the direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); //nl=n*l

            //if the light hits the point add it

                if (nl * nv > 0) {

//                    if (unshaded(intersection, v, n, nv, lightSource)) {
//                        Color lightIntensity = lightSource.getIntensity(intersection.point);
//                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
//                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
//                    }
                    if(!unshaded(intersection,l,n,nl,lightSource)){
                        Color iL =lightSource.getIntensity(intersection.point);
                        color =color.add((calcDiffusive(kd,l,n,iL)),(calcSpecular(ks,l,n,v,nShininess,iL)));
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
        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.kR).
                add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.kT));
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
        Geometry geometry= intersection.geometry;
        Color color = geometry.getEmission().add( calcLocalEffect(intersection, ray));
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
        double ln = alignZero(abs(l.dotProduct(n))); //ln=|l*n|
        return lightIntensity.scale(kd.scale(ln)); //Kd * |l * n| * Il
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
        double ln = alignZero(l.dotProduct(n)); //ln=l*n
        Vector r = l.subtract(n.scale(2 * ln)).normalize(); //r=l-2*(l*n)*n
        double vr = alignZero(v.dotProduct(r)); //vr=v*r
        double vrnsh = pow(max(0, -vr), nShininess); //vrnsh=max(0,-vr)^nshininess
        return lightIntensity.scale(ks.scale(vrnsh)); //Ks * (max(0, - v * r) ^ Nsh) * Il
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
        if( intersections ==null) {
            return false;
        }

        //check if any intersection closer to light then current point - thanks to Yaeli
        for(GeoPoint point1 :intersections){
            double d =point1.point.distance(lightRay.getP0());
            if (d<light.getDistance(point1.point)){
                return false;
            }
        }

        return true;
    }

    /**
     * construct the refracted ray of the point on the geometry
     *
     * @param n     normal vector
     * @param point on the geometry
     * @return new ray
     */
    private Ray constructRefractedRay(Point point, Vector l, Vector n) {
        return new Ray(point, l, n);
    }

    /**
     * @param point point on the geometry
     * @param n     normal vector
     * @param l
     * @return new ray
     */
    private Ray constructReflectedRay(Point point, Vector n, Vector l) {

        double vn = alignZero(n.dotProduct(l));
        Vector r = l.subtract(n.scale(2 * vn).normalize());
        // move the head
        return new Ray(point, n, r);
    }

}