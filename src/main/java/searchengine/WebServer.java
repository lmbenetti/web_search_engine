package searchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class WebServer {
  static final int PORT = 8080;
  static final int BACKLOG = 0;
  static final Charset CHARSET = StandardCharsets.UTF_8;
  HttpServer server;
  QueryHandler queryHandler;
  List<List<String>> pages;

  WebServer(int port, String filename) throws IOException {      
    pages = new ArrayList<>();
    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    getPages(filename);
    createServerContext();
    server.start();
    printServerMessage(port);
  }

  public void printServerMessage(int port) {
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭"+"─".repeat(msg.length())+"╮");
    System.out.println("│"+msg+"│");
    System.out.println("╰"+"─".repeat(msg.length())+"╯");
  }

  public void createServerContext() {
    server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> search(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
  }

  public void getPages(String filename) throws IOException {
    try {
      List<String> lines = Files.readAllLines(Paths.get(filename));
      var lastIndex = lines.size();
      for (var i = lines.size() - 1; i >= 0; --i) {
        if (lines.get(i).startsWith("*PAGE")) {
          pages.add(lines.subList(i, lastIndex));
          lastIndex = i;
        }
      }
      Collections.reverse(pages);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  void search(HttpExchange io) {
    var searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
    var response = new ArrayList<String>();
    for (var page : search(searchTerm)) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
        page.get(0).substring(6), page.get(1)));
    }
    var bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  List<List<String>> search(String searchTerm) {
    var result = new ArrayList<List<String>>();
    for (var page : pages) {
      if (page.contains(searchTerm)) {
        result.add(page);
      }
    }
    return result;
  }

  byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  void respond(HttpExchange io, int code, String mime, byte[] response) {
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

  public static void main(final String... args) throws IOException {
    var filename = Files.readString(Paths.get("config.txt")).strip();
    new WebServer(PORT, filename);
  }
}