package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;

import static java.lang.Math.*;
import static primitives.Util.*;



public class RayTracerBasic extends RayTracerBase {

    /**
     * constant number for size moving first rays for shading rays
     */

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * A builder
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
     }



    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.getGeometries().findGeoIntersections(ray);

        if (points != null) {
            GeoPoint closePoint = ray.findClosestGeoPoint(points);
            return calcColor(closePoint, ray);
        }
        //no points
        return scene.getBackground();
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
            Vector l = lightSource.getL(intersection.point).normalize(); //the direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); //nl=n*l

            //if sign(nl) == sign(nv) (if the light hits the point add it, otherwise don't add this light)
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /* returns the color at a certain point
     *
             * @param intersection with the geometry
     * @param ray          the ray from the viewer
     * @return Color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        // Calculating the color at a point according to Phong Reflection Model

        return scene.getAmbientLight().getIntensity() //ka*Ia
                .add(intersection.geometry.getEmission(), //+Ie
                        calcLocalEffect(intersection, ray)); //+calculated light contribution from all light sources
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
     *find the closest intersection point of the ray with the geometry
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

}