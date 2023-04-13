package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    @Test
    public void testNormal() {
        // Create a plane with 3 points constructor
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        Plane plane = new Plane(p1, p2, p3);

        // Check that the plane's normal vector is normalized (has length 1)
        Vector normal = plane.getNormal();
        double length = normal.length();
        assertEquals(1.0, length, 0.0001);
    }


        @Test
        public void testConstructor() {
            // Test valid constructor call
            Point p1 = new Point(1.0, 2.0, 3.0);
            Point p2 = new Point(4.0, 5.0, 6.0);
            Point p3 = new Point(7.0, 8.0, 9.0);
            Plane plane = new Plane(p1, p2, p3);
            assertNotNull(plane);

            // Test constructor call with two identical points
            assertThrows(IllegalArgumentException.class, () -> {
                Plane invalidPlane = new Plane(p1, p1, p3);
            });

            // Test constructor call with points on the same line
            Point p4 = new Point(10.0, 11.0, 12.0);
            Vector v1 = p2.subtract(p1);
            Vector v2 = p3.subtract(p1);
            Vector v3 = p4.subtract(p1);
            assertThrows(IllegalArgumentException.class, () -> {
                Plane invalidPlane = new Plane(p1, p2, p4);
            });
            assertThrows(IllegalArgumentException.class, () -> {
                Plane invalidPlane = new Plane(p1, v1, v3);
            });
        }
}



