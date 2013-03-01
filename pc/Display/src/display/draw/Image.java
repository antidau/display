package display.draw;

/**
 * The content to be shown at a specific point in time.
 */
public class Image {
    /**
     * Width of the Image
     */
    final public static int WIDTH = 64;
    /**
     * Height of the Image
     */
    final public static int HEIGHT = 8;
    /**
     * Displayed image content.
     * Minimum value =0, Maximum value=1, y first then x
     */
    public float[][] data;
    /**
     * Create new Image
     */
    public Image() {
        data = new float[HEIGHT][WIDTH];
    }
    /**
     * Fill the whole image with one color
     * @param color The color to fill the image with
     */
    public void fill(float color) {
        fillRect(0,0,WIDTH,HEIGHT,color);
    }
    
    /**
     * Fill a rectangle with a color. Lower boundaries are inclusive, upper are
     * exclusive. Coordinates outside the Image range will be clamped to the
     * visible area.
     * @param left Left boundary, inclusive
     * @param top Top boundary, inclusive
     * @param right Right boundary, exclusive
     * @param bottom Bottom boundary, exclusive
     * @param color The color to fill with
     */
    public void fillRect(int left, int top, int right, int bottom, float color) {
        int leftSafe = left>0?left:0;
        int topSafe = top>0?top:0;
        int rightSafe = right<Image.WIDTH?right:Image.WIDTH;
        int bottomSafe = bottom<Image.HEIGHT?bottom:Image.HEIGHT;
        for (int y=topSafe;y<bottomSafe;y++)
            for (int x=leftSafe;x<rightSafe;x++)
                data[y][x]=color;
    }
}
