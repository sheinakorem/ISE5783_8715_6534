package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

class ImageWriterTests {

    @Test
    void testsWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("TestTurquoise", 800, 500);

        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                // 800/16=50, 500/10=50
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, primitives.Color.BLACK);
                } else {
                    imageWriter.writePixel(i, j, new Color(255, 255, 0));
                }

            }
        }
        imageWriter.writeToImage();
    }
}