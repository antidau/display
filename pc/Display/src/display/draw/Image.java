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
    final public static int WIDTH = 128;
    final public static int HEIGHT = 16;
    //Minimum value =0, Maximum value=1, y first then x
    public float[][] data;
    public Image() {
        data = new float[HEIGHT][WIDTH];
    }
}
