package display.scene.sound;

/**
 *
 * @author Jakob Wenzel
 */
public interface SoundFftListener {
    public void dataArrived(byte[] raw, double[] fourier);    
}
