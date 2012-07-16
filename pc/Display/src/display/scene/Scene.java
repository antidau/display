/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import display.draw.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilson
 */
public abstract class Scene {
    public abstract String getName();
    public void stop() {}
    public abstract void drawFrame(Image img,float delta);
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
