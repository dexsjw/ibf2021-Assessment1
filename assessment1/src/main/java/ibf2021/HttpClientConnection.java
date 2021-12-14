package ibf2021;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HttpClientConnection implements Runnable {

    private final Socket socket;
    //private int id;
    private List<String> directories;

    public HttpClientConnection(Socket socket, List<String> directories) {
        this.socket = socket;
        //this.id = id;
        this.directories = directories;
    }

    @Override
    public void run() {
        
        BufferedReader in = null;
        HttpWriter out = null;
        String clientLine = "";
        //System.out.println("Connection ID: " + id);

        try {

            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            in = new BufferedReader(new InputStreamReader(bis));
            out = new HttpWriter(socket.getOutputStream());
/* 
            out.write("Assigned Client ID: " + id);
            out.newLine();
            out.flush();
 */
            clientLine = in.readLine();

            //Action 1
            if (!clientLine.contains("GET")) {
                Scanner scan = new Scanner(clientLine);
                String method = scan.next();
                String Response1 = "HTTP/1.1 405 Method Not Allowed";
                String Response2 = method + "not supported";
                out.writeString(Response1);
                out.writeString();
                out.writeString(Response2);
                scan.close();
                out.close();
                socket.close();

            //Action 2
            } else {
                String[] request = clientLine.split(" ");
                String resource = request[1];
                if ("/".equals(resource)) {
                    resource = "/index.html";
                }
                for (String dir: directories) {
                    Path resourcePath = Paths.get(dir + resource.trim());
                    if (!Files.exists(resourcePath)) {
                        String Response1 = "HTTP/1.1 404 Not Found";
                        String Response2 = resource + "not found";
                        out.writeString(Response1);
                        out.writeString();
                        out.writeString(Response2);
                        out.close();
                        socket.close();
                    
            //Action 4        
                    } else {
                        if (resource.contains(".png")) {
                            byte[] buffer = Files.readAllBytes(resourcePath);
                            String Response1 = "HTTP/1.1 200 OK";
                            String Response2 = "Content-Type: image/png";
                            out.writeString(Response1);
                            out.writeString(Response2);
                            out.writeString();
                            out.writeBytes(buffer);
                            out.close();
                            socket.close();

            //Action 3
                        } else {
                            byte[] buffer = Files.readAllBytes(resourcePath);
                            String Response1 = "HTTP/1.1 200 OK";
                            out.writeString(Response1);
                            out.writeString();
                            out.writeBytes(buffer);
                            out.close();
                            socket.close();

                        }

                    }
                }

            }



        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
/*         while (!clientLine.equals("close") && clientLine != null) {

            System.out.println("Client " + id + ": " + clientLine);

            try {
                
                if (clientLine.equals("get-cookie")) {
                    Cookie cookie = new Cookie();
                    out.write("cookie-text " + cookie.getCookie(cookieFilePath));
                    out.newLine();
                    out.flush();
                
                } else {
                    out.write("Invalid request, please use \"get-cookie\" to request cookies");
                    out.newLine();
                    out.flush();
                }
                clientLine = in.readLine();
    
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        } */

    }

}
