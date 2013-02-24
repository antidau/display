/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.draw;

/**
 *
 * @author wilson
 */
public class Image {
    final public static int WIDTH = 64;
    final public static int HEIGHT = 8;
    /* Displayed image content.
     * Minimum value =0, Maximum value=1, y first then x
     */
    public float[][] data;
    public Image() {
        data = new float[HEIGHT][WIDTH];
    }
    public void fill(float color) {
        for (int y=0;y<HEIGHT;y++)
            for (int x=0;x<WIDTH;x++)
                data[y][x]=color;
    }
}
