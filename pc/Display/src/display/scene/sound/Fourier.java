/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene.sound;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jakob Wenzel
 */
public class Fourier implements SoundSourceListener {

    double[] imagData;

    @Override
    public void dataArrived(byte[] data) {
        //Convert to array of doubles
        double[] realData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            realData[i] = data[i];
        }

        //Apply fft
        //TODO: Use better fft function, skip calculating upper half, rely on imag being zero
        double[] fft = FFTbase.fft(realData, imagData, true);

        //Calculate norm of vectors squared
        double[] result = new double[data.length / 2];
        for (int i = 0; i < data.length / 2; i++) {
            double a = fft[2 * i];
            double b = fft[2 * i + 1];
            double res = a * a + b * b;
            result[i] = res;
        }
        for (SoundFftListener l : listeners) {
            l.dataArrived(data, result);
        }
    }
    AudioSource source;

    public Fourier(AudioSource source) {
        this.source = source;
        source.addSoundSourceListener(this);
        imagData = new double[source.getBufferSize()];
    }

    /**
     * Get the frequency increase per array step
     *
     * @return The difference in frequency between two array entries
     */
    public double getFrequencyIncrease() {
        return 1f * source.getSamplesPerSecond() / source.getBufferSize();
    }
    
    public int getResultSize() {
        return source.getBufferSize() / 2;
    }
    
    public AudioSource getSource() {
        return source;
    }
    
    Set<SoundFftListener> listeners = new HashSet<SoundFftListener>();

    public boolean addSoundFftListener(SoundFftListener l) {
        return listeners.add(l);
    }

    public boolean removeFftActionListener(SoundFftListener l) {
        return listeners.remove(l);
    }
}
