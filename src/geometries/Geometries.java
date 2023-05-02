package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Geometries implements Intersectable {

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = new LinkedList<>();
        for (var elem : intersectables) {
            List<Point> elemPoints = elem.findIntersections(ray);
            if (elemPoints != null) {
                result.addAll(elemPoints);
            }
        }

        if(result.isEmpty()){
            return  null;
        }

        return result;
    }

}


