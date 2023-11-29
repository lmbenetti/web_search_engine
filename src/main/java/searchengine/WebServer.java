package searchengine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * The WebServer class is responsible for creating, running and stopping a local webserver.
 */
public class WebServer {
  private static final int BACKLOG = 0;
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  private static int PORT = 8080;
  private HttpServer server;
  private QueryHandlerTemp queryHandler;

  /**
   * Constructor for WebServer.
   * It creates an instance of the QueryHandler class.
   *
   * @throws IOException if there is an IO error when initializing the QueryHandler class.
   */
  public WebServer() throws IOException {
    queryHandler = new QueryHandlerTemp();
  }

  /**
   * Prints message to the console when the server has started successfully.
   */
  private void printServerMessage() {
    String msg = " WebServer running on http://localhost:" + PORT + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");
  }

  /**
   * Creates a server and the context for handling HTTP requests.
   *
   * @throws IOException if there is an IO error when the server is being created.
   */
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

  /**
   * Generates the search results in JSON for the searchTerm.
   *
   * @param io The HttpExchange object with the request.
   * @throws UnsupportedEncodingException if the encoding specified in CHARSET is not supported.
   */
  private void generateSearchResults(HttpExchange io) throws UnsupportedEncodingException {
    String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
    ArrayList<String> response = new ArrayList<>();
    for (Page page : queryHandler.processQuery(searchTerm)) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
          page.getUrl(), page.getTitle()));
    }
    byte[] bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  /**
   * Reads the file specified and returns it as an array of bytes.
   *
   * @param filename The name of the file that should be read.
   * @return an array of bytes containing the file content.
   */
  private byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  /**
   * Sends the response to the client.
   *
   * @param io The HttpExchange object.
   * @param code The code of the HTTP status.
   * @param mime The mimetype of the response.
   * @param response The response content as an array of bytes.
   */
  private void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }

  /**
   * Starts server and prints the start-up message.
   */
  public void runServer() {
    try {
      createServerContext();
      printServerMessage();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Stops server.
   */
  public void stopServer() {
    server.stop(0);
  }

  /**
   * Retrieves the number of the port on which the server is running.
   *
   * @return The number of the port.
   */
  public int getServerPort() {
    return server.getAddress().getPort();
  }
}