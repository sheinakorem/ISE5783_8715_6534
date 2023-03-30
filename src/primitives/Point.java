package primitives;

import java.util.Objects;

/**
 * A class that represents a point in space that receives a point of type double3
 * @author michal slutzkin & sheina korem
 */
public class Point {
    protected Double3 xyz;

    /**
     *A constructor that accepts an object of type Double3
     * @param xyz=xyz
     */
    public Point(Double3 xyz) {
        this.xyz=new Double3(xyz.d1,xyz.d2,xyz.d3);

    }

    /**
     * A constructor that accepts three double numbers for the coordinate values
     * @param x=xyz.x
     * @param y=xyz.y
     * @param z=xyz.z
     */
    public Point (double x, double y, double z) {
        xyz= new Double3(x,y,z);
   }

    /**
     * Vector subtraction - receives a  point returns a vector  which the subtraction is performed the action
     * @param p=given point
     * @return vector of the substraction
     */
    public Vector subtract (Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Adding a vector to a point - returns a new point
     * @param vector=vector
     * @return  point on which we added the vector
     */
    public Point add(Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * gets a point and calculates the  distance between two points in a square
     * @param p1=point
     * @return The distance between two points in a square
     */
    public double distanceSquared(Point p1){
        return ( ( p1.xyz.d1-this.xyz.d1) * (p1.xyz.d1- this.xyz.d1) +
            (p1.xyz.d2 - this.xyz.d2) * (p1.xyz.d2 - this.xyz.d2) +
            (p1.xyz.d3- this.xyz.d3) * (p1.xyz.d3 - this.xyz.d3));

    }

    /**
     * distance between 2 points using  distanceSquared
     * @param p1=point
     * @return distance between two points
     */
    public double distance(Point p1){
        return Math.sqrt(distanceSquared(p1));
    }

    /**
     * override function for equal operation
     * @param o=o
     * @return True if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    /**
     * override function for hashcode
     * @return hash of xyz
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * override function of toString
     * @return xyz
     * */
    @Override
    public String toString() {
        return "Point" +xyz;
    }
}
