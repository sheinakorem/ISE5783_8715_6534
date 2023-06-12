package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    // A point with coordinates (0,0,0)
    private final Point ZEROP = new Point(0, 0, 0);


    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), new Double3(1, 1, 1)))
                .setBackground(new Color(75, 127, 90))
                .build(); // set background color

        scene.getGeometries().add(
                new Sphere(50d, new Point(0, 0, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(ZEROP, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(YELLOW));
        camera.writeToImage();
    }

    // For stage 6 - please disregard in stage 5

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * /** bodies and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))).build(); //

        scene.getGeometries().add( // center
                new Sphere(50, new Point(0, 0, -100)),
                // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                // down left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));

        Camera camera = new Camera(ZEROP, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("color render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();
    }
}
/**
 * Test for XML based scene - for bonus
 */
// @Test
//   public void basicRenderXml() {
//      Scene  scene  = new Scene.SceneBuilder("XML Test scene").build();
//      // enter XML file name and parse from XML file into scene object
//      // using the code you added in appropriate packages
//      // ...
//      // NB: unit tests is not the correct place to put XML parsing code
//
//      Camera camera = new Camera(ZEROP, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
//         .setVPDistance(100)                                                                //
//         .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//         .setRayTracer(new RayTracerBasic(scene));
//      camera.renderImage();
//      camera.printGrid(100, new Color(YELLOW));
//      camera.writeToImage();
//   }
//}
