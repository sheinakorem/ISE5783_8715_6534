package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CylinderTests {
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        try {
            new Cylinder(2, new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 3);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Failed constructor of the correct cylinder");
        }

        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        try {
            new Cylinder(0, new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 5);
            fail("Constructed a cylinder while a radius can not be 0");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03:Test when the radius negative, -1
        try {
            new Cylinder(-1, new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 5);
            fail("Constructed a cylinder while a radius can not be negative");
        } catch (IllegalArgumentException ignored) {
        }
        //TC04: Test when the height 0
        try {
            new Cylinder(5, new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 0);
            fail("Constructed a cylinder while a height can not be 0");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03:Test when the height negative, -1
        try {
            new Cylinder(5, new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), -1);
            fail("Constructed a cylinder while a height can not be negative");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        Cylinder cyl = new Cylinder(1.0, new Ray(new Point(0, 0, 1), new Vector(0, 1, 0)), 1d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Point at a side of the cylinder
        assertEquals(new Vector(0, 0, 1), cyl.getNormal(new Point(0, 0.5, 2)), "Bad normal to cylinder");

        // TC02: Point at a 1st base of the cylinder
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 0, 1.5)), "Bad normal to lower base of cylinder");

        // TC03: Point at a 2nd base of the cylinder
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 1, 0.5)), "Bad normal to upper base of cylinder");

        // =============== Boundary Values Tests ==================
        // TC11: Point at the center of 1st base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 0, 1)), "Bad normal to center of lower base");

        // TC12: Point at the center of 2nd base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 1, 1)), "Bad normal to center of upper base");

        // TC13: Point at the edge with 1st base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 0, 2)), "Bad normal to edge with lower base");

        // TC14: Point at the edge with 2nd base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 1, 2)), "Bad normal to edge with upper base");

    }

//    /**
//     * Test method for {@link geometries.Plane#findGeoIntersectionsHelper(Ray, double)}
//     */
//    @Test
//    void findGeoIntersectionsHelperTest1() {
//        Cylinder cylinder = new Cylinder(1d, new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)), 2d);
//        List<Intersectable.GeoPoint> res = null;
//
//        res = cylinder.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0)), 1d);
//        assertNull(res, "wrong zero intersections");
//
//
//        res = cylinder.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0)), 2.5d);
//        assertEquals(1, res.size(), "wrong one point intersections");
//
//
//        res = cylinder.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0)), 5d);
//        assertEquals(2, res.size(), "wrong two point intersections");
//
//    }
}