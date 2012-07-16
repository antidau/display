/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import display.draw.Image;

/**
 *
 * @author wilson
 */
public class SineScene extends Scene {

    protected void updateImage(Image img, float a, int b) {
        for (int y = 0; y < Image.HEIGHT; y++) {
            for (int x = 0; x < Image.WIDTH; x++) {
                img.data[y][x] = (float) (Math.sin(a + 2 * Math.PI * (x % Image.WIDTH) / Image.WIDTH) + 1) / 2;
            }
        }

    }
    float a = 0;
    int b = 0;
    float t;
    float speed = 1;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void drawFrame(Image img, float delta) {
        updateImage(img, a, b);
        t += delta;
        a = t * speed;
    }

    @Override
    public String getName() {
        return "Sine";
    }
}
