/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import display.scene.Scene;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilson
 */
public class SceneProperties {
    public static SceneProperty[] getProperties(Scene scene) {
        try {
            BeanInfo b = Introspector.getBeanInfo(scene.getClass(),Scene.class);
            
            PropertyDescriptor[] descriptors = b.getPropertyDescriptors();
            
            SceneProperty[] res = new SceneProperty[descriptors.length];
            for (int i=0;i<descriptors.length;i++) {
                res[i]=new SceneProperty(descriptors[i], scene);
            }
            
            return res;
        } catch (IntrospectionException ex) {
            Logger.getLogger(SceneProperties.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
