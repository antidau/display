/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.scene.Scene;
import display.scene.SceneFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author wilson
 */
public class SceneManager {
    ArrayList<Scene> scenes = new ArrayList<Scene>();
    SceneFactory sceneFactory = new SceneFactory();
    public int getSceneCount() {
        return scenes.size();
    }
    public Scene getScene(int i) {
        return scenes.get(i);
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
        return (List<Scene>)scenes.clone();
    }
    public SceneFactory getSceneFactory() {
        return sceneFactory;
    }
}
