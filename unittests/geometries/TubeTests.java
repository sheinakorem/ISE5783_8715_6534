package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {
    void testConstructor() {
        double expectedRadius = 2.5;
        Ray expectedAxisRay = new Ray(new Vector(0, 0, 1), new Point(0, 0, 0));
        Tube tube = new Tube(expectedRadius, expectedAxisRay);
        assertEquals(expectedRadius, tube.radius, 0.0001);
        assertEquals(expectedAxisRay, tube.axisRay);
    }

    /**
     * Test for when the connection between the point on the body and the ray’s head creates a 90 degrees with the ray (boundary test)
     * The test checks if the normal to the tube at the point of intersection with the ray is perpendicular to the axis ray
     */
    @Test
    void testGetNormalAt90Degrees() {
        Tube tube = new Tube(2.0, new Ray( new Vector(0, 0, 1) , new Point(0, 0, 0)));
        Point p = new Point(2, 0, 0);
        Vector expectedNormal = new Vector(2, 0, 0).normalize();
        Vector actualNormal = tube.getNormal(p);
        assertEquals(expectedNormal, actualNormal);
        assertTrue(actualNormal.dotProduct(tube.axisRay.dir) < 0.0001);
    }

}