package primitives;

import java.util.Objects;

/**
 * class that represents Ray
 * @author michal slutzkin & sheina korem
 */
public class Ray {
    Point p0;
    Vector dir;

    /**
     * constructor that accepts a point and a vector
     * @param v= normalized vector
     * @param p =point
     */
    public Ray(Vector v, Point p){
        this.p0=p;
        this.dir=v.normalize();
    }

    /**
     * override function for equal operation
     * @param o= object o
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * override function of toString
     * @return print of p0 and direction of the vector
     */
    @Override
    public String toString() {
        return "Ray" +"p0=" + p0 +", dir=" + dir ;
    }
}
