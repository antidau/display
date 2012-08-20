/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene.sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilson
 */
public class PulseSource extends AudioSource {

    @Override
    public int getSamplesPerSecond() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getBufferSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addSoundSourceListener(SoundSourceListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeSoundSourceListener(SoundSourceListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    class PulseThread extends Thread {

        Process process;
        BufferedReader input;
        boolean wantStop = false;
        PulseSource source;
        char[] buffer;

        @Override
        public void start() {
            try {
                process = Runtime.getRuntime().exec("pacat --record -d alsa_output.pci-0000_00_1b.0.analog-stereo.monitor");
                input = new BufferedReader(new InputStreamReader(process.getInputStream()));

                super.start();
            } catch (IOException ex) {
                Logger.getLogger(PulseSource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        @Override
        public void run() {
            try {
                while (!wantStop) {
                    input.read(buffer);
                    source.newData();
                }
                input.close();
                process.destroy();
            } catch (IOException ex) {
                Logger.getLogger(PulseSource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public PulseThread(PulseSource source) {
            super();
            this.source=source;
            buffer = new char[1024];
        }
    }
    PulseThread thread;

    public PulseSource(SoundSourceListener listener) {
        thread = new PulseThread(this);
        thread.start();
    }

    @Override
    public void stop() {
        thread.wantStop = true;
        //TODO: block until thread is dead
    }
    
    void newData() {
        
    }
}
