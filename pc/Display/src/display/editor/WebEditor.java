/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package display.editor;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
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
        mime.addMimeTypes("image/x-icon ico");
        mime.addMimeTypes("application/javascript js");
        mime.addMimeTypes("text/css css");
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

    //For development, try loading from src and not build, therefore we
    //do not have to restart the program to copy from src to build
    final static boolean tryLoadFromSrc = true;
    protected InputStream loadFile(String filename) throws MalformedURLException, UnsupportedEncodingException {
        if (tryLoadFromSrc) {
            
            
            String location = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
            
            // delete last slash if not a jar file
            if (!location.endsWith("jar"))
                location = location.substring(0, location.length()-1); 
            
            //Find second last slash
            int pos = location.lastIndexOf('/');
            pos=location.lastIndexOf('/', pos-1);
            //delete everything after
            location = location.substring(0, pos+1);
            
            //Put together full path
            location = location+"src"+filename;
            //Make file
            File f = new File( URLDecoder.decode( new URL(location).getFile(), "UTF-8" ) );
            
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException ex) {
                //Return null when file not found, will be translated to HTTP 404
                return null;
            }
        } else
            //Load from classpath
            return this.getClass().getResourceAsStream(filename);
    }

    protected void readFile(String path, DataOutputStream output,
            boolean printBody) throws IOException {
        try {

            String p = escapePath(path);
            InputStream requestedfile = loadFile(p);
            String file_type = mime.getContentType(new File(p.toLowerCase()));

            if (requestedfile != null) {
                System.out.println("file served, mime: " + file_type);
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
                System.out.println("file not found");
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
