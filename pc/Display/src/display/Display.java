/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.draw.AwtDrawer;
import display.draw.Drawer;
import display.draw.Image;
import display.editor.WebEditor;
import display.scene.Scene;
import display.scene.SineScene;

/**
 *
 * @author wilson
 */
public class Display implements StopListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Without doing anything with comparables, i get exceptions otherwise...
        System.setProperty("java.util.Arrays.useLegacyMergeSort", new Boolean(true).toString());
        
        new Display();
    }
    boolean stop;
    public Display() {
                //AudioSource source = new PulseSource();
        //Drawer drawer = new AwtDrawer(this);
        //Scene scene = new SineScene();
        new WebEditor().start();
        
        /*Image img = new Image();
        stop = false;
        long t = System.nanoTime();
        long second = t;
        long frames = 0;
        while (!stop) {
            long oldT = t;
            t = System.nanoTime();
            float delta = (t - oldT)/1000000000f;
            scene.drawFrame(img,delta);
            drawer.drawImage(img);
            
            frames++;
            //TODO: FPS limiter
            if ((t-second)>1000000000) {
                //System.out.println(frames);
                frames=0;
                second = t;
            }
        }
        scene.stop();
        drawer.stop();*/
    }

    @Override
    public void stop() {
        stop = true;
    }
}
