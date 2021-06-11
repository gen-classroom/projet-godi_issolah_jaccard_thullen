package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.modules.FileWatcher;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import picocli.CommandLine;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "serve")
public class SubCommandServe implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "PROJECT_FOLDER", description = "The root folder of the project to be served", defaultValue = ".") String projectFolder;
    @CommandLine.Option(names = "--watch", usageHelp = true, description = "Auto-rebuild when files are changed") boolean autoRebuild;

    int defaultPort = 8080;
    String defaultHostname = "localhost";

    private HttpServer server;

    @Override
    public Integer call() throws Exception {
        try
        {
            server = HttpServer.create(new InetSocketAddress(defaultHostname, defaultPort), 0);
        }
        catch(IOException e)
        {
            System.out.println("Error: Could not create an HTTP server\n");
            return 1;
        }

        String path = projectFolder.contains("/build") ? projectFolder.substring(0, projectFolder.indexOf("/build")) : projectFolder; //Watch parent folder of "build" subfolder if exists
        server.createContext("/", new StatiqueHandler(projectFolder));
        server.setExecutor(null);
        server.start();
        FileWatcher fw;
        if(autoRebuild)
        {
            fw = new FileWatcher(path);
            fw.start();
            System.out.println("Watchdog initialized for path " + path + "\n");
        }
        System.out.println("Server was launched: " + defaultHostname + ":" + defaultPort + "\n Root folder: " + projectFolder + "\n Press Enter to terminate...");
        //Wait for user to press enter
        Scanner userInput = new Scanner(System.in);
        userInput.nextLine();
        //Exit the program
        server.stop(0);
        System.out.println("Server was shutdown\n");

        return 0;
    }

    static class StatiqueHandler implements HttpHandler
    {
        private String rootFolder;

        public StatiqueHandler(String rootFolder) {
            this.rootFolder = rootFolder;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if(!exchange.getRequestMethod().equals("GET"))
            {
                exchange.sendResponseHeaders(501, -1);
                exchange.close();
                return;
            }
            URI uri = exchange.getRequestURI();
            try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(rootFolder + uri.getPath())))
            {
                //Detect and transmit content type and encoding ?
                byte[] buffer = new byte[512];
                BufferedOutputStream bos = new BufferedOutputStream(exchange.getResponseBody());
                int bytesRead, nbBytes = 0;
                while((bytesRead = bis.read(buffer)) > 0)
                {
                    bos.write(buffer, 0, bytesRead);
                    nbBytes += bytesRead;
                }
                exchange.sendResponseHeaders(200, nbBytes);
                bos.flush();
                bos.close();
                exchange.close();
                return;
            }
            catch(FileNotFoundException e)
            {
                byte[] errorMsg = "The requested file could not be found".getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(404, errorMsg.length);
                exchange.getResponseBody().write(errorMsg);
                exchange.close();
                return;
            }
        }
    }
}
