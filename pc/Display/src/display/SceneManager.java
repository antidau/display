/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.draw.Image;
import display.scene.Scene;
import display.scene.SceneFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a list of all top level scenes that currently exist.
 */
public class SceneManager {

    ArrayList<Scene> scenes = new ArrayList<Scene>();
    SceneFactory sceneFactory = new SceneFactory();
    
    int current =0;

    public int getSceneCount() {
        return scenes.size();
    }

    public Scene getScene(int i) {
        try {
            return scenes.get(i);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addScene(Scene scene) {
        return scenes.add(scene);
    }

    public boolean removeScene(Scene scene) {
        return scenes.remove(scene);
    }

    public boolean containsScene(Scene scene) {
        return scenes.contains(scene);
    }

    public List<Scene> getScenes() {
        return (List<Scene>) scenes.clone();
    }

    public SceneFactory getSceneFactory() {
        return sceneFactory;
    }
    
    
    public void drawFrame(Image img,float delta) {
        if (scenes.size()>current) {
            scenes.get(current).drawFrame(img, delta);
        } else img.fill(0);
    }

    void stop() {
        for(Scene scene : scenes)
            scene.stop();
    }
}
