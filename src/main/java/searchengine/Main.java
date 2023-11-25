package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(final String... args) throws IOException {
        String filename = Files.readString(Paths.get("config.txt")).strip();
        WebServer webserver = new WebServer(filename);
        webserver.runServer();
    }
}
