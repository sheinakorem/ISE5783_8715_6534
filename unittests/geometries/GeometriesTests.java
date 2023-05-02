package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void findIntersections() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================
        //TC01: empty geometries list
        assertNull(geometries.findIntersections(new Ray(new Vector(1.0, 0.0, 5.0), new Point(0.0, 1.0, 0.0))));

        geometries.add(new Plane(new Point(1.0, 1.0, 0.0), new Vector(0.0, 0.0, 1.0)));
        geometries.add(new Triangle(new Point(1.0, 0.0, 0.0), new Point(0.0, 1.0, 0.0), new Point(0.0, 0.0, 1.0)));
        geometries.add(new Sphere(1.0, new Point(1.0, 0.0, 0.0)));

        //TC02: each geometry doesn't have intersection points
        assertNull(geometries.findIntersections(new Ray(new Vector(0.0, -1.0, 0.0), new Point(0.0, 0.0, 2.0))));

        List<Point> points = geometries.findIntersections(new Ray(new Vector(0.0, 0.0, 1.0), new Point(0.0, 5.0, -1.0)));
        //TC03: just one geometry has intersections point
        assertEquals(1, points.size());

        //TC04: all the geometries have intersection points
        Geometries geometries1 = new Geometries(
                new Sphere(0.5, new Point(0, 0, 2)),
                new Polygon(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 0, 0),
                        new Point(0, -1, 0)
                ),
                new Triangle(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                )
        );
        List<Point> result;
        result = geometries1.findIntersections(new Ray(new Vector(0, 0, 1), new Point(0.2, 0.2, -0.6)));
        assertEquals(4, result.size(), "All geometries intersects");

        // ============ Equivalence Partitions Tests ==============
        //TC11: part of the geometries has intersection points
        assertEquals(2, geometries.findIntersections(new Ray(new Vector(0.0, 0.0, 1.0), new Point(1.0, 0.0, -1.0))).size());
    }
}


   /* public  void testFindIntersections3() {
        //TC04: all the geometries have intersection points
      *//*  Geometries all_geometries = new Geometries();
        all_geometries.add(new Plane(new Point(0.0, 1.0, 2.0), new Vector(0.0, -1.0, 0.0)));
        all_geometries.add(new Triangle(new Point(0.4, 3.0, 1.0), new Point(0.0, 3.0, 1.0), new Point(2.0, 3.0, 5.0)));
        all_geometries.add(new Sphere(1.0, new Point(0.0, 5.0, 1.0)));
        assertEquals(3.0, all_geometries.findIntersections(new Ray(new Vector(0.0, 5.0, 1.0),new Point(0.0,0.0,0.0))).size());*//*
        Geometries geometries = new Geometries(
                new Sphere(0.5,new Point(0, 0, 2)),
                new Polygon(
                        new Point( 1, 0, 0),
                        new Point(0,  1, 0),
                        new Point(-1, 0, 0),
                        new Point(0, -1, 0)
                ),
                new Triangle(
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                )
        );
        List<Point> result;
        result=geometries.findIntersections(new Ray(new Vector(0,0,1),new Point(0.2,0.2,-0.6)));
        assertEquals(4,result.size(),"All geometries intersects");
    }
}*/