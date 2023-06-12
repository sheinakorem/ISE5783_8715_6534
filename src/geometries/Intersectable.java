package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

public abstract class Intersectable {

    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    //public List<GeoPoint> findGeoIntersections(Ray ray) {
    //   return findGeoIntersectionsHelper(ray);
    // }

    // protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Represents a geographic point with associated geometry and coordinates.
     */
    public static class GeoPoint {


        /**
         * The geometry associated with the geographic point.
         */
        public Geometry geometry;
        /**
         * The coordinates of the geographic point.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint object.
         *
         * @param geometry the geometry associated with the GeoPoint
         * @param point    the point associated with the GeoPoint
         */

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        /**
         * @param o object
         * @return whether recieved GeoPoint o is equal to this one
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    Objects.equals(point, geoPoint.point);
        }

        /**
         * override function to print the values of the class
         *
         * @return point and geometry
         */
        @Override
        public String toString() {
            return "GeoPoint" +
                    "geometry=" + geometry +
                    ", point=" + point;
        }
    }


}
