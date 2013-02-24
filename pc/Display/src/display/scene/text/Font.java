package display.scene.text;

/**
 *
 * @author Jakob Wenzel
 */
public abstract class Font {
        /**
     * Get the pixel data for the given character, or, if not available, the
     * data for the fallback character.
     *
     * @param c The character to which the data should be returned
     * @param fallback The char which should be rendered if the requested char
     * is not available. If this is null or also not available, the return value
     * will also be null.
     * @return The given Char's pixels.
     */
    public abstract int[] getCharacter(char c, Character fallback);

    /**
     * Get the pixel data for the given character
     *
     * @param c The character to which the data should be returned
     * @return The given Char's pixels or null if not available.
     */
    public abstract int[] getCharacter(char c);
}
