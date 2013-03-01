package display.editor;

import display.editor.json.JsonProvider;
import display.SceneManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import javax.activation.MimetypesFileTypeMap;

/**
 * Provides all Web Interface capabilities to edit Scenes.
 */
public class WebEditor implements WebRequestHandler {

    SceneManager manager;
    WebServer server;
    JsonProvider json;
    MimetypesFileTypeMap mime = new MimetypesFileTypeMap();

    public WebEditor(SceneManager manager) {
        this.manager = manager;
        json = new JsonProvider(manager);
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

    /**
     * Get the path to a file inside the classpath.
     * Defeat tries to change to an upper directoy level by returning null if 
     * detected.
     * @param path Path to convert
     * @return Classpath location to path
     */
    protected String getClassPath(String path) {
        if (path.contains("..")) {
            return null;
        }
        return "/display/editor/webPages" + path;
    }

    /**
     * Try loading from src directory and not from build directory, therefore we
     * do not have to restart the program to copy from src to build. This is
     * useful for development
     */
    final static boolean tryLoadFromSrc = true;
    
    /**
     * Load a file from either classpath or from src directory if tryLoadFromSrc
     * is set
     * @param filename the file to load
     * @return An InputStream containing the file
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException 
     */
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

    /**
     * Send a static file to the client
     * @param path The path to the file to send
     * @param output Stream to output into
     * @param printBody true if content is needed
     * @throws IOException 
     */
    protected void readFile(String path, DataOutputStream output,
            boolean printBody) throws IOException {
        try {

            //Try to get File
            String p = getClassPath(path);
            InputStream requestedfile = loadFile(p);

            //Does the file exist?
            if (requestedfile != null) {
                //Get mime type
                String file_type = mime.getContentType(new File(p.toLowerCase()));
                System.out.println("file served, mime: " + file_type);
                //Write header
                output.writeBytes(WebServer.httpHeader(200, file_type));
                //Copy file to output if needed
                if (printBody) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = requestedfile.read(buf)) > 0) {
                        output.write(buf, 0, len);
                    }
                }
                requestedfile.close();
            } else {
                //Send not found message
                output.writeBytes(WebServer.httpHeader(404));
                output.writeBytes("not found");
                System.out.println("file not found");
            }
        } catch (Exception e) {
                e.printStackTrace();
                //something went wrong, send 500
                output.writeBytes(WebServer.httpHeader(500));
                output.writeBytes("error");
        }
    }

    
    /*
     * This is called by the web server for every request.
     */
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
        //Dynamic call?
        } else if (path.startsWith("/json/")) {
            json.call(path,output,printBody);
        //Else its a static file
        } else {
            readFile(path, output, printBody);
        }

    }
}
