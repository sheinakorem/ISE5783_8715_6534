package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Plane class
 * @author michal slutzkin & sheina korem
 */

public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane# Plane.getNormal(Point).}
     */
    @Test
    public void testGetNormal() {
        Plane plane = new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );
        double n = Math.sqrt(1d / 3);
        assertEquals(new Vector(n, n, n), plane.getNormal(new Point(0, 0, 1)), "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane# Plane.Plane(Point, Point,Point).}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Plane
                    (
                            new Point(1, 0, 0),
                            new Point(0, 1, 0),
                            new Point(0, 0, 1)
                    );
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct plane");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        try {
            new Plane
                    (
                            new Point(1, 0, 0),
                            new Point(1, 0, 0),
                            new Point(0, 0, 1)
                    );
            fail("Constructed a plane while a point equal to b point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03: Test when a point equal to c point.
        try {
            new Plane
                    (
                            new Point(1, 0, 0),
                            new Point(0, 0, 1),
                            new Point(1, 0, 0)
                    );
            fail("Constructed a plane while a point equal to c point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC04: Test when b point equal to c point.
        try {
            new Plane
                    (
                            new Point(1, 0, 0),
                            new Point(0, 0, 1),
                            new Point(0, 0, 1)
                    );
            fail("Constructed a plane while b point equal to c point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC05: Test when all 3 points are on the same line.
        try {
            new Plane
                    (
                            new Point(1, 2, 3),
                            new Point(2, 3, 4),
                            new Point(3, 4, 5)
                    );
            fail("Constructed a plane while all 3 point on the same plane");
        } catch (IllegalArgumentException ignored) {
        }
    }
}



