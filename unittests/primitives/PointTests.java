package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for primitives.Point class
 *
 * @author michal slutzkin & sheina korem
 */
class PointTests {

    /**
     * Test method for {@link primitives.Point# Point.Subtract(point).}
     */
    @Test
    void testsSubtract() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(new Point(1, 2, 3)), //
                "Wrong point subtract");

        // =============== Boundary Values Tests ==================
        // TC11: test subtracting same point
        assertThrows(IllegalArgumentException.class, () -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)), //
                "Subtract P from P must throw exception");
    }

    /**
     * Test method for {@link primitives.Point# Point.Add(vector).}
     */
    @Test
    void testsAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Point(2, 3, 4), new Point(1, 1, 1).add(new Vector(1, 2, 3)), "Wrong point add");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }

    /**
     * Test method for {@link primitives.Point# Point.DistanceSquared(point).}
     */
    @Test
    void testsDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d, new Point(1, 1, 1).distanceSquared(new Point(2, 3, 4)), 0.0001, //
                "Wrong squared distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distanceSquared(new Point(1, 2, 3)), 0.0001, //
                "Wrong squared distance between the point and itself");
    }

    /**
     * Test method for {@link primitives.Point# Point.Distance(point).}
     */
    @Test
    void testsDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(Math.sqrt(14), new Point(1, 1, 1).distance(new Point(2, 3, 4)), 0.0001, //
                "Wrong distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distance(new Point(1, 2, 3)), 0.0001, //
                "Wrong distance between the point and itself");
    }

}