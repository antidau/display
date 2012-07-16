/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor.json;

import display.SceneManager;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Jakob Wenzel
 */
public interface JsonMethod {
    public String getName();
    public void call(Map<String,String> params,DataOutputStream output,SceneManager manager) throws IOException;
}
