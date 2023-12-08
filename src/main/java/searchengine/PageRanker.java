package searchengine;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

public class PageRanker {
    
    
    /** 
     * A Static method which applies one of multiple ranking algorithms depending on the querytype argument. 
     * @param queryType A string reflecting the applied ranking algorithm.
     * @param queries A list of strings reflecting the search-words from the query.
     * @param pages An unordered set of pages to be ranked.
     * @return An ordered list of pages is returned based on the queries and unordered set of pages provides.
     */
    public static List<Page> rankPages(String queryType, List<String> queries, Set<Page> pages) {
        List<Page> orderedList;
        switch(queryType) {
            case "simplePageRanker":
                orderedList = simplePageRanker(queries, pages);
                break;
            case "titlePageRanker":
                orderedList = titlePageRanker(queries, pages);
                break;
            default:
                orderedList = new ArrayList<Page>();
        }
          
        return orderedList;
    
    }

    
    
    /** 
     * A simple ranking algorithm which takes a list of query-strings and an unordered set of pages and returns an ordered list of pages. The ranking is only based on the frequency of words in a given website.
     * @param queries A list of strings reflecting the search-words from the query.
     * @param pages An unordered set of pages to be ranked.
     * @return An ordered List<Page>-object is returned. 
     */
    private static List<Page> simplePageRanker(List<String> queries, Set<Page> pages){


        for(Page page: pages){
            int rank = 0;
            for(String query : queries){
                String[] splitQuery = query.split(" +");
                for(String qWord: splitQuery){
                    int toAdd = page.getWordFrequency(qWord);
                    rank += toAdd == -1? 0: toAdd;
                }
            }
            page.setRank(rank);
        }    

        List<Page> orderedList = new ArrayList<Page>(pages);
        Collections.sort(orderedList, Collections.reverseOrder());
        return orderedList;
    }

    private static List<Page> titlePageRanker(List<String> queries, Set<Page> pages){

        for(Page page: pages){
            int rank = 0;
            for(String query : queries){
                String[] splitQuery = query.split(" +");
                for(String qWord: splitQuery){
                    int toAdd = page.getWordFrequency(qWord);
                    rank += toAdd == -1? 0: toAdd;
                    if(page.getTitle().toLowerCase().contains(qWord.toLowerCase())){
                        rank *=2;
                    }
                }
            }
            page.setRank(rank);
        }
        
        List<Page> orderedList = new ArrayList<Page>(pages);
        Collections.sort(orderedList, Collections.reverseOrder());
        return orderedList;
    }


}
