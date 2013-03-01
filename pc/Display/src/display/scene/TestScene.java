package display.scene;

import display.draw.Drawer;
import display.draw.Image;

/**
 * Display an ugly test animation.
 */
public class TestScene extends Scene {

    protected void updateImage(Image img, int a, int b) {
        for (int y = 0; y < Image.HEIGHT; y++) {
            for (int x = 0; x < Image.WIDTH; x++) {
                img.data[y][x] = (float) ((x + a) % Image.WIDTH) / Image.WIDTH
                        * ((y + b) % Image.HEIGHT) / Image.HEIGHT;

            }
        }

    }
    int a = 0;
    int b = 0;
    float t;

    public void drawFrame(Image img, float delta) {
        updateImage(img, a, b);
        t += delta;
        if (t > 0.1) {
            t -= 0.1;
            a++;
            if (a % 3 == 0) {
                b++;
            }
            while (a > Image.WIDTH) {
                a -= Image.WIDTH;
            }
            while (b > Image.HEIGHT) {
                b -= Image.HEIGHT;
            }
        }
    }

    @Override
    public String getName() {
        return "Test";
    }
}
