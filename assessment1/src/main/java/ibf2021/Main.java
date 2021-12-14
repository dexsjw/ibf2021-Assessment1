package ibf2021;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //System.out.println(new File(".").getCanonicalPath());// - used to determine current directory
        //C:\Users\dexte\Documents\NUS-ISS (IBF_TFIP)\Module 1\codes\gitAssessment1\ibf2021-Assessment1\assessment1 - current directory
        int port = 3000;
        List<String> directories = new ArrayList<>();
        
        if (args != null && args.length >= 1) {
           if (args.length >= 4) {
               if (args[0].contains("--port") && args[2].contains("--docRoot")) {                      //port and directories settings
                   port = Integer.parseInt(args[1]);
                   String directory = args[3];
                   String[] directoryList = directory.split(":");
                   
                   for (String dir: directoryList) {
                       directories.add(dir);
                   }
                   
                   System.out.println("port: " + port);
                   System.out.println("docRoot: " + directories);
                }

            } else if (args.length <= 2) {
                if (args[0].contains("--port")) {                                             //port settings
                    port = Integer.parseInt(args[1]);
                    System.out.println("port: " + port);

                } else if (args[0].contains("--docRoot")) {                                             //directories settings
                    String directory = args[1];
                    String[] directoryList = directory.split(":");
                    for (String dir: directoryList) {
                        directories.add(dir);
                    }
                System.out.println("docRoot: " + directories);
                } 
            }            
        } else {
            directories.add(0, "./static/");
            System.out.println("Settings were not provided. Default settings will be used.");
            System.out.printf("port: %d\ndocRoot: %s%n", port, directories);
        }

        HttpServer server = new HttpServer(port, directories);
        server.startServer();


    }
}
