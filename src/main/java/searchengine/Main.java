package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(final String... args) throws IOException {

        /* 
        String filename = Files.readString(Paths.get("config.txt")).strip();
        WebServer webserver = new WebServer(filename);
        webserver.runServer();

        */

        WebMapper systemUnderTest_small = new WebMapper("data/enwiki-medium.txt");
        systemUnderTest_small.getWebMap().keySet().forEach(x -> System.out.println(x));

    }
}
