package primitives;

import java.util.Objects;

public class Ray {
    Point p0;
    Vector dir;

    public Ray(Vector v, Point p){
        this.p0=p;
        this.dir=v.normalize();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }


    @Override
    public String toString() {
        return "Ray" +"p0=" + p0 +", dir=" + dir ;
    }
}
