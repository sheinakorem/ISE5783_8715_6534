package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.nio.channels.NotYetBoundException;

import static primitives.Util.isZero;

public class Camera{
    //eye of the pinhole Camera
    private Point p0;

    //3D positional Vector towards the view plane
    private Vector vTo;

    //3D positional Vector upwards
    private Vector vUp;

    // calculated 3D positional Vector to the right
    private Vector vRight;

    // physical width of the viewplane
    private double width;

    // physical height of the viewplane
    private double height;

    //distance from the eye of the camera to the view plane
    private double distance;

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("the vectors should be perpendicular");
        }

        // position of the camera in 3D space
        this.p0 = p0;

        //normalizing vTo and vUp
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        //no need to normalize
        this.vRight = this.vTo.crossProduct(this.vUp);
    }


    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        //view plane Center
        Point Pc = p0.add(vTo.scale(distance));

        //pixel ratios
        double Ry = height / nY;
        double Rx = width / nX;

        //Point of the center pixel in Nx,Ny coordinates
        // starting from view plane center
        Point Pij = Pc;

        //offsets for Pij
        double Yi = -(i - (nY - 1) / 2.0) * Ry;
        double Xj = (j - (nX - 1) / 2.0) * Rx;

        //adding opffsts if necessary
        if(!isZero(Xj)){
            Pij = Pij.add(vRight.scale(Xj));
        }
       if(!isZero(Yi)){
            Pij = Pij.add(vUp.scale(Yi));
        }

       // return ray from camera through view plane Pij
       return new Ray(p0,Pij.subtract(p0));
    }




}
