package display.scene.sound;

import java.util.HashSet;
import java.util.Set;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * Get sound data with javax.sound.sampled
 */
public class JavaSource extends AudioSource {

    @Override
    public void stop() {
        running = false;
        //TODO: Block until thread is gone
    }

    protected AudioFormat getFormat() {
        float sampleRate = getSamplesPerSecond();
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate,
                sampleSizeInBits, channels, signed, bigEndian);
    }
    boolean running = false;
    final static int bufferSize = 1024;

    public JavaSource() throws LineUnavailableException {
        final AudioFormat format = getFormat();
        DataLine.Info info = new DataLine.Info(
                TargetDataLine.class, format);
        final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        Runnable runner = new Runnable() {
            byte buffer[] = new byte[bufferSize];
            int bufferOffset = 0;

            long readTime =0;
            long calcTime=0;
            long output;
            int count=0;

            public void run() {
                running = true;
                output= System.nanoTime();
                while (running) {
                    long beforeRead = System.nanoTime();
                    bufferOffset +=
                            line.read(buffer, bufferOffset, buffer.length - bufferOffset);

                    long afterRead = System.nanoTime();
                    readTime+=afterRead-beforeRead;

                    //TODO: new thread?
                    if (bufferOffset == bufferSize) {
                        count++;
                        long beforeCalc = System.nanoTime();
                        for (SoundSourceListener l : listeners) {
                            l.dataArrived(buffer);
                        }
                        long afterCalc = System.nanoTime();
                        calcTime += afterCalc-beforeCalc;

                        bufferOffset = 0;
                        buffer = new byte[bufferSize];
                        //TODO: reuse old buffers
                    }
                    long current = System.nanoTime();
                    //Every second
                    if ((current-output) > 1000000000) {
                        //System.out.println(count+" frames");
                        count=0;
                        //System.out.println(100f*calcTime/(calcTime+readTime)+"% calc");
                        readTime=0;
                        calcTime=0;
                        output = current;
                    }
                }
            }
        };
        Thread captureThread = new Thread(runner);
        captureThread.start();
    }

    @Override
    public int getSamplesPerSecond() {
        return 44100;
    }

    @Override
    public int getBufferSize() {
        return bufferSize;
    }
    Set<SoundSourceListener> listeners = new HashSet<SoundSourceListener>();

    @Override
    public boolean addSoundSourceListener(SoundSourceListener l) {
        return listeners.add(l);
    }

    @Override
    public boolean removeSoundSourceListener(SoundSourceListener l) {
        return listeners.remove(l);
    }
}
