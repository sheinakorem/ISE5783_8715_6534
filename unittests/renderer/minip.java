package renderer;
/**
 *
 */

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
public class minip {
    private Scene scene = new Scene.SceneBuilder("Test scene").build();
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

        scene.getLights().add(new DirectionalLight(new Color(10, 10, 10), new Vector(1, -1, 0)));
                scene.getLights().add(new SpotLight(new Color(500, 400, 400), new Point(0, -300, -100), new Vector(-2, 2, -3)).setKc(1).setKl(0.00001).setKq(0.000005));
                        scene.getLights().add(new SpotLight(new Color(650, 400, 300), new Point(-300, -300, 100), new Vector(2, 2, -3)).setKc(1.0).setKl(0.00001).setKq(0.000005));
                                scene.getLights().add(new SpotLight(new Color(400, 400, 1020), new Point(-400, -300, -120),new Vector(2, 2, -3)).setKc(1).setKl(0.00001).setKq(0.000005));
//        // Add a point light on top of the scene
//        scene.getLights().add(
//                new PointLight(new Color(800, 800, 800), new Point(0, 0, 50)) //
//                        .setKl(0.0001).setKq(0.00005)
//        );

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
