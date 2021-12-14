package ibf2021;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.util.List;

public class HttpClientConnection implements Runnable {

    private final Socket socket;
    //private int id;
    private List<Path> directory;

    public HttpClientConnection(Socket socket, List<Path> directoryPathList) {
        this.socket = socket;
        //this.id = id;
        this.directory = directoryPathList;
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
                
            }

            //Action 2


            //Action 3


            //Action 4


        } catch (IOException ioe) {
            System.out.println(ioe);
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
