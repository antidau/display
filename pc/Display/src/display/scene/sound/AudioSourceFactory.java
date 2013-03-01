package display.scene.sound;

/**
 * Provide Access to one preset AudioSource.
 */
public class AudioSourceFactory {
    static AudioSource source;
    public static void setAudioSource(AudioSource asource) {
        source = asource;
    }
    public static AudioSource getAudioSource() {
        return source;
    }
}
