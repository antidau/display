package display.scene.sound;

/**
 * Provide access to currently running sound in unsigned, 8 bit mono format.
 */
public abstract class AudioSource {
    /**
     * Shutdown
     */
    public abstract void stop();
    /**
     * Get the number of samples this source delivers per second
     * @return Samples per second
     */
    public abstract int getSamplesPerSecond();
    /**
     * Get the Size of the Buffer sent to connected SoundSourceListeners
     * @return Buffer Size
     */
    public abstract int getBufferSize();
    /**
     * Add a SoundSourceListener which is called everytime the buffer is full.
     * @param l The listener to add
     * @return true if was not already added
     */
    public abstract boolean addSoundSourceListener(SoundSourceListener l);
    /**
     * Remove a SoundSourceListener
     * @param l The listener to remove
     * @return true if it was added before
     */
    public abstract boolean removeSoundSourceListener(SoundSourceListener l);
}
