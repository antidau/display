package display.editor;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Handle a HTTP request sent by some client
 */
public interface WebRequestHandler {
    /**
     * Handle a HTTP request sent by some client.
     * @param path The path requested by the client
     * @param output Stream to output into
     * @param printBody true for GET requests, false for HEAD requests
     *      if false, only the header is needed.
     * @throws IOException 
     */
    public void handleRequest(String path, DataOutputStream output, boolean printBody) throws IOException;
    
}
