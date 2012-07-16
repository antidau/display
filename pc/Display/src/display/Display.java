/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.editor.WebEditor;

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
        SceneManager manager = new SceneManager();
        new WebEditor(manager).start();
                //AudioSource source = new PulseSource();
        /*MultiDrawer drawer = new MultiDrawer();
        drawer.addDrawer(new AwtDrawer(this));
        Scene scene = new SineScene();
        
        Image img = new Image();
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
