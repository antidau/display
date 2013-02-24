/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import display.draw.Image;
import display.scene.text.Font;

/**
 *
 * @author wilson
 */
public class TextScene extends Scene {

    @Override
    public String getName() {
        return "Text";
    }
    
    String content = "Hello World";
    Font font = new Font();
    Character fallback='?';
    int position=0;
    float deltasum=0;
    
    
    @Override
    public void drawFrame(Image img, float delta) {
        deltasum+=delta;
        
        if (deltasum>1f) { position--; deltasum-=1f; System.out.println("hello");}
        int relposition = position;
        int currentChar =0;
        while (relposition<Image.WIDTH && currentChar<content.length()) {
            char c = content.charAt(currentChar);
            int[] data = font.getCharacter(c,fallback);
            
            if (data!=null) {
                
                for (int x =0;x<6;x++) {
                    if (relposition+x>=Image.WIDTH) break;
                    if (relposition+x<0) continue;
                    for (int y=0;y<8;y++) {
                        int p;
                        //Last column is always empty
                        if (x==5) p=0; 
                        //Get right bit for current pixel
                        else p=(data[x] >> y) & 1;
                        img.data[y][x+relposition]=p;
                    }
                    
                }
                //Clear space column
                relposition+=6;
                
            }
            currentChar++;
            
        }
        //Fill Space right of text
        img.fillRect(relposition, 0, Image.WIDTH, Image.HEIGHT, 0);
    }
    
}
