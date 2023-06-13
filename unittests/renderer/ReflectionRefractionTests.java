/**
 *
 */
package renderer;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.getGeometries().add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.getLights().add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.getGeometries().add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)
                                .setkT(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));

        scene.getLights().add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.getGeometries().add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }



       // private Scene scene = new Scene.SceneBuilder("Effects Test Scene").build();
//       public void effectsDemo() {
//           Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
//                   .setVPSize(200, 200).setVPDistance(1000);
//
//           scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//           // Create the bodies with different effects
//           scene.getGeometries().add(
//           new Sphere(50, new Point(0, 0, -50))
//                   .setEmission(new Color(BLUE))
//                   .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
//
//            new Sphere(25, new Point(-60, 0, -150))
//                   .setEmission(new Color(BLUE))
//                   .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
//
//           new Sphere(35, new Point(60, 0, -150))
//                   .setEmission(new Color(BLUE))
//                   .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(80)),
//
////                   new Triangle(new Point(-100, -100, -20),
////                           new Point(100, -100, -20),
////                           new Point(0, 100, -20))
////                           .setEmission(new Color(20, 20, 20)) //
////                           .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
//           new Plane(new Point(0, -55, 0), new Vector(0, 1, 0))
//                   .setEmission(new Color(20, 20, 20)) //
//                   .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
//
//
//
//           scene.getLights().add(new SpotLight(new Color(400, 240, 0), new Point(50, 50, 500), new Vector(0, 0, -1)) //
//                   .setKl(1E-5).setKq(1.5E-7));
//           // Add shadows with different colors
//
//           camera.setImageWriter(new ImageWriter("effectsDemo", 500, 500))
//                   .setRayTracer(new RayTracerBasic(scene))
//                   .renderImage()
//                   .writeToImage();
//       }
       @Test
       public void PR07() {
           scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)); // Set the ambient light
           Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                   .setVPSize(200, 200).setVPDistance(1000);
           scene.getGeometries().add(
                   new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                           .setMaterial(new Material().setkS(0.8).setnShininess(60).setkR(new Double3(0.5, 0, 0.4))),
                   new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                           .setMaterial(new Material().setkS(0.8).setnShininess(60).setkR(new Double3(0.5, 0, 0.4))),
                   new Sphere(10, new Point(-50, 20, -11))
                           .setEmission(new Color(BLUE))
                           .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
                   new Sphere(30, new Point(0, 0, -11))
                           .setEmission(new Color(BLUE))
                           .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
                   new Sphere(20, new Point(50, -50, -11))
                           .setEmission(new Color(BLUE))
                           .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30))
           );

           scene.getLights().add(
                   new SpotLight(new Color(700, 400, 400), new Point(40, 40, 40), new Vector(-1, -1, -4))
                           .setKl(4E-4).setKq(2E-5)
           );
           ImageWriter imageWriter = new ImageWriter("PR07.1", 600, 600);
           camera.setImageWriter(imageWriter) //
                   .setRayTracer(new RayTracerBasic(scene)) //
                   .renderImage() //
                   .writeToImage();
       }
    }


