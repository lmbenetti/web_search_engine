package searchengine;

import java.util.HashSet;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

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
    public WebMapper()throws IOException{
        fileName = Files.readString(Paths.get("config.txt")).strip();
        webMap = makeWebMap(fileName);
    }

    /**
     * Creates an inverted index and maps the words to their corresponding pages.
     *
     * @throws IOException if there is an IO error when reading the file.
     */
    public void createInvertedIndex()throws IOException{
        webMap = makeWebMap(fileName);
    }

    /**
     * Private method to create a web map using the given filename.
     * It maps each word of that file to a list of pages containing that word.
     *
     * @param filename The name of the file which contains the data.
     * @return A HashMap where each key is a word and each value is a list of pages which contain that word.
     */
    private HashMap<String, HashSet<Page>> makeWebMap(String filename){
        HashMap<String, HashSet<Page>> mapToReturn = new HashMap<>();
        List<Page> pageList = getPages(filename);
        for (Page page : pageList) {
            for(String word : page.getWebSiteWords()){
                if (mapToReturn.containsKey(word)){
                    HashSet<Page> pageToAdd = mapToReturn.get(word);
                    pageToAdd.add(page);
                    mapToReturn.put(word, pageToAdd);
                }
                else mapToReturn.put(word, new HashSet<Page>(){{
                    add(page);
                }});
            }
        }
        return mapToReturn;
    }

    /**
     * Reads the file which is referenced by the filename and creates a list of Page objects from it.
     *
     * @param filename The name of the file which contains the data.
     * @return A list of Page objects created from the given file.
     */
    private List<Page> getPages(String filename) {
        try {
            List<Page> pageList = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filename));
            int lastIndex = lines.size();
            for (int i = lines.size() - 1; i >= 0; --i) {
                if (lines.get(i).startsWith("*PAGE")) {
                    List<String> webPage = lines.subList(i, lastIndex);
                    if (webPage.size() > 2) {
                        pageList.add(new Page(webPage));
                    }
                    lastIndex = i;
                }
            }
            return pageList;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<Page>();
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Page>();
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
}
