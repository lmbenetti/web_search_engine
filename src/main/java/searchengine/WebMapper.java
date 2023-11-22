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

    public WebMapper(){
        webMap = new HashMap<>();
    }

    private HashMap<String, List<Page>> makeWebMap(String filename) throws IOException{
        List<Page> pageList = getPages(filename);

        for (Page page : pageList) {
            
        }

        return new HashMap<String, List<Page>>();

    }



    private List<Page> getPages(String filename) throws IOException {
        try {
            List<Page> pageList = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filename));
            var lastIndex = lines.size();
            for (var i = lines.size() - 1; i >= 0; --i) {
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
    }
}
