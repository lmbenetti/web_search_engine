package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebMapper {
    private HashMap<String, List<Page>> webMap;

    public WebMapper(String filename){
        webMap = makeWebMap(filename);
    }

    private HashMap<String, List<Page>> makeWebMap(String filename){
        HashMap<String, List<Page>> mapToReturn = new HashMap<>();
        List<Page> pageList = getPages(filename);
        for (Page page : pageList) {
            for(String word : page.getWebSiteWords()){
                if (mapToReturn.containsKey(word)){
                    List <Page> pageToAdd = mapToReturn.get(word);
                    pageToAdd.add(page);
                    mapToReturn.put(word, pageToAdd);
                }
                else mapToReturn.put(word, new ArrayList<Page>(){{
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
                pageList.add(new Page(lines.subList(i, lastIndex)));
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

    public HashMap<String, List<Page>> getWebMap() {
        return webMap;
    }
}
