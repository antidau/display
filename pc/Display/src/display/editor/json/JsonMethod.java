package display.editor.json;

import display.SceneManager;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * A method which can be called by HTTP
 * @author Jakob Wenzel
 */
public interface JsonMethod {
    /**
     * The name under which it can be called
     * @return 
     */
    public String getName();
    /**
     * Call the method from HTTP
     * @param params The URL's querystring decoded to a Map
     * @param output Stream to Output into
     * @param manager
     * @throws IOException 
     */
    public void call(Map<String,String> params,DataOutputStream output,SceneManager manager) throws IOException;
}
