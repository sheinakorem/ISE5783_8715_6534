package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(5, new Point(1, 2, 3));
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct sphere");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when the radius is 0.
        try {
            new Sphere(0, new Point(1, 2, 3));
            fail("Constructed a sphere while the radius is 0");
        } catch (IllegalArgumentException ignored) {
        }
        // TC03: Test when the radius is negative,-1.
        try {
            new Sphere(-1, new Point(1, 2, 3));
            fail("Constructed a sphere while the radius is negative");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * Test method for {@link geometries.Sphere# Sphere.getNormal(Point).}
     */
    @Test
    void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp = new Sphere(1.0, new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 2)));
    }


}