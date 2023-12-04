package searchengine;

import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The WebMapper class maps web pages to their corresponding URLs.
 * 
 */
public class WebMapper {
    private HashMap<String, HashSet<Page>> webMap;
    private String fileName;

    /**
     * Constructor for WebMapper.
     * Initializes the webMap using the provided filename.
     *
     * @param filename The name of the file which contains the data.
     */   
    public WebMapper() {
        try {
            fileName = Files.readString(Paths.get("config.txt")).strip();
            webMap = new HashMap<>();
            makeWebMap(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please check config.txt for any errors");
        }

    }

    /**
     * Creates an inverted index and maps the words to their corresponding pages.
     *
    //  * @throws IOException if there is an IO error when reading the file.
    //  */
    // public void createInvertedIndex() throws IOException {
    //     webMap = makeWebMap(fileName);
    // }

    /**
     * Private method to create a web map using the given filename.
     * It maps each word of that file to a list of pages containing that word.
     *
     * @param filename The name of the file which contains the data.
     * @return A HashMap where each key is a word and each value is a list of pages which contain that word.
     */
    private void updateWebMap(Page page){
        for(String word : page.getWebSiteWords()){
            webMap.computeIfAbsent(word, i -> new HashSet<>()).add(page);
        }
    }

    /**
     * Reads the file which is referenced by the filename and creates a list of Page objects from it.
     *
     * @param filename The name of the file which contains the data.
     * @return A list of Page objects created from the given file.
     */
    private void makeWebMap(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<String> webPage = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("*PAGE")) {
                    if (webPage.size() > 2) {
                        Page page = new Page(webPage);
                        updateWebMap(page);
                    }
                    webPage.clear();
                }
                webPage.add(line);
            }
            if (webPage.size() > 2) {
                Page page = new Page(webPage);
                updateWebMap(page);
            }
            reader.close();
        }
        
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the HashMap of the instance.
     *
     * @return A HashMap where each key is a word and each value is a list of pages containing that word.
     */
    public HashMap<String, HashSet<Page>> getWebMap() {
        return webMap;
    }

    /**
     * Retrieves a Set<Page>-object relating to the passed argument query.
     * 
     * 
     * @param query
     * @return Returns a clone of a Set intance containing Page-instances relating to the query-word through the HashMap field of the class. If no Set is matched, an empty set is retrieved.
     */
    public Set<Page> getPageSet(String query){

        if(!webMap.containsKey(query)){
            return new HashSet<Page>();
        }
        return webMap.get(query).stream().collect(Collectors.toSet()); 
    }
}
