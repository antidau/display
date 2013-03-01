package display.scene;

import display.draw.Image;
import display.scene.text.Font;
import display.scene.text.FontVarWidth;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Display text.
 */
public class TextScene extends Scene {

    @Override
    public String getName() {
        return "Text";
    }
    
    private String content = "Hällo World!";
    private float scrollSpeed = 0;
    private Character fallback='?';
    private boolean loop = true;
    private float position=0;
    
    
    Font font = new FontVarWidth();
    
    
    @Override
    public void drawFrame(Image img, float delta) {
        
        position+=delta*getScrollSpeed();
        int relposition = getPosition();
        int currentChar =0;
        while (relposition<Image.WIDTH && currentChar<getContent().length()) {
            char c = getContent().charAt(currentChar);
            int[] data = font.getCharacter(c, getFallback());
            
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
        if (relposition<0 && isLoop()) setPosition(64);
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the scrollSpeed
     */
    public float getScrollSpeed() {
        return scrollSpeed;
    }

    /**
     * @param scrollSpeed the scrollSpeed to set
     */
    public void setScrollSpeed(float scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
        setPosition(Math.round(position)); //Round position
    }

    /**
     * @return the fallback
     */
    public Character getFallback() {
        return fallback;
    }

    /**
     * @param fallback the fallback to set
     */
    public void setFallback(Character fallback) {
        this.fallback = fallback;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return Math.round(position);
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the loop
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * @param loop the loop to set
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    
}
