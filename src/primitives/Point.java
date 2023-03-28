package primitives;

import java.util.Objects;
public class Point {
    protected Double3 xyz;

    public Point(Double3 xyz) {
        this.xyz=new Double3(xyz.d1,xyz.d2,xyz.d3);

    }

    public Point (double x, double y, double z) {
        xyz= new Double3(x,y,z);
   }

    public Vector subtract (Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }
    public Point add(Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    public double distanceSquared(Point p1){
        return ( ( p1.xyz.d1-this.xyz.d1) * (p1.xyz.d1- this.xyz.d1) +
            (p1.xyz.d2 - this.xyz.d2) * (p1.xyz.d2 - this.xyz.d2) +
            (p1.xyz.d3- this.xyz.d3) * (p1.xyz.d3 - this.xyz.d3));

    }
    public double distance(Point p1){
        return Math.sqrt(distanceSquared(p1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point" +xyz;
    }
}
