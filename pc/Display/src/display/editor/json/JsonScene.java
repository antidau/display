package display.editor.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import display.SceneManager;
import display.editor.WebServer;
import display.scene.Scene;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Json Methods which are related to a specific scene.
 */
public class JsonScene extends MethodCollection {

    @Override
    public void addMethods(JsonProvider provider) {
        final ObjectMapper mapper = new ObjectMapper();


        //Get a list of available scene types
        provider.addMethod(new JsonMethod() {

            @Override
            public String getName() {
                return "scene/properties";
            }

            @Override
            public void call(Map<String, String> params, DataOutputStream output, SceneManager manager) throws IOException {
                output.writeBytes(WebServer.httpHeader(200, "application/json"));
                boolean result = false;
                String paramStr = params.get("id");
                if (paramStr != null) {
                    int id = new Integer(paramStr);
                    Scene scene = manager.getScene(id);
                    if (scene != null) {
                        mapper.writeValue(output, SceneProperties.getProperties(scene));
                        result=true;
                    }
                }
                if (result == false) {
                    output.writeBytes("false");
                }
            }
        });
        
        provider.addMethod(new JsonMethod() {

            @Override
            public String getName() {
                return "scene/saveProps";
            }

            @Override
            public void call(Map<String, String> params, DataOutputStream output, SceneManager manager) throws IOException {
                output.writeBytes(WebServer.httpHeader(200, "application/json"));
                boolean result = false;
                String paramStr = params.get("id");
                if (paramStr != null) {
                    int id = new Integer(paramStr);
                    Scene scene = manager.getScene(id);
                    if (scene != null) {
                        result=true;
                        SceneProperty[] prop = SceneProperties.getProperties(scene);
                        for (SceneProperty p : prop) {
                            if (p.writable) {
                                String val = params.get("prop-"+p.name);
                                if (val!=null) {
                                    boolean valRes = p.setValue(val);
                                    result=result&&valRes;
                                }
                            }
                        }
                    }
                }
                output.writeBytes(Boolean.toString(result));
            }
        });
    }
}
