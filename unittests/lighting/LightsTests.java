package lighting;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private static final int SHININESS = 301;
    private static final double KD = 0.5;
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
    private static final double KS = 0.5;
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    private static final double SPHERE_RADIUS = 50d;
    private final Scene scene1 = new Scene.SceneBuilder("Test scene").build();
    private final Scene scene2 = new Scene.SceneBuilder("Test scene")//
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)))
            .build();
    private final Camera camera1 = new Camera(
            new Point(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setVPSize(150, 150)
            .setVPDistance(1000);
    private final Camera camera2 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(200, 200)
            .setVPDistance(1000);
    private final Material material = new Material().setkD(KD3).setkS(KS3).setnShininess(SHININESS);
    private final Color trianglesLightColor = new Color(800, 500, 250);
    private final Color sphereLightColor = new Color(800, 500, 0);
    private final Color sphereColor = new Color(BLUE).reduce(2);
    private final Point sphereCenter = new Point(0, 0, -50);
    // The triangles' vertices:
    private final Point[] vertices =
            { // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
            .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setnShininess(SHININESS));
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKl(new Double3(0.001)).setKq(new Double3(0.0002)));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                .setKl(new Double3(0.001)).setKq(new Double3(0.0001)));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));


        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKl(0.001).setKq(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage(); //
        camera2.writeToImage(); //
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spotlight
     */
    @Test
    public void sphereSpotSharp() {
        scene1.getGeometries().add(sphere);
        scene1.getLights()
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                        .setNarrowBeam(10).setKl(0.001).setKq(0.00004));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a narrow spotlight
     */
    @Test
    public void trianglesSpotSharp() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection).
                setNarrowBeam(10).setKl(0.001).setKq(0.00004));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }


    /**
     * Produce a picture of a sphere lighted by a multiple lights
     */
    @Test
    public void sphereMultiLights() {
        scene1.getGeometries().add(sphere);
        //direction light
        scene1.getLights().add(new DirectionalLight(new Color(600, 350, 0), new Vector(30, 30, -1)));
        //point light
        scene1.getLights().add(new PointLight(new Color(500, 0, 0), new Point(900, 100, -500))
                .setKl(0.01).setKq(0.01));
        //spotlight
        scene1.getLights().add(new SpotLight(new Color(600, 0, 0), new Point(-200, 100, 50), new Vector(1, 1, -2)) //
                .setKl(0.05).setKq(0.05));

        ImageWriter imageWriter = new ImageWriter("lightsMultiLightSphere", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage(); //
        camera1.writeToImage(); //
    }

    /**
     * Produce a picture of  two triangles lighted by a multiple lights
     */
    @Test
    public void TrianglesMultiLight() {
        scene2.getGeometries().add(triangle1.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)));
        //direction light
        scene2.getLights().add(new DirectionalLight(new Color(400, 30, 0), new Vector(0.65, 0, -1)));
        //spotlight
        scene2.getLights().add(new SpotLight(new Color(500, 250, 250), new Point(10, -10, -130), new Vector(-150100, -1000, 1)) //
                .setKl(0.0001).setKq(0.000005));
        //point light
        scene2.getLights().add(new PointLight(new Color(500, 250, 250), new Point(110, -10, -130)) //
                .setKl(0.0005).setKq(0.0005));


        ImageWriter imageWriter = new ImageWriter("lightsMultiLightTriangles", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage();//
        camera2.writeToImage(); //
    }


}