package ibf2021;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer {

    private int port;
    private List<String> directories;
    private String directory;

    public HttpServer(int port, List<String> directories) {
        this.port = port;
        this.directories = directories;
    }

    public void checkPath() {

    }

    public void startServer() {
       
        try {
            ExecutorService threadpool = Executors.newFixedThreadPool(3);
            ServerSocket server = new ServerSocket(port);
            System.out.println("--- Server listening at port 3000 ---");

            while (true) { 

                Socket socket = server.accept();
                int id = (int) (Math.random() * 10000);
                HttpClientConnection worker = new HttpClientConnection(socket, id, cookieFilePath);
                threadpool.submit(worker);

            }

        } finally {
            server.close();
        }
    } 
}
