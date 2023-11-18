package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        var filename = Files.readString(Paths.get("config.txt")).strip();
        WebServer webserver = new WebServer(PORT, filename);
        webserver.createServerContext();
        webserver.printServerMessage(PORT);
    }
}
