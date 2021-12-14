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
        List<Path> directoryPathList = new ArrayList<>();
        directoryPathList = getPath();

        try {
            ExecutorService threadpool = Executors.newFixedThreadPool(3);
            ServerSocket server = new ServerSocket(port);
            System.out.printf("--- Server listening at port %d ---%n", port);
    
                while (true) { 
    
                    Socket socket = server.accept();
                    //int id = (int) (Math.random() * 10000);
                    HttpClientConnection worker = new HttpClientConnection(socket, directoryPathList);
                    threadpool.submit(worker);
    
            }
    
        } catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(1);
        }
    }

    public List<Path> getPath() {

        List<Path> directoryPathList = new ArrayList<>();
        for (String dir: directories) {
            Path dirPath = Paths.get(dir);
            directoryPathList.add(dirPath);
        }
        return directoryPathList;

    }

    public void checkPath() {
        
        List<Path> directoryPathList = new ArrayList<>();
        directoryPathList = getPath();

        for (Path dpl: directoryPathList) {
            
            if (Files.exists(dpl) && Files.isDirectory(dpl) && Files.isReadable(dpl)) {
                System.out.println("All directories provided are valid.");
            } else {
                if (!Files.exists(dpl)) {
                    System.out.println("Path does not exist.");
                    System.exit(1);
                } else if (!Files.isDirectory(dpl)) {
                    System.out.println("Path provided is not a directory.");
                    System.exit(1);
                } else if (!Files.isReadable(dpl)) {
                    System.out.println("Path is not readable by server.");
                    System.exit(1);
                } else {
                    System.out.println("Invalid docRoot Directories provided.");
                    System.exit(1);
                }
            }

        }
        
    }
    
}
