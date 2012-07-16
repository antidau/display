/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author wilson
 */
public interface WebRequestHandler {
    public void handleRequest(String path, DataOutputStream output, boolean printBody) throws IOException;
    
}
