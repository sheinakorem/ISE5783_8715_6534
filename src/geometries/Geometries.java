package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;


public class Geometries extends Intersectable {

    /**
     * List of intersectables
     */
    public final List<Intersectable> intersectables;


    /**
     * Default constructor.
     * Creates an empty list of intersectables.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * Creates a list of given intersectables.
     *
     * @param intersectables List of intersectables
     */
    public Geometries(Intersectable... intersectables) {
        this.intersectables = new LinkedList<>(Arrays.asList(intersectables));
    }

    /**
     * Adds a list of given intersectables to the current list.
     *
     * @param intersectables List of intersectables to add
     */
    public void add(Intersectable... intersectables) {
        Collections.addAll(this.intersectables, intersectables);
     }

    /**
     * the function gets a ray and returns list of intersection points of the ray with all the geometries in the scene
     * @param ray the ray
     * @return list of Point3D that intersect the osef
     */

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;

        for (var elem : intersectables) {
            List<GeoPoint> tempIntersections = elem.findGeoIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<GeoPoint>();
                intersections.addAll(tempIntersections);
            }

        }
        return intersections;
    }
}


