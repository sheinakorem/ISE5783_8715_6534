package primitives;

import java.util.Objects;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero");
        }
    }

    public Vector(Double3 xyz) {
        super(xyz.d1, xyz.d2, xyz.d3);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero");
        }
    }
    public Vector add(Vector vector1  ){
        return new Vector(xyz.add(vector1.xyz));
    }
    public Vector scale(double d){
        return new Vector(xyz.scale(d));
    }

    public double dotProduct(Vector v){

        return (this.xyz.d1 * v.xyz.d1 +
        this.xyz.d2 * v.xyz.d2 +
        this.xyz.d3 * v.xyz.d3);
    }
    public Vector crossProduct(Vector v){
        double w1 = this.xyz.d2 * v.xyz.d3- this.xyz.d3 * v.xyz.d2;
        double w2 = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double w3 = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;

        return new Vector(w1, w2, w3);

    }
     public double lengthSquared (){
        return (xyz.d1* xyz.d1 + xyz.d2*xyz.d2 + xyz.d3*xyz.d3);
    }
    public double length() {
       return Math.sqrt(lengthSquared());
    }
    public Vector normalize(){


        double magnitude = Math.sqrt(xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3);
        if (magnitude == 0) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vector(xyz.d1 / magnitude, xyz.d2 / magnitude, xyz.d3 / magnitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vector v))
            return false;
        return Objects.equals(xyz, v.xyz);
    }


    @Override
    public String toString() {
        return "vector"+xyz ;
    }

}
