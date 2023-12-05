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
    /**
 * The WebMapper class maps web pages to their corresponding URLs.
 * 
 */
public class WebMapper {
    private HashMap<String, Set<String>> urlMap;
    private HashMap<String, Page> pageMap;


   /**
     * Constructor for WebMapper.
     * Initializes the webMap using the path specified in the  config.txt file in the root-directory.
     *
     */   
    public WebMapper() {
        try {
            String fileName = Files.readString(Paths.get("config.txt")).strip();
            urlMap = new HashMap<String, Set<String>>();
            pageMap = new HashMap<String, Page>(); 

            List<Page> pageList = getPages(fileName);
            for(Page page: pageList){
                String url = page.getUrl();
                for(String word: page.getWebSiteWords()){
                    if(urlMap.containsKey(word)){
                        Set<String> urlSet = urlMap.get(word);
                        urlSet.add(url);
                        urlMap.put(word, urlSet);
                    }
                    else{
                        urlMap.put(word, new HashSet<String>() {{ add(url); }});
                    }
                }
                pageMap.put(url, page);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please check config.txt for any errors");
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


    
    /** 
     * A getter-method which retrieves a Set of URL's matching a search-term.
     * 
     * @param word
     * @return A Set<String> is returned with the URL-strings for websites containing the word parameter.
     */
    public Set<String> getUrl(String word){
        if(!urlMap.containsKey(word)){
            return new HashSet<String>();
        }
       return urlMap.get(word);
    }



    
    /** 
     * A getter-method which retrieves a Page-object matching a URL.
     * 
     * @param url
     * @return A Page-object is returned matching the provided URL
     */
    public Page getPage(String url){
        return pageMap.get(url);
    }

}
