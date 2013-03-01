package display.draw;

import java.util.ArrayList;
import java.util.List;

/**
 * Drawer that relays every draw request to a list of drawers, therefore making
 * it possible to have multiple drawers.
 */
public class MultiDrawer extends Drawer{

    ArrayList<Drawer> drawers;
    @Override
    public void drawImage(Image data) {
        for (Drawer d : drawers) {
            d.drawImage(data);
        }
    }

    @Override
    public void stop() {
        for (Drawer d : drawers) {
            d.stop();
        }
    }
    
    public MultiDrawer() {
        drawers = new ArrayList<Drawer>();
    }
    
    public void addDrawer(Drawer d) {
        drawers.add(d);
    }
    
    public boolean containsDrawer(Drawer d) {
        return drawers.contains(d);
    }
    
    public List<Drawer> getDrawers() {
        return (List<Drawer>)drawers.clone();
    }
    
    public boolean removeDrawer(Drawer d) {
        return drawers.remove(d);
    }
    
}
