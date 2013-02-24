/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import display.draw.Image;
import display.scene.text.Font;
import display.scene.text.FontVarWidth;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author wilson
 */
public class TextScene extends Scene {

    @Override
    public String getName() {
        return "Text";
    }
    
    String content = "Hällo World!";
    float scrollSpeed = Float.POSITIVE_INFINITY;
    Character fallback='?';
    int position=0;
    boolean loop = true;
    
    
    Font font = new FontVarWidth();
    float deltasum=0;
    
    
    @Override
    public void drawFrame(Image img, float delta) {
        
        deltasum+=delta;
        
        while (deltasum>scrollSpeed) { position--; deltasum-=scrollSpeed; }
        int relposition = position;
        int currentChar =0;
        while (relposition<Image.WIDTH && currentChar<content.length()) {
            char c = content.charAt(currentChar);
            int[] data = font.getCharacter(c,fallback);
            
            if (data!=null) {
                
                for (int x =0;x<data.length;x++) {
                    if (relposition+x>=Image.WIDTH) break;
                    if (relposition+x<0) continue;
                    for (int y=0;y<8;y++) {
                        //Get right bit for current pixel
                        int p=(data[x] >> y) & 1;
                        img.data[y][x+relposition]=p;
                    }
                    
                }
                //Clear space column
                relposition+=data.length+1;
                img.fillRect(relposition-1, 0, relposition, Image.HEIGHT, 0);
                
            }
            currentChar++; 
            
        }
        //Fill Space right of text
        img.fillRect(relposition, 0, Image.WIDTH, Image.HEIGHT, 0);
        
        //Check if whole text is outside on the left
        if (relposition<0 && loop) position=64;
    }
    
}
