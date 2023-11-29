package searchengine;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

/**
 * The Main class of the project.
 * Marks the beginning of the application.
 */
public class Main {
    /**
     * The main method starts the application.
     * 
     * @param args command-line arguments passed to the application.
     * @throws IOException if there is an IO error when reading the configuration file.
     */
    public static void main(final String... args) throws IOException {

        WebServer webserver = new WebServer();
        webserver.runServer();

 

    }
}
