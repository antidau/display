package display.draw;

/**
 * Send Images via some serial port to the Display to be shown there.
 * Nothing is implemented yet, all methods throw an
 * UnsupportedOperationException.
 */
public class SerialDrawer extends Drawer{

    @Override
    public void drawImage(Image data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
