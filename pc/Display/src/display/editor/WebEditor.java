/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        return "/home/wilson/tlc5940/pc/Display/src/display/editor/webPages" + path;
    }

    protected void readFile(String path, DataOutputStream output,
            boolean printBody) throws IOException {
        try {

            String p = escapePath(path);
            FileInputStream requestedfile = new FileInputStream(p);
            String file_type = mime.getContentType(new File(p.toLowerCase()));

            output.writeBytes(WebServer.httpHeader(200, file_type));
            if (printBody) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = requestedfile.read(buf)) > 0) {
                    output.write(buf, 0, len);
                }
            }
            requestedfile.close();
        } catch (Exception e) {
            try {
                //if you could not open the file send a 404
                output.writeBytes(WebServer.httpHeader(404));
                output.writeBytes("not found");
                //close the stream
                output.close();
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
            System.out.println("json stuff");
        } else {
            readFile(path, output, printBody);
        }

    }
}
