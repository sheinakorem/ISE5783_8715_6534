/**
 *
 */
package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
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
    @Test
public void MP1() {
        Camera camera = new Camera(new Point(0, 0, 5000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        // Create the first cube building
        Point p1 = new Point(-100, -200, -500);
        Point p2 = new Point(100, -200, -500);
        Point p3 = new Point(100, 200, -500);
        Point p4 = new Point(-100, 200, -500);
        Polygon face1 = new Polygon(p1, p2, p3, p4);
        face1.setMaterial(new Material()).setEmission(new Color(128, 128, 128)); // Gray color for the building

// Create the second cube building
        Point p5 = new Point(200, -150, -700);
        Point p6 = new Point(500, -150, -700);
        Point p7 = new Point(500, 150, -700);
        Point p8 = new Point(200, 150, -700);
        Polygon face2 = new Polygon(p5, p6, p7, p8);
        face2.setMaterial(new Material()).setEmission(new Color(64, 64, 64)); // Dark gray color for the building

// Create windows as lights for the buildings
//        double windowWidth = 30;
//        double windowHeight = 40;
//
//// Window on building 1
//        Point window1Center = new Point(-50, 0, -500);
//        Polygon window1 = new Polygon(windowWidth, windowHeight, window1Center, new Vector(0, 0, -1));
//        window1.setMaterial(new Material()).setEmission(new Color(255, 255, 0)); // Yellow light from the window
//        //face1.addChild(window1);
//
//// Window on building 2
//        Point window2Center = new Point(350, 0, -700);
//        Polygon window2 = new Polygon(windowWidth, windowHeight, window2Center, new Vector(0, 0, -1));
//        window2.setMaterial(new Material()).setEmission(new Color(0, 255, 255)); // Cyan light from the window
//        //face2(window2);


        // Create windows as lights for the buildings
        double windowRadius = 15;

// Window on building 1
        Point window1Center = new Point(-50, 0, -500);
        Sphere window1 = new Sphere(windowRadius, window1Center);
        window1.setEmission(new Color(255, 255, 0)); // Yellow light from the window
        scene.getGeometries().add(window1);

// Window on building 2
        Point window2Center = new Point(350, 0, -700);
        Sphere window2 = new Sphere(windowRadius, window2Center);
        window2.setEmission(new Color(0, 255, 255)); // Cyan light from the window
        scene.getGeometries().add(window2);

// Create the scene and add objects
        Scene scene = new Scene.SceneBuilder("Building Scene").build();
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.3)); // Dim ambient light

        scene.getGeometries().add(face1, face2,window1,window2);



        ImageWriter imageWriter = new ImageWriter("MP1", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
  //  @Test
//    public void MP1() {
//
//
//            Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                    .setVPSize(2500, 2500).setVPDistance(10000); //
//            // Create the walls
//        scene.getGeometries().add(
//          new Plane(new Point(0, 0, -100), new Vector(0, 0, 1))
//            .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100)),
//
//         new Plane(new Point(-100, 0, 0), new Vector(1, 0, 0))
//            .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100)),
//
//             new Plane(new Point(100, 0, 0), new Vector(-1, 0, 0)).
//                     setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100)),
//
//            // Create the floor
//            new Plane(new Point(0, -100, 0), new Vector(0, 1, 0))
//                    .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100)));
//
//        // Create the window
//        Point windowCenter = new Point(0, 50, -99);
//        Vector windowWidthVector = new Vector(1, 0, 0);
//        Vector windowHeightVector = new Vector(0, 0, -1);
//        Point topLeft = windowCenter.add(windowWidthVector.scale(-0.5)).add(windowHeightVector.scale(0.5));
//        Point topRight = windowCenter.add(windowWidthVector.scale(0.5)).add(windowHeightVector.scale(0.5));
//        Point bottomLeft = windowCenter.add(windowWidthVector.scale(-0.5)).add(windowHeightVector.scale(-0.5));
//        Point bottomRight = windowCenter.add(windowWidthVector.scale(0.5)).add(windowHeightVector.scale(-0.5));
//
//        Polygon window = new Polygon(topLeft, topRight, bottomRight, bottomLeft);
//        window.setMaterial(new Material().setkD(0.2).setkS(0.8).setkR(0.8).setkT(0.2).setnShininess(100));
//
//
//        // Create the lamp
//            double lampRadius = 10;
//            double lampHeight = 50;
//            Point lampCenter = new Point(-50, -50, -99);
//            Cylinder lampBase = new Cylinder(lampHeight, new Ray(lampCenter, new Vector(0, 0, -1)), lampRadius);
//            lampBase.setEmission(new Color(255, 255, 0)).setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100));
//
//            // Create the scene and add objects
//            Scene scene = new Scene.SceneBuilder("Room with Window and Lamp").build();
//            scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.2));
//            //scene.getGeometries().add(backWall, leftWall, rightWall, floor, window, lampBase);
//
//            // Add lights
//            scene.getLights().add(new SpotLight(new Color(WHITE), new Point(-50, -50, 100), new Vector(0, 0, -1)).setKl(0.0001).setKq(0.000005));
//
//
//
//
//        ImageWriter imageWriter = new ImageWriter("MP1", 500, 500);
//         camera.setImageWriter(imageWriter) //
//                .setRayTracer(new RayTracerBasic(scene)) //
//                .renderImage() //
//                .writeToImage();
//    }


    @Test
    public void pyramidOfSpheresTest() {
        // Create the scene
        Scene scene = new Scene.SceneBuilder("Pyramid of Spheres").build();
        //scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.0015)); // Set the ambient light
        // Create the spheres
        double sphereRadius = 10;
        double spacing = sphereRadius * 2;

        // Create the pyramid of spheres
        // First row
        Sphere sphere1 = new Sphere(sphereRadius, new Point(-30, 0, -150));
        sphere1.setEmission(new Color(BLUE))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));
        scene.getGeometries().add(sphere1);

        Sphere sphere2 = new Sphere(sphereRadius, new Point(-10, 0, -150));
        sphere2.setEmission(new Color(BLUE))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));
        scene.getGeometries().add(sphere2);

        Sphere sphere3 = new Sphere(sphereRadius, new Point(10, 0, -150));
        sphere3.setEmission(new Color(BLUE))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));
        scene.getGeometries().add(sphere3);

        Sphere sphere4 = new Sphere(sphereRadius, new Point(30, 0, -150));
        sphere4.setEmission(new Color(BLUE))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));
        scene.getGeometries().add(sphere4);

        // Second row
        Sphere sphere5 = new Sphere(sphereRadius, new Point(-20, spacing, -150));
        sphere5.setEmission(new Color(RED))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.9));
        scene.getGeometries().add(sphere5);

        Sphere sphere6 = new Sphere(sphereRadius, new Point(0, spacing, -150));
        sphere6.setEmission(new Color(RED))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.9));
        scene.getGeometries().add(sphere6);

        Sphere sphere7 = new Sphere(sphereRadius, new Point(20, spacing, -150));
        sphere7.setEmission(new Color(RED))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.9));
        scene.getGeometries().add(sphere7);

        // Third row
        Sphere sphere8 = new Sphere(sphereRadius, new Point(-10, 2 * spacing, -150));
        sphere8.setEmission(new Color(GREEN))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));
        scene.getGeometries().add(sphere8);

        Sphere sphere9 = new Sphere(sphereRadius, new Point(10, 2 * spacing, -150));
        sphere9.setEmission(new Color(GREEN))
                .setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));
        scene.getGeometries().add(sphere9);

        // Last row
        Sphere sphere10 = new Sphere(sphereRadius, new Point(0, 3 * spacing, -150));
        sphere10.setEmission(new Color(YELLOW)).
                setMaterial(new Material().setkD(new Double3(0.6, 0.6, 0.6)).setkS(new Double3(0.4, 0.4, 0.4)).setnShininess(100).setkT(0.5));

        scene.getGeometries().add(sphere10,
                new Plane(new Point(0, -10, 0), new Vector(0, 1, 0))
                .setEmission(new Color(216, 216, 216)) //
                .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));

        // Add light sources

//            scene.getLights().add(new SpotLight(new Color(white), new Point(0, 140, 200), new Vector(0, 0, -1)) //
//                        .setKl(1E-5).setKq(5E-8));

        scene.getLights().add(new SpotLight(new Color(YELLOW), new Point(0, 200, 500), new Vector(0, 0, -1))
                .setKl(0.00000001).setKq(0.00000000005));

        scene.getLights().add(new SpotLight(new Color(YELLOW), new Point(0, -200, 500), new Vector(0, 0, -1)) //
                .setKl(0.0000000004).setKq(0.0000000002));


        // Set up the camera
        Camera camera = new Camera(new Point(0, 0, 50), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(200)
                .setRayTracer(new RayTracerBasic(scene));
        ImageWriter imageWriter = new ImageWriter("Pyramid of Spheres", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }



    /** Produce a picture of a scene with two 3D rectangles */
    @Test
    public void twoRectangles() {

        Camera camera = new Camera(new Point(2500, -10, 50), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.getGeometries().add(

                // Rectangle 3 (Smallest)
                // Front face
                new Polygon(
                        new Point(-25, -25, -15),
                        new Point(25, -25, -15),
                        new Point(25, 25, -15),
                        new Point(-25, 25, -15)
                ).setEmission(new Color(250, 219, 216)),

                // Back face
                new Polygon(
                        new Point(-25, -25, -85),
                        new Point(25, -25, -85),
                        new Point(25, 25, -85),
                        new Point(-25, 25, -85)
                ).setEmission(new Color(250, 219, 216)),

                // Left face
                new Polygon(
                        new Point(-25, -25, -85),
                        new Point(-25, -25, -15),
                        new Point(-25, 25, -15),
                        new Point(-25, 25, -85)
                ).setEmission(new Color(250, 219, 216)),

                // Right face
                new Polygon(
                        new Point(25, -25, -85),
                        new Point(25, -25, -15),
                        new Point(25, 25, -15),
                        new Point(25, 25, -85)
                ).setEmission(new Color(250, 219, 216)),

                // Top face
                new Polygon(
                        new Point(-25, 25, -85),
                        new Point(25, 25, -85),
                        new Point(25, 25, -15),
                        new Point(-25, 25, -15)
                ).setEmission(new Color(250, 219, 216)),

                // Bottom face
                new Polygon(
                        new Point(-25, -25, -85),
                        new Point(25, -25, -85),
                        new Point(25, -25, -15),
                        new Point(-25, -25, -15)
                ).setEmission(new Color(250, 219, 216)).setMaterial(new Material().setkR(0.5)),

                // Rectangle 2 (Top)
                new Polygon(
                        new Point(-50, -50, -50),
                        new Point(50, -50, -50),
                        new Point(50, 50, -50),
                        new Point(-50, 50, -50)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                new Polygon(
                        new Point(50, -50, -50),
                        new Point(50, -50, -100),//G
                        new Point(50, 50, -100),//F
                        new Point(50, 50, -50)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                new Polygon(
                        new Point(50, -50, -100),
                        new Point(-50, -50, -100),
                        new Point(-50, 50, -100),
                        new Point(50, 50, -100)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                new Polygon(
                        new Point(-50, -50, -100),
                        new Point(-50, -50, -50),
                        new Point(-50, 50, -50),
                        new Point(-50, 50, -100)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                new Polygon(
                        new Point(-50, -50, -50),
                        new Point(50, -50, -50),
                        new Point(50, -50, -100),
                        new Point(-50, -50, -100)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                new Polygon(
                        new Point(-50, 50, -50),
                        new Point(50, 50, -50),
                        new Point(50, 50, -100),
                        new Point(-50, 50, -100)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                new Polygon(
                        new Point(-50, -50, -100),
                        new Point(50, -50, -100),
                        new Point(50, 50, -100),
                        new Point(-50, 50, -100)
                ).setEmission(new Color(241, 148, 138)).setMaterial(new Material().setkR(0.5)),

                // Rectangle 1 (Bottom)
                new Polygon(
                        //top face of cube
                        new Point(-75, -75, -100),//A
                        new Point(75, -75, -100),//B
                        new Point(75, 75, -100),//C
                        new Point(-75, 75, -100)//D
                ).setEmission(new Color(236, 112, 99)).setMaterial(new Material().setkR(0.5)
                ),

                new Polygon(
                        new Point(75, -75, -100),
                        new Point(75, -75, -150),
                        new Point(75, 75, -150),
                        new Point(75, 75, -100)
                ).setEmission(new Color(236, 112, 99)).setMaterial(new Material().setkR(0.5)
                ),

                new Polygon(
                        new Point(75, -75, -150),
                        new Point(-75, -75, -150),
                        new Point(-75, 75, -150),
                        new Point(75, 75, -150)
                ).setEmission(new Color(236, 112, 99)).setMaterial(new Material().setkR(0.5)
                ),

                new Polygon(
                        new Point(-75, -75, -150),
                        new Point(-75, -75, -100),
                        new Point(-75, 75, -100),
                        new Point(-75, 75, -150)
                ).setEmission(new Color(236, 112, 99)).setMaterial(new Material().setkR(0.5)
                ),

                new Polygon(
                        new Point(-75, -75, -100),
                        new Point(75, -75, -100),
                        new Point(75, -75, -150),
                        new Point(-75, -75, -150)
                ).setEmission(new Color(236, 112, 99)).setMaterial(new Material().setkR(0.5)
                ),

                new Polygon(
                        new Point(-75, 75, -100),
                        new Point(75, 75, -100),
                        new Point(75, 75, -150),
                        new Point(-75, 75, -150)
                ).setEmission(new Color(236, 112, 99)).setMaterial(new Material().setkR(0.5)
                ),

                // Plane
                new Plane(new Point(0, 0, -150), new Vector(0, 0, 1))
                        .setEmission(new Color(80, 80, 80)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0.3).setkR(0.4))


        );

//        scene.getLights().add(
//                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, -500), new Vector(-1, -1, -2)) //
//                        .setKl(0.0004).setKq(0.0000006)
//        );

//        scene.getLights().add(
//                new DirectionalLight(new Color(1000, 600, 0), new Vector(-1, -1, -2)) //
//
//        );
        //Add a sphere representing the cherries
        scene.getGeometries().add(
                new Sphere(5, new Point(0, 40, -45))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(0, -40, -45))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(50, -15, -45))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(50, 15, -45))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(0, -65, -95))
                        .setEmission(new Color(210, 4, 45))
        );
        scene.getGeometries().add(
                new Sphere(5, new Point(0, 65, -95))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(80, 30, -95))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(80, -30, -95))
                        .setEmission(new Color(210, 4, 45))
        );

        scene.getGeometries().add(
                new Sphere(5, new Point(80, 0, -95))
                        .setEmission(new Color(210, 4, 45))
        );

        // Add a point light on top of the scene
        scene.getLights().add(
                new PointLight(new Color(800, 800, 800), new Point(0, 0, 50)) //
                        .setKl(0.0001).setKq(0.00005)
        );

//        scene.getLights().add(
//                new PointLight(new Color(255, 255, 0), new Point(0, 0, 10)) //
//                        .setKl(0.0001).setKq(0.00005)
//        );
        camera.setImageWriter(new ImageWriter("twoRectanglesScene", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }









}






