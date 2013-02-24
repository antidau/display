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
        img.fill(0);
        if (deltasum>1f) { position--; deltasum-=1f; System.out.println("hello");}
        int relposition = position;
        int currentChar =0;
        while (relposition<Image.WIDTH && currentChar<content.length()) {
            char c = content.charAt(currentChar);
            int[] data = font.getCharacter(c,fallback);
            
            if (data!=null) {
                
                for (int x =0;x<5;x++) {
                    if (relposition+x>=Image.WIDTH) break;
                    if (relposition+x<0) continue;
                    for (int y=0;y<8;y++) {
                        int p=(data[x] >> y) & 1;
                        img.data[y][x+relposition]=p;
                    }
                    
                }
                relposition+=6;
                
            }
            currentChar++;
            
        }
    }
    
}
