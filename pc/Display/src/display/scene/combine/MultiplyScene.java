package display.scene.combine;

import display.draw.Image;
import display.scene.Scene;

/**
 * Combine other Scenes by multiplying every pixel value.
 */
public class MultiplyScene extends Scene {

    @Override
    public String getName() {
        return "Multiply";
    }
    Scene[] scenes;

    public MultiplyScene(Scene[] scenes) {
        this.scenes = scenes;
    }

    public MultiplyScene(Scene a, Scene b) {
        scenes = new Scene[2];
        scenes[0] = a;
        scenes[1] = b;
    }

    @Override
    public void drawFrame(Image img, float delta) {
        img.fill(1);
        Image single = new Image();
        for (int i = 0; i < scenes.length; i++) {
            scenes[i].drawFrame(single, delta);

            for (int y = 0; y < Image.HEIGHT; y++) {
                for (int x = 0; x < Image.WIDTH; x++) {
                    img.data[y][x]*=single.data[y][x];
        
                }
            }
        }
    }
}
