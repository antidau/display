/*
 * unsigned, 8 bit mono!
 */
package display.scene.sound;

/**
 *
 * @author wilson
 */
public abstract class AudioSource {
    public abstract void stop();
    public abstract int getSamplesPerSecond();
    public abstract int getBufferSize();
    public abstract boolean addSoundSourceListener(SoundSourceListener l);
    public abstract boolean removeSoundSourceListener(SoundSourceListener l);
}
