package searchengine;

import java.util.HashSet;
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
public class WebMapperTemp {
    private HashMap<String, Set<String>> urlMap;
    private HashMap<String, Page> pageMap;

    private String fileName;

   /**
     * Constructor for WebMapper.
     * Initializes the webMap using the provided filename.
     *
     * @param filename The name of the file which contains the data.
     */   
    public WebMapperTemp() {
        try {
            fileName = Files.readString(Paths.get("config.txt")).strip();
            makeWebMap(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please check config.txt for any errors");
        }

    }


    /**
     * Private method to create a web map using the given filename.
     * It maps each word to a set of URL-strings in the field urlMap and maps each URL to a page in the pageMap.
     *
     * @param filename The name of the file which contains the data.
     */
    private void makeWebMap(String filename){
        List<Page> pageList = getPages(filename);
        for (Page page : pageList) {
            for(String word : page.getWebSiteWords()){

                String url = page.getUrl();

                if (urlMap.containsKey(word)){
                    Set<String> urlSet = urlMap.get(word);
                    urlSet.add(url);
                    urlMap.put(word, urlSet);
                    
                }
                else urlMap.put(word, new HashSet<String>(){{
                    add(url);
                }});
                pageMap.put(word, page);

            }
        }
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


    //getters

    public Set<String> getUrl(String word){
        if(!urlMap.containsKey(word)){
            return new HashSet<String>();
        }
       return urlMap.get(word);
    }


    public Page getPage(String url){
        return pageMap.get(url);
    }
    
}
