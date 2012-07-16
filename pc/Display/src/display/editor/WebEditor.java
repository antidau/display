/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author wilson
 */
public class WebEditor implements WebRequestHandler {
    
    WebServer server;
    MimetypesFileTypeMap mime = new MimetypesFileTypeMap();

    public WebEditor() {
        server = new WebServer(this);
        mime.addMimeTypes("text/html html htm");
        mime.addMimeTypes("image/png png");        
    }
    
    public void stop() {
        server.wantStop();
    }
    
    public void start() {
        server.start();
    }
    
    protected String escapePath(String path) {
        if (path.contains("..")) {
            return null;
        }
        return "/display/editor/webPages" + path;
    }
    
    protected void readFile(String path, DataOutputStream output,
            boolean printBody) throws IOException {
        try {
            
            String p = escapePath(path);
            InputStream requestedfile = this.getClass().getResourceAsStream(p);
            String file_type = mime.getContentType(new File(p.toLowerCase()));
            
            if (requestedfile != null) {
                output.writeBytes(WebServer.httpHeader(200, file_type));
                if (printBody) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = requestedfile.read(buf)) > 0) {
                        output.write(buf, 0, len);
                    }
                }
                requestedfile.close();
            } else {
                output.writeBytes(WebServer.httpHeader(404));
                output.writeBytes("not found");
            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
                //something went wrong, send 500
                output.writeBytes(WebServer.httpHeader(500));
                output.writeBytes("error");
            } catch (Exception e2) {
            }
            
        }
    }
    
    @Override
    public void handleRequest(String path, DataOutputStream output,
            boolean printBody) throws IOException {
        
        System.out.println("Client requested: " + path);

        //Redirect to index
        if (path.equals("/")) {
            output.writeBytes(WebServer.httpHeader(301, null));
            output.writeBytes("Location: /index.html\r\n");
            output.writeBytes("\r\n");
            System.out.println("redirected");
        } else if (path.startsWith("/json/")) {
            readJson(path, output, printBody);
        } else {
            readFile(path, output, printBody);
        }
        
    }
    
    private void readJson(String path, DataOutputStream output, boolean printBody) throws IOException {
        
        output.writeBytes(WebServer.httpHeader(200));
        output.writeBytes("JSON");
    }
}
