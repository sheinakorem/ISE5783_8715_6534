package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    Sphere sphere = new Sphere(1d, new Point(1, 0, 0));


    @Test
    void getNormalTest1() {
        Sphere sp = new Sphere(1.0, new Point(0, 0, 1));
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point(0,0,2)));
    }

    @Test
    void getNormalTest2() {
        Sphere sp = new Sphere(1,new Point(0,0,1));
        assertNotEquals(new Vector(0,0,1),sp.getNormal(new Point(0,1,1)));
        System.out.println(sp.getNormal(new Point(0,1,1)));
    }
}