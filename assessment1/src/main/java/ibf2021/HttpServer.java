package ibf2021;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void startServer() {
       
        checkPath();
        try {
            ExecutorService threadpool = Executors.newFixedThreadPool(3);
            ServerSocket server = new ServerSocket(port);
            System.out.println("--- Server listening at port 3000 ---");
    
    /*             while (true) { 
    
                Socket socket = server.accept();
                int id = (int) (Math.random() * 10000);
                HttpClientConnection worker = new HttpClientConnection(socket, id, cookieFilePath);
                threadpool.submit(worker);
    
            } */
    
        } catch (IOException ioe) {
            System.out.println(ioe);
        
        } finally {
            //server.close();
        }
    }

    public void checkPath() {
        
        List<Path> directoryPathList = new ArrayList<>();
        for (String dir: directories) {
            Path dirPath = Paths.get(dir);
            directoryPathList.add(dirPath);
        }

        for (Path dpl: directoryPathList) {
            
            if (Files.exists(dpl) && Files.isDirectory(dpl) && Files.isReadable(dpl)) {
                System.out.println("All directories provided are valid.");
            } else {
                if (!Files.exists(dpl)) {
                    System.out.println("Path does not exist.");
                } else if (!Files.isDirectory(dpl)) {
                    System.out.println("Path provided is not a directory.");
                } else if (!Files.isReadable(dpl)) {
                    System.out.println("Path is not readable by server.");
                } else {
                    System.out.println("Invalid docRoot Directories provided.");
                }
            }

        }
        
    }
    
    
}
