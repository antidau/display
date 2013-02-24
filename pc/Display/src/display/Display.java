/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.draw.AwtDrawer;
import display.draw.Image;
import display.draw.MultiDrawer;
import display.scene.Scene;
import display.scene.SoundScene;
import display.scene.sound.AudioSource;
import display.scene.sound.JavaSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

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
        try {
            /* SoundActionListener out = new SoundToStdOut();
             out = new Fourier(out);
             try {
             AudioSource source = new JavaSource(out);
     
             } catch (LineUnavailableException ex) {
             Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            /*SceneManager manager = new SceneManager();
             new WebEditor(manager).start();*/
            AudioSource source = new JavaSource();
            Scene scene = new SoundScene(source);
            
            
            
            MultiDrawer drawer = new MultiDrawer();
            drawer.addDrawer(new AwtDrawer(this));

            Image img = new Image();
            stop = false;
            long t = System.nanoTime();
            long second = t;
            long frames = 0;
            while (!stop) {
                long oldT = t;
                t = System.nanoTime();
                float delta = (t - oldT) / 1000000000f;
                scene.drawFrame(img, delta);
                drawer.drawImage(img);

                frames++;
                //TODO: FPS limiter
                if ((t - second) > 1000000000) {
                    System.out.println(frames+" fps");
                    frames = 0;
                    second = t;
                }
            }
            scene.stop();
            drawer.stop();
            source.stop();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        stop = true;
    }
}
