package searchengine;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class PageRanker {
    
    public static List<Page> rankPages(String queryType, List<String> queries, Set<Page> pages) {
        List<Page> orderedList;
        switch(queryType) {
            case "simplePageRanker":
              orderedList = simplePageRanker(queries, pages);
              break;
            default:
                orderedList = new ArrayList<Page>();
              // code block
          }
          
        return orderedList;
    
    }

    private static List<Page> simplePageRanker(List<String> queries, Set<Page> Pages){


        for(Page page: Pages){
            int rank = 0;
            for(String query : queries){
                String[] splitQuery = query.split(" ");
                for(String qWord: splitQuery){
                    rank += page.getWordFrequency(qWord);

                }
            }
            page.setRank(rank);
        }     
        return new ArrayList<Page>();
    }

}
