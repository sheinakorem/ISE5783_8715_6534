package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 * @author michal slutzkin & sheina korem
 */
class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle# Triangle.Triangle(Point,Point,Point).}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Triangle
                    (
                            new Point(1, 0, 0),
                            new Point(0, 1, 0),
                            new Point(0, 0, 1)
                    );
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct triangle");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        try {
            new Triangle
                    (
                            new Point(1, 0, 0),
                            new Point(1, 0, 0),
                            new Point(0, 0, 1)
                    );
            fail("Constructed a triangle while a point equal to b point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03: Test when a point equal to c point.
        try {
            new Triangle
                    (
                            new Point(1, 0, 0),
                            new Point(0, 0, 1),
                            new Point(1, 0, 0)
                    );
            fail("Constructed a triangle while a point equal to c point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC04: Test when b point equal to c point.
        try {
            new Triangle
                    (
                            new Point(1, 0, 0),
                            new Point(0, 0, 1),
                            new Point(0, 0, 1)
                    );
            fail("Constructed a triangle while b point equal to c point");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * Test method for {@link geometries.Triangle# Triangle.getNormal(Point).}
     */
    @Test
    public void testGetNormal() {
        Triangle triangle = new Triangle
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );
        double n = Math.sqrt(1d / 3);
        assertEquals(new Vector(n, n, n), triangle.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");

    }

}