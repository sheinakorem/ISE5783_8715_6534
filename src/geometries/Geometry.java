package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * An interface called  Geometry for geometric shapes
 *
 * @author michal slutzkin & sheina korem
 */
public abstract class Geometry extends Intersectable {


    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * material getter
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set material
     *
     * @param material
     * @return this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * simple getter
     *
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter
     *
     * @param emission=emission
     * @return this
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * function that gets point and returns a normalized vector
     *
     * @param p = point
     * @return normalized vector
     */
    abstract public Vector getNormal(Point p);


    // protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
