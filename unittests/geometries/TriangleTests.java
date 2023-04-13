package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    @Test
    public void testNormalIsNormalized() {
        // Create a triangle with known vertices
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        Triangle triangle = new Triangle(p1, p2, p3);

        // Get the normal of the triangle
        Vector normal = triangle.getNormal(p1);

        // Check that the length of the normal is 1 (within a certain tolerance)
        assertEquals(1.0, normal.length(), 0.0001);
    }

}