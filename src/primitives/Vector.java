package primitives;

import java.util.Objects;

/**
 * vector class that inserts point
 *  @author michal slutzkin & sheina korem
 */
public class Vector extends Point{

    /**
     * vector constructor receiving three numbers of type double
     * uses Point constructor
     * @param x=x
     * @param y=y
     * @param z=z
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero");
        }
    }

    /**
     * vector constructor that accepts an object of type Double3
     * @param xyz=xyz
     */
    public Vector(Double3 xyz) {
        super(xyz.d1, xyz.d2, xyz.d3);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero");
        }
    }

    /**
     *vector connection (returns a new vector)
     * @param vector1=vector
     * @return new vector
     */
    public Vector add(Vector vector1  ){
        return new Vector(xyz.add(vector1.xyz));
    }

    /**
     *multiplying a vector by a number
     * @param d=the number we multiply by
     * @return Vector after the multiplication
     */
    public Vector scale(double d){
        return new Vector(xyz.scale(d));
    }

    /**
     * scalar multiplication using an algebraic formula
     * @param v=vector
     * @return double
     */

    public double dotProduct(Vector v){

        return (this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3);
    }

    /**
     * vector multiplication - returns a new vector perpendicular to the two vectors
     * @param v=a second vector
     * @return ector perpendicular to the two vectors
     */
    public Vector crossProduct(Vector v){
        double w1 = this.xyz.d2 * v.xyz.d3- this.xyz.d3 * v.xyz.d2;
        double w2 = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double w3 = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;

        return new Vector(w1, w2, w3);

    }

    /**
     * calculation of the squared length of the vector
     * @return squared length of the vector
     */
     public double lengthSquared (){
        return (xyz.d1* xyz.d1 + xyz.d2*xyz.d2 + xyz.d3*xyz.d3);
    }

    /**
     * calculation of the length of  vector
     * @return length of vector
     */
    public double length() {
       return Math.sqrt(lengthSquared());
    }

    /**
     * calculate the normal of a vector
     * @return normalized vector
     */
    public Vector normalize(){

        double magnitude = Math.sqrt(xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3);
        if (magnitude == 0) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vector(xyz.d1 / magnitude, xyz.d2 / magnitude, xyz.d3 / magnitude);
    }

    /**override function for equal operation
     * @param o=o
     * @return True if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vector v))
            return false;
        return Objects.equals(xyz, v.xyz);
    }

    /**
     * override function of toString
     * @return the vector
     */
    @Override
    public String toString() {
        return "vector"+xyz ;
    }

}
