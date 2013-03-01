package display.editor;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create a web server, content to send is provided by a WebRequestHandler.
 * This is heavily based on some tutorial, unfortunately I forgot to write
 * down the source... Sorry :(
 */
public class WebServer extends Thread {

    final static int PORT = 10847;
    boolean stop = false;

    public void wantStop() {
        stop = true;
    }
    WebRequestHandler requestHandler;

    public WebServer(WebRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {

        ServerSocket serversocket = null;
        try {
            //print/send message to the guiwindow
            System.out.println("Trying to bind to localhost on port " + Integer.toString(PORT) + "...");
            //make a ServerSocket and bind it to given port,
            serversocket = new ServerSocket(PORT);
        } catch (Exception e) { //catch any errors and print errors to gui
            System.err.println("\nFatal Error:" + e.getMessage());
            return;
        }
        System.out.println("OK!\n");
        //go in a infinite loop, wait for connections, process request, send response
        while (!stop) {
            try {
                serversocket.setSoTimeout(500);
                //this call waits/blocks until someone connects to the port we
                //are listening to
                Socket connectionsocket = serversocket.accept();
                //figure out what ipaddress the client commes from, just for show!
                InetAddress client = connectionsocket.getInetAddress();
                //and print it to gui
                System.out.println(client.getHostName() + " connected to server.\n");
                //Read the http request from the client from the socket interface
                //into a buffer.
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
                //Prepare a outputstream from us to the client,
                //this will be used sending back our response
                //(header + requested file) to the client.
                DataOutputStream output =
                        new DataOutputStream(connectionsocket.getOutputStream());

//as the name suggest this method handles the http request, see further down.
//abstraction rules
                http_handler(input, output);
            } catch (Exception e) { //catch any errors, and print them
                if (!(e instanceof SocketTimeoutException)) {
                    System.err.println("\nError:" + e.getMessage());
                }
            }

        } //go back in loop, wait for next request
    }

    //our implementation of the hypertext transfer protocol
//its very basic and stripped down
    protected void http_handler(BufferedReader input, DataOutputStream output) {
        try {
            String http = new String(); //a bunch of strings to hold
            String path = new String(); //the various things, what http v, what path,
            String file = new String(); //what file
            String user_agent = new String(); //what user_agent
            try {
                //This is the two types of request we can handle
                //GET /index.html HTTP/1.0
                //HEAD /index.html HTTP/1.0
                String line = input.readLine(); //read from the stream
                String upper = line.toUpperCase();
                System.out.println(line);
                boolean printBody = false;
                if (upper.startsWith("GET")) { //compare it is it GET
                    printBody = true;
                } else if (upper.startsWith("HEAD")) { //same here is it HEAD
                    printBody = false;
                } else {
                    try {
                        output.writeBytes(httpHeader(501));
                        output.close();
                        return;
                    } catch (Exception e3) { //if some error happened catch it
                        System.err.println("error:" + e3.getMessage());
                    } //and display error
                }



                boolean valid = false;
                int start = line.indexOf(' ');
                int end = 0;
                if (start > -1) {
                    end = line.indexOf(' ', start + 1);
                    if (end > -1) {
                        valid = true;
                    }
                }
                if (!valid) {

                    output.writeBytes(httpHeader(501));
                    output.close();
                }
                path = line.substring(start + 1, end); //fill in the path
                requestHandler.handleRequest(path, output, printBody);

            } catch (Exception e) {
                System.err.println("error " + e.getMessage());
            } //catch any exception

            output.close();
        } catch (IOException ex) {
            Logger.getLogger(WebEditor.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public static String httpHeader(int return_code) {

        return httpHeader(return_code, "text/html");
    }
    //this method makes the HTTP header for the response
    //the headers job is to tell the browser the result of the request
    //among if it was successful or not.

    public static String httpHeader(int return_code, String file_type) {
        StringBuilder s = new StringBuilder();
        s.append("HTTP/1.0 ");

        switch (return_code) {
            case 200:
                s.append("200 OK");
                break;
            case 400:
                s.append("400 Bad Request");
                break;
            case 403:
                s.append("403 Forbidden");
                break;
            case 404:
                s.append("404 Not Found");
                break;
            case 500:
                s.append("500 Internal Server Error");
                break;
            case 501:
                s.append("501 Not Implemented");
                break;
            default:
                s.append(return_code);
        }

        s.append("\r\n"); //other header fields,
        s.append("Connection: close\r\n"); //we can't handle persistent connections
        s.append("Server: LED Display\r\n"); //server name


        if (file_type!=null) {
            s.append("Content-Type: " + file_type + "\r\n");

            s.append("\r\n"); //this marks the end of the httpheader
            //and the start of the body
        }

        return s.toString();
    }
}
