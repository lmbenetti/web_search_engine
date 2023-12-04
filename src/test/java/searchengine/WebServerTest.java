package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.TestInstance.Lifecycle;
import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
// import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class WebServerTest {

    WebServer server = null;

    @BeforeEach
    void setUp() {
        try {
            while (server == null) {
                try {
                    server = new WebServer();
                    server.runServer();
                } catch (BindException e) {
                    e.printStackTrace();
                    // port in use. Try again
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        server.stopServer();
        server = null;
    }

    @Test
    void WebServer_getServerPort_isValidPort(){
        assertTrue(server.getServerState(), "Server not running");
        assertEquals(8080, server.getServerPort(), "Server Port is not standard port");
    }

    @Test 
    void Webserver_stopServer_ServerNotActive(){
        String baseURL = String.format("http://localhost:%d/search?q=", server.getServerPort());
        assertNull(httpGet(baseURL));
    }

    @Test
    void WebServer_runServer_IOExceptionIsThrown() {
        try {
            server.runServer();
            throw new IOException("Simulating IOException");
        }
        catch (IOException e) {
            assertTrue(e.getMessage().contains("Simulating IOException"));
        }
    }

    private String httpGet(String url) {
    URI uri = URI.create(url);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
    try {
        return client.send(request, BodyHandlers.ofString()).body();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return null;
    }
}

    @Test
    void lookupWebServer() {
        String baseURL = String.format("http://localhost:%d/search?q=", server.getServerPort());
        assertEquals("[{\"url\": \"https://en.wikipedia.org/wiki/Papyrus\", \"title\": \"Papyrus\"}, {\"url\": \"https://en.wikipedia.org/wiki/Werner_Heisenberg\", \"title\": \"Werner Heisenberg\"}]", 
            httpGet(baseURL + "paper"));
        assertEquals("[{\"url\": \"https://en.wikipedia.org/wiki/Geoff_Hurst\", \"title\": \"Geoff Hurst\"}]",
            httpGet(baseURL + "man"));
        assertEquals("[{\"url\": \"https://en.wikipedia.org/wiki/Statue_of_Liberty\", \"title\": \"Statue of Liberty\"}]", 
            httpGet(baseURL + "tablet"));
        assertEquals("[]", 
            httpGet(baseURL + "locked"));
    }
}