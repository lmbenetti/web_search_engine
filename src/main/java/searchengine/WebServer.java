package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class WebServer {
  private static final int BACKLOG = 0;
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private static int PORT = 8080;
  private HttpServer server;
  private QueryHandler queryHandler;

  public WebServer(String filename) throws IOException {      
    queryHandler = new QueryHandler(filename);
  }
  
  private void printServerMessage() {
    String msg = " WebServer running on http://localhost:" + PORT + " ";
    System.out.println("╭"+"─".repeat(msg.length())+"╮");
    System.out.println("│"+msg+"│");
    System.out.println("╰"+"─".repeat(msg.length())+"╯");
  }
  
  private void createServerContext() throws IOException {
    server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
    server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> generateSearchResults(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
    server.start();
      }
      
      
  private void generateSearchResults(HttpExchange io) {
    String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
    ArrayList <String> response = new ArrayList<>();
    for (Page page : queryHandler.getMatchingWebPages(searchTerm)) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
        page.getUrl(), page.getTitle()));
    }
    byte[] bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  

  private byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    }
    catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
        .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    }
    catch (Exception e) {
    }
    finally {
      io.close();
    }
  }

  public void runServer() {
    try {
      createServerContext();
      printServerMessage();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stopServer() {
    server.stop(0);
  }
}