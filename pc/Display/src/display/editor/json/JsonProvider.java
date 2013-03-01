package display.editor.json;

import display.SceneManager;
import display.editor.WebServer;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * 
 */
public class JsonProvider {
    SceneManager manager;
    Map<String,JsonMethod> methods = new HashMap<String,JsonMethod>();
    
    public final void addMethod(JsonMethod method) {
        methods.put(method.getName(),method);
    }
    
    public JsonProvider(SceneManager manager) {
        this.manager = manager;
        
        CollectedMethods.addMethods(this);
    }

    /**
     * Call a Json Method.
     * The actual call is only done if printBody is true, otherwise we only
     * check if the method exists.
     * @param path The path to the method to call
     * @param output Stream to output into
     * @param printBody Print body text?
     * @throws IOException 
     */
    public void call(String path, DataOutputStream output, boolean printBody) throws IOException {
        //Get method name
        int pos = path.indexOf('?');
        String methodName;
        String query=null;
        if (pos==-1) {
            methodName = path.substring(6);
        } else {
            methodName = path.substring(6,pos);
            query = path.substring(pos+1);
        }
        
        //Finding method
        JsonMethod method = methods.get(methodName);
        
        //Error if not found
        if (method==null) {
           output.writeBytes(WebServer.httpHeader(404));
           if (printBody) 
               output.writeBytes("Method "+methodName+" not found!");
           return;
        }
        
        //Do the call if we want body text
        if (printBody) {
        
            //Decode parameters
            Map<String,String> params = new HashMap<String,String>();
            if (query!=null) {
                List<NameValuePair> list = URLEncodedUtils.parse(query, null);
                for (NameValuePair item : list) {
                    params.put(item.getName(), item.getValue());
                }
            }
        
            method.call(params, output, manager);
        }
        
    }
    
}
