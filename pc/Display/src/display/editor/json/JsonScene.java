package display.editor.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import display.SceneManager;
import display.editor.WebServer;
import display.scene.Scene;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Json Methods which are related to a specific scene.
 */
public class JsonScene extends MethodCollection {

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

                        //mapper.writeValue(output,mapper.generateJsonSchema(scene.getClass()));
                        // mapper.writeValue(output, scene);
                    }
                }
                if (result == false) {
                    output.writeBytes("false");
                }
            }
        });
    }
}
