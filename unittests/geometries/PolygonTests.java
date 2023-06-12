package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void findIntersections() {
        double delta = 0.001;
        Polygon polygon = new Polygon(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-2, 0, 0),
                new Point(0, -1, 0)
        );
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the polygon
        result = polygon.findIntersections(new Ray(new Point(-0.5, -0.5, -1), new Vector(0.5, 0.5, 3)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-0.33333333333333337, -0.33333333333333337, 0), result.get(0), "Ray doesn't intersect the polygon");

        //TC02:Ray outside against vertex
        assertNull(polygon.findIntersections(new Ray(new Point(0, -2, 0), new Vector(0, 0, 4))), "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(polygon.findIntersections(new Ray(new Point(-1, -1, 0), new Vector(0, 0, 3))), "Ray isn't outside against edge");

        //TC04:Ray inside the polygon
        assertNull(polygon.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, 0, 0))), "Ray  isn't inside the polygon");

        // ============ Boundary Values Tests =============
        //TC11: Ray On edge
        result = polygon.findIntersections(new Ray(new Point(-2, 0, 3), new Vector(1.03d, 0.51d, -3)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-0.97d, 0.51d, 0d), result.get(0), "Ray  isn't on edge of the polygon");

        ///TC12: Ray in vertex
        assertNull(polygon.findIntersections(new Ray(new Point(0, 1, 0), new Vector(-2d, -1d, 3))), "Ray  isn't on vertex of the polygon");

        //TC13: Ray On edge's continuation
        assertNull(polygon.findIntersections(new Ray(new Point(-1, 2, 0), new Vector(-1d, -2d, 3))), "Ray  isn't On edge's continuation");


    }

    /**
     * Test method for {@link geometries.Plane#findGeoIntersectionsHelper(Ray, double)}
     */
    @Test
    void findGeoIntersectionsHelperTest1() {

        //region ***test for intersection points with consideration to maxDistance parameter
        Polygon square1 = new Polygon(
                new Point(-1, 0, 0),
                new Point(-1, 0, 1),
                new Point(1, 0, 1),
                new Point(1, 0, 0));
        Ray ray1 = new Ray(new Point(0, -3, 0.5), new Vector(0, 1, 0));
        Intersectable.GeoPoint gp1 = new Intersectable.GeoPoint(square1, new Point(0, 0, 0.5));

        /**
         * Test method for {@link Triangle#findGeoIntersectionsHelper(Ray, double)}
         */
        // TC01 -  max distance is smaller than distance to intersection point - no intersections
        assertNull(square1.findGeoIntersectionsHelper(ray1, 2), "points are further than maxDistance");

        /**
         * Test method for {@link Triangle#findGeoIntersectionsHelper(Ray, double)}
         */
        //TC02 -  max distance is larger than distance to  intersection point - one intersection point
        List<Intersectable.GeoPoint> res = square1.findGeoIntersectionsHelper(ray1, 5);
        assertEquals(List.of(gp1), res, "point is in boundary");

    }
}

