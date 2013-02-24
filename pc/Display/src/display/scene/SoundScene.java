/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import ddf.minim.analysis.FFT;
import display.draw.Image;
import display.scene.sound.AudioSource;
import display.scene.sound.SoundSourceListener;

/**
 *
 * @author wilson
 */
public class SoundScene extends Scene implements SoundSourceListener{

    final static double scale = 0.0005;
    final static double decay = 0.6;
    final static double offset = 0.0;
    
    int max;
    @Override
    public void drawFrame(Image img, float delta) {
        
        for (int x=0;x<max;x++) {
            
            
            
            double val = fft.getAvg(x)*scale;
            
            double pixelH = 1f/Image.HEIGHT;
            for (int y=Image.HEIGHT-1;y>=0;y--) {
                if (val>pixelH)
                    img.data[y][x]=1;
                else if (val<=0) {
                    img.data[y][x]=0;
                } else {
                    img.data[y][x]=(float) (val / pixelH);
                }
                val-=pixelH;
            }
        }
        
        
    }

    @Override
    public String getName() {
        return "Sound";
    }

    @Override
    public void dataArrived(byte[] data) {
        float[] realData = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            realData[i] = data[i];
        }

        fft.forward(realData);
    }
    
    protected double inv(double y) {
        return Math.log(y);
    }
    protected double f(double x) {
        return Math.exp(x);
    }
    
    FFT fft;
    public SoundScene(AudioSource source) {
        fft = new FFT(source.getBufferSize(), source.getSamplesPerSecond());
        source.addSoundSourceListener(this);
         fft.logAverages(40, 7);
         System.out.println("Bands: "+fft.avgSize());
        max=Image.WIDTH>fft.avgSize()?fft.avgSize():Image.WIDTH;
    }


    
}
