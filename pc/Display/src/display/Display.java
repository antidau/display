package display;

import display.draw.AwtDrawer;
import display.draw.Image;
import display.draw.MultiDrawer;
import display.editor.WebEditor;
import display.scene.sound.AudioSourceFactory;
import display.scene.sound.JavaSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

/**
 * Main class to start everything.
 */
public class Display implements StopListener {

    /**
     * Start from command line:
     * Create the display class.
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
            SceneManager manager = new SceneManager();
            WebEditor editor = new WebEditor(manager);
            editor.start();
            AudioSourceFactory.setAudioSource(new JavaSource());
            
            
            
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
                manager.drawFrame(img, delta);
                drawer.drawImage(img);

                frames++;
                try {
                    //TODO: real FPS limiter
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                }
                if ((t - second) > 1000000000) {
                    System.out.println(frames+" fps");
                    frames = 0;
                    second = t;
                }
            }
            manager.stop();
            drawer.stop();
            AudioSourceFactory.getAudioSource().stop();
            editor.stop();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        stop = true;
    }
}
