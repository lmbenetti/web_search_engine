package searchengine;

import java.util.HashSet;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class WebMapper {
    private HashMap<String, HashSet<Page>> webMap;
    private String fileName;

    public WebMapper()throws IOException{
        fileName = Files.readString(Paths.get("config.txt")).strip();
        webMap = makeWebMap(fileName);
    }

    public void createInvertedIndex()throws IOException{
        webMap = makeWebMap(fileName);
    }

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

    public HashMap<String, HashSet<Page>> getWebMap() {
        return webMap;
    }
}
