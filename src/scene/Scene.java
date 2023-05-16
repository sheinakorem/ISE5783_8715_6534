package scene;

import geometries.Geometries;
import primitives.Color;
import lighting.AmbientLight;

public class Scene {
    /**
     * Name of the scene
     */
    private final String name;

    /**
     * Color of the background
     */
    private final Color background;

    /**
     * Ambient light of the scene
     */
    private final AmbientLight ambientLight;

    /**
     * Geometries in the scene
     */
    private final Geometries geometries;

    /**
     * Private constructor for the Scene class.
     * Use the SceneBuilder to construct Scene objects.
     *
     * @param builder the SceneBuilder object containing the scene parameters
     */
    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
    }

    /**
     * Get the name of the scene.
     *
     * @return the name of the scene
     */
    public String getName() {
        return name;
    }

    /**
     * Get the background color of the scene.
     *
     * @return the background color of the scene
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Get the ambient light of the scene.
     *
     * @return the ambient light of the scene
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * Get the geometries in the scene.
     *
     * @return the geometries in the scene
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * The SceneBuilder class is used to construct Scene objects.
     */
    public static class SceneBuilder {
        /**
         * Name of the scene
         */
        private final String name;

        /**
         * Color of the background. The default is black.
         */
        private Color background = Color.BLACK;



        /**
         * Ambient light of the scene. The default is none.
         */
        private AmbientLight ambientLight = new AmbientLight().NONE;

        /**
         * Set the ambient light of the scene.
         *
         * @param ambientLight the ambient light
         * @return the SceneBuilder object
         */

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Geometries in the scene.
         */
        private Geometries geometries = new Geometries();

        /**
         * Set the geometries of the scene.
         *
         * @param geometries the geometries
         * @return the SceneBuilder object
         */
        public SceneBuilder setGeometries(Geometries geometries) {this.geometries = geometries;
            return this;
        }

        /**
         * Create a new SceneBuilder with the specified name.
         *
         * @param name the name of the scene
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        /**
         * Set the background color of the scene.
         *
         * @param background the background color
         * @return the SceneBuilder object
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }






        /**
         * Build the Scene object with the provided parameters.
         *
         * @return the constructed Scene object
         */
        public Scene build() {
            return new Scene(this);
        }
    }
}
