/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import java.util.ArrayList;
import java.util.List;

/**
 * A complete list of all MethodCollections.
 * Every MethodCollection added here will be able to be called from HTTP.
 */
public class CollectedMethods {
    protected static CollectedMethods instance = null;
    protected static CollectedMethods getInstance() {
        if (instance==null)
            instance = new CollectedMethods();
        return instance;
    }
    public static void addMethods(JsonProvider p) {
        getInstance().addMethodsInst(p);
    }
    protected void addMethodsInst(JsonProvider p) {
        for (MethodCollection c : collection) {
            c.addMethods(p);
        }
    }
    
    List<MethodCollection> collection = new ArrayList<MethodCollection>();
    public CollectedMethods() {
        collection.add(new JsonScene());
        collection.add(new JsonSceneList());
        
        //Add new Collections here!
    }
}
