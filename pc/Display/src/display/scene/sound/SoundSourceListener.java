package display.scene.sound;

/**
 * Listener which is called when a AudioSource has filled its buffer.
 */
public interface SoundSourceListener {
    public void dataArrived(byte[] data);
}
