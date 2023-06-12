package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera {

    // Eye of the pinhole camera
    private final Point p0;

    // 3D positional vector towards the view plane
    private final Vector vTo;

    // 3D positional vector upwards
    private final Vector vUp;

    // Calculated 3D positional vector to the right
    private final Vector vRight;

    // Physical width of the view plane
    private double width;

    // Physical height of the view plane
    private double height;

    // Distance from the eye of the camera to the view plane
    private double distance;

    // Image writer to output the rendered image
    private ImageWriter imageWriter;

    // Ray tracer base for rendering the scene
    private RayTracerBase rayTracer;

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("The vectors should be perpendicular.");
        }

        // Position of the camera in 3D space
        this.p0 = p0;

        // Normalizing vTo and vUp
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        // No need to normalize
        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * Set the size of the view plane.
     *
     * @param width  The width of the view plane
     * @param height The height of the view plane
     * @return The Camera object itself
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set the distance from the camera's eye to the view plane.
     *
     * @param distance The distance to the view plane
     * @return The Camera object itself
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Set the ImageWriter for the camera.
     *
     * @param imageWriter The ImageWriter object
     * @return The Camera object itself
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Set the RayTracerBase for rendering the scene.
     *
     * @param rayTracer The RayTracerBase object
     * @return The Camera object itself
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Construct a ray from the camera through a specified pixel.
     *
     * @param nX The number of pixels in the X direction
     * @param nY The number of pixels in the Y direction
     * @param j  The pixel index in the X direction
     * @param i  The pixel index in the Y direction
     * @return The constructed ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //view plane Center
        Point Pc = p0.add(vTo.scale(distance));

        //pixel ratios
        double Ry = (double) height / nY;
        double Rx = (double) width / nX;

        //Point of the center pixel in Nx,Ny coordinates
        // starting from view plane center
        Point Pij = Pc;

        //offsets for Pij
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;

        //adding opffsts if necessary
        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));
        }

        // return ray from camera through view plane Pij
        return new Ray(p0, Pij.subtract(p0));
    }

    /**
     * Render the image using the configured image writer and ray tracer.
     *
     * @return The Camera object itself
     */

    public Camera renderImage() {
        try {
            // if one of the fields hasn't been initialized throw an exception
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            //go over all the pixels
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    // construct a ray through the current pixel
                    Ray ray = this.constructRay(nX, nY, j, i);
                    // get the  color of the point from trace ray
                    Color color = rayTracer.traceRay(ray);
                    // write the pixel color to the image
                    imageWriter.writePixel(j, i, color);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }


    private Color castRay(int nX, int nY, int i, int j) {
//        Ray ray = constructRay(nX, nY, j, i);
//        Color color = rayTracer.traceRay(ray);
//        imageWriter.writePixel(j, i, color);
        Ray ray = constructRay(nX, nY, j, i);
        return rayTracer.traceRay(ray);

    }


    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("missing imageawriter", "Camera", "in print Grid");
        for (int j = 0; j < imageWriter.getNx(); j++) {
            for (int i = 0; i < imageWriter.getNy(); i++) {
                //grid 16 X 10
                if (j % interval == 0 || i % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Function renderImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public Camera writeToImage() {
        // if imageWriter hasn't been initialized throw an exception
        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }

        imageWriter.writeToImage();
        return this;
    }

}