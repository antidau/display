/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import display.scene.Scene;
import display.scene.SoundSceneStyle;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    
    private PropertyDescriptor d;
    private Scene scene;
    
    public SceneProperty(PropertyDescriptor d, Scene scene) {
        this.d=d;
        this.scene = scene;
        
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

    boolean setValue(String val) {
        try {
            Method write = d.getWriteMethod();
            //This would certainly be nicer with subclasses for each type...
            if(type.equals("String")) {
                write.invoke(scene,val);
            } else if (type.equals("Character")) {
                write.invoke(scene,new Character(val.charAt(0)));
            } else if (type.equals("int")) {
                write.invoke(scene,Integer.parseInt(val));
            } else if (type.equals("float")) {
                write.invoke(scene,Float.parseFloat(val));
            } else if (type.equals("boolean")) {
                write.invoke(scene,val.equals("true"));
            } else if (type.equals("SoundSceneStyle")) {
                write.invoke(scene,SoundSceneStyle.valueOf(val));
            } else return false;
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SceneProperty.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
