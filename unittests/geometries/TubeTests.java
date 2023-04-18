package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 *
 * @author michal slutzkin & sheina korem
 */
class TubeTests {


    /**
     * Test method for {@link geometries.Tube# Tube.Tube(radius,Ray).}
     */
    @Test

   public  void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        try {
            new Tube(1, new Ray(new Vector(1, 5, 4), new Point(1, 2, 3)));
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Failed constructor of the correct Tube");
        }
        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        try {
            new Tube(0, new Ray(new Vector(1, 5, 4), new Point(1, 2, 3)));
            fail("Constructed a Tube while a radius can not be 0");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03:Test when the radius negative, -1
        try {
            new Tube(-1, new Ray(new Vector(1, 5, 4), new Point(1, 2, 3)));
            fail("Constructed a Tube while a radius can not be negative");
        } catch (IllegalArgumentException ignored) {
        }
    }


    /**
     * Test method for {@link geometries.Tube# Tube.getNormal(point).}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube= new Tube(1d, new Ray(new Vector(0,0,1),new Point(1,1,0)));

        assertEquals(new Vector(0,-1,0), tube.getNormal(new Point(1,0,2)), "Bad normal to tube");

    }


}