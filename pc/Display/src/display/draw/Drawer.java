package display.draw;

/**
 * Base class to everything that can draw Images on Screen or on LED Displays or
 * anything else.
 */
public abstract class Drawer {
   public abstract void drawImage(Image data); 
   public abstract void stop();
}
