/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import display.draw.Image;
import display.scene.sound.Fourier;
import display.scene.sound.SoundFftListener;

/**
 *
 * @author wilson
 */
public class SoundScene extends Scene implements SoundFftListener{

    double[] data;
    final static double scale = 0.0001;
    @Override
    public void drawFrame(Image img, float delta) {
        if (data==null) return;
        int factor = data.length / Image.WIDTH /2;
        //Assuming that the fourier length is a multiple of the image width!!
        
        for (int x=0;x<Image.WIDTH;x++) {
            double sum =0;
            for (int a=factor*x;a<factor*(x+1);a++)
                sum+=data[a];
            double val = sum / factor * scale;
            
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

    final static double decay = 0.4;
    @Override
    public void dataArrived(byte[] raw, double[] fourier) {
        for (int i=0; i<fourier.length;i++) {
            data[i]=data[i]*decay+fourier[i]*(1-decay);
        }
    }
    
    public SoundScene(Fourier fourier) {
        data = new double[fourier.getResultSize()];
        fourier.addSoundFftListener(this);
    }

    
}
