/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import display.SceneManager;
import display.editor.WebServer;
import display.scene.Scene;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Jakob Wenzel
 */
public class JsonSceneList {

    public static void addMethods(JsonProvider provider) {
        final ObjectMapper mapper = new ObjectMapper();
        
        
        //Get a list of available scene types
        provider.addMethod(new JsonMethod() {

            @Override
            public String getName() {
                return "listSceneTypes";
            }

            @Override
            public void call(Map<String, String> params, DataOutputStream output, SceneManager manager) throws IOException {
                output.writeBytes(WebServer.httpHeader(200, "application/json"));
                mapper.writeValue(output, manager.getSceneFactory().getSceneTypes());
            }
        });
        
        //Add a scene
        provider.addMethod(new JsonMethod() {

            @Override
            public String getName() {
                return "addScene";
            }

            @Override
            public void call(Map<String, String> params, DataOutputStream output, SceneManager manager) throws IOException {
                output.writeBytes(WebServer.httpHeader(200, "application/json"));
                Boolean result = true;
                String name = params.get("name");
                System.out.println("Trying to add scene named: "+name);
                System.out.println(params);
                Scene scene = manager.getSceneFactory().makeScene(name);
                if (scene==null)
                    result = false;
                else {
                    manager.addScene(scene);
                }
                mapper.writeValue(output, result);
                
            }
        });
        
        //Get a list of current scenes
        provider.addMethod(new JsonMethod() {

            @Override
            public String getName() {
                return "listScenes";
            }

            public void call(Map<String, String> params, DataOutputStream output, SceneManager manager) throws IOException {
                output.writeBytes(WebServer.httpHeader(200));
                output.writeBytes("hello");
            }
        });
    }
}
