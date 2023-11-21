package searchengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

public class InvertedIndex {    
    
    public void getPages(String filename) throws IOException {
        Map<String, Integer> invertedIndex = new HashMap<>();
        
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
    
    public static void main(String[] args) {
        
    }
}
