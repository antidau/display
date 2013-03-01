package display.scene;

import display.draw.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Scene generates content for Images.
 */
public abstract class Scene {
    public abstract String getName();
    /**
     * Stuff to do when shutting down. Is not abstract because a scene might not
     * want to do anything there.
     */
    public void stop() {}
    /**
     * Is called whenever there is a new image to draw
     * @param img The image to draw on
     * @param delta Time in seconds since last call to drawFrame
     */
    public abstract void drawFrame(Image img,float delta);
    
    /**
     * Sleep for a given time
     * TODO: Is this still needed?
     * @param millis 
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
