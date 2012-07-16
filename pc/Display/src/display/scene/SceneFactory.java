/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.scene;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jakob Wenzel
 */
public class SceneFactory {
    Map<String,Class<? extends Scene>> sceneTypes = new HashMap<String,Class<? extends Scene>>();
    public final void addSceneType(String name, Class<? extends Scene> type) {
        sceneTypes.put(name,type);
    }
    public SceneFactory() {
        addSceneType("Sine",SineScene.class);
        addSceneType("Test",TestScene.class);
    }
    
    public Set<String> getSceneTypes() {
        return sceneTypes.keySet();
    }
    
    public Scene makeScene(String name) {
        Class type = sceneTypes.get(name);
        if (type==null) return null;
        try {
            return (Scene)type.newInstance();
        } catch (InstantiationException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        }
    }
}
