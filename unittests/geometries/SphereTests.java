package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author michal slutzkin & sheina korem
 */
class SphereTests {

    Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

    /**
     * Test method for {@link geometries.Sphere# Sphere.Sphere(radious,Point).}
     */
    //  @Test
//    public void testConstructor() {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Test for a proper result.
//        try {
//            new Sphere(5, new Point(1, 2, 3));
//        } catch (IllegalArgumentException error) {
//            fail("Failed constructor of the correct sphere");
//        }
//
//        // ============ Boundary Values Tests =============
//        // TC02: Test when the radius is 0.
//        try {
//            new Sphere(0, new Point(1, 2, 3));
//            fail("Constructed a sphere while the radius is 0");
//        } catch (IllegalArgumentException ignored) {
//        }
//        // TC03: Test when the radius is negative,-1.
//        try {
//            new Sphere(-1, new Point(1, 2, 3));
//            fail("Constructed a sphere while the radius is negative");
//        } catch (IllegalArgumentException ignored) {
//        }
//    }


    /**
     * Test method for {@link geometries.Sphere# Sphere.getNormal(Point).}
     */
    @Test
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp = new Sphere(1.0, new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 2)));
    }

    @Test
    public void findIntsersectionsTest() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> exp = List.of(p1, p2);

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));

        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getD1() > result.get(1).getD1()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(exp, result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(p2),
                List.of(sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0), new Vector(3, 1, 0))).get(0)),
                "Ray from inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(3, 1, 0))),
                "Sphere behind Ray");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new Point(2, 0, 0)),
                List.of(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 1, 0))).get(0)),
                "Ray from sphere inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 1, 0))),
                "Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        assertEquals(2, result.size(), "Wrong number of points");
        result = sphere.findIntersections(new Ray(new Point(1, -2, 0), new Vector(0, 1, 0)));

        if (result.get(0).getD2() > result.get(1).getD2()) {
            result = List.of(result.get(1), result.get(0));
        }

        Point p3 = new Point(1, -1, 0);
        Point p4 = new Point(1, 1, 0);
        assertEquals(List.of(p3, p4), result,
                "Line through O, ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(new Point(1, 1, 0)),
                List.of(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0))).get(0)),
                "Line through O, ray from and crosses sphere");

        // TC15: Ray starts inside (1 point)
        assertEquals(List.of(new Point(1, 1, 0)),
                List.of(sphere.findIntersections(new Ray(new Point(1, 0.5, 0), new Vector(0, 1, 0))).get(0)),
                "Line through O, ray from inside sphere");

        // TC16: Ray starts at the center (1 point)
        assertEquals(List.of(new Point(1, 1, 0)),
                List.of(sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0))).get(0)),
                "Line through O, ray from O");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 1, 0))),
                "Line through O, ray from sphere outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(0, 1, 0))),
                "Line through O, ray outside sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray before sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray at sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray after sphere");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1))),
                "Ray orthogonal to ray head -> O line");

    }
}

    /**
     * Test method for {@link geometries.Plane#findGeoIntersectionsHelper(Ray, double)}
     */
//    @Test
//    void findGeoIntersectionsHelperTest1() {
//
//        //region *** test including consideration that points are closer to ray origin than maxDistance parameter
//        // ray and sphere intersect twice at (0,0,3) and (0,6,3)
//        Sphere sphere1 = new Sphere(3, new Point(0, 3, 3));
//        Ray ray = new Ray(new Point(0, -4, 3), new Vector(0, 1, 0));
//        Intersectable.GeoPoint gp1 = new Intersectable.GeoPoint(sphere1, new Point(0, 0, 3));
//        Intersectable.GeoPoint gp2 = new Intersectable.GeoPoint(sphere1, new Point(0, 6, 3));
//
//        // TC01 -  max distance is smaller than distance to both intersection points - no intersections
//       assertNull(sphere1.findGeoIntersectionsHelper(ray,2),"points are further than maxDistance");
//
//        //TC02 -  max distance is smaller than distance to second intersection point - one intersection point
//        List<Intersectable.GeoPoint> res = sphere1.findGeoIntersectionsHelper(ray,5);
//        assertEquals(List.of(gp1),res,"one point only is in boundary");
//
//        //TC03 -  distance to both points is smaller than maxDistance - two intersection points
//        List<Intersectable.GeoPoint> res2 = sphere1.findGeoIntersectionsHelper(ray,12);
//        assertEquals(List.of(gp1,gp2),res2,"one point only is in boundary");
//
//    }
//}