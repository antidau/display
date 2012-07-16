/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.draw;

import display.BeanEditor;
import display.StopListener;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

//TODO: Quadratic pixelz
/**
 *
 * @author wilson
 */
public class AwtDrawer extends Drawer {
    JFrame frame;
    JPanel[][] content;

    public AwtDrawer(final StopListener listen) {
        frame = new JFrame();
        frame.setLayout(new GridLayout(Image.HEIGHT, Image.WIDTH));

        frame.setSize(640, 480);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listen.stop();
            }
        });

        content = new JPanel[Image.HEIGHT][Image.WIDTH];

        for (int y = 0; y < Image.HEIGHT; y++) {
            for (int x = 0; x < Image.WIDTH; x++) {
                JPanel panel = new JPanel();

                content[y][x] = panel;
                frame.add(panel);
            }
        }


        frame.setVisible(true);
    }

    @Override
    public void drawImage(Image data) {
        for (int y = 0; y < Image.HEIGHT; y++) {
            for (int x = 0; x < Image.WIDTH; x++) {
                float d = data.data[y][x];
                JPanel panel = content[y][x];
                Color c = new Color(0f, 0f, d);
                panel.setBackground(c);
            }
        }
        frame.repaint();
    }

    @Override
    public void stop() {
        frame.dispose();
    }
}
