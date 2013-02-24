/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.draw;

import display.StopListener;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

class Content extends Canvas {

    public Image data;

    /**
     * If true, make Width and Height of pixels the same.
     * If false, fill whole window
     */
    final static boolean squarePixels = true;
    
    public void paint() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        int w = this.getWidth() / Image.WIDTH;
        int h = this.getHeight() / Image.HEIGHT;
        if (squarePixels) {
            w = w<h?w:h;
            h = w;
        }
        for (int y = 0; y < Image.HEIGHT; y++) {
            for (int x = 0; x < Image.WIDTH; x++) {
                float d = data.data[y][x];
                Color c = new Color(0f, 0f, d);
                g.setColor(c);
                g.fillRect(x * w, y * h, w, h);
            }
        }
	g.dispose();
	strategy.show();
    }
    BufferStrategy strategy;

    public void init() {
        setIgnoreRepaint(true);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }
}

/**
 *
 * @author wilson
 */
public class AwtDrawer extends Drawer {

    JFrame frame;
    //JPanel[][] content;

    Content content;
    public AwtDrawer(final StopListener listen) {
        frame = new JFrame();

        frame.setSize(640, 480);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listen.stop();
            }
        });
        frame.setVisible(true);
        content = new Content();
        frame.getContentPane().add(content);
        content.init();

    }

    @Override
    public void drawImage(Image data) {
        content.data = data;
        content.paint();
    }

    @Override
    public void stop() {
        frame.dispose();
    }
}
