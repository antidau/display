/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import display.scene.Scene;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilson
 */
public class SceneProperty {
    public String name;
    public String type;
    public String content;
    public boolean writable;
    
    public SceneProperty(PropertyDescriptor d, Scene scene) {
        name = d.getName();
        type = d.getPropertyType().getSimpleName();
        writable= d.getWriteMethod()!=null;
        try {
            content = d.getReadMethod().invoke(scene).toString();
        } catch (Exception ex) {
            Logger.getLogger(SceneProperty.class.getName()).log(Level.SEVERE, null, ex);
            content = null;
        }
    }
}
