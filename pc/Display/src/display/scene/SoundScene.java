package display.scene;

import ddf.minim.analysis.FFT;
import display.draw.Image;
import display.scene.sound.AudioSource;
import display.scene.sound.SoundSourceListener;


/**
 * Draw a spectrum of sound incoming via a AudioSource.
 */
public class SoundScene extends Scene implements SoundSourceListener {
    //TODO: Dynamically adjust scale
    //TODO: Share FFT between multiple SoundScenes so no unneccessary FFTs are performed
    final static double scale = 0.0005;
    final static double decay = 0.6;
    
    SoundSceneStyle style = SoundSceneStyle.BAR;
    
    int maxX; // Maximum x coordinate, limited by fft's available bands and display width
    @Override
    public void drawFrame(Image img, float delta) {
        
        for (int x=0;x<maxX;x++) {
            
            
            
            double val = fft.getAvg(x+8)*scale;
            
            if (style==SoundSceneStyle.BAR) {
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
            } else if (style==SoundSceneStyle.COLOR) {
                //Clamp to (0,1)
                val = Math.max(0, Math.min(1, val));
                for (int y=0;y<Image.HEIGHT;y++) {
                    img.data[y][x]=(float) val;
                }
                
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
    
    FFT fft;
    public SoundScene(AudioSource source) {
        fft = new FFT(source.getBufferSize(), source.getSamplesPerSecond());
        source.addSoundSourceListener(this);
        fft.logAverages(40, 8);
        
        maxX=Image.WIDTH>fft.avgSize()?fft.avgSize():Image.WIDTH;
    }
    public SoundScene(AudioSource source, SoundSceneStyle style) {
        this(source);
        this.style = style;
    }

    
}
