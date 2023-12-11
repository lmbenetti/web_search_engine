package searchengine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


 @TestInstance(Lifecycle.PER_CLASS)
 public class PageRankertest {
     private WebMapper testMap;
     private String singleSearchWord;
     private String multiSearchWord;
     private String multiOrSearchWord;

     @BeforeAll 
     void init(){
        testMap = new WebMapper();
        singleSearchWord = "United";
        multiSearchWord = "United States";
        multiOrSearchWord = "United or States";
        

     }

     @Test
     void pageRanker_simplePageRanker_CorrectDecreasingSorting_Single_Word(){

        List<String> query = new ArrayList<>() {{ add(singleSearchWord); }};
        Set<String> urlSet= testMap.getUrl(singleSearchWord);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(testMap.getPage(url));
        }

        
        Map<String, Double> IDFMap = new HashMap<>();
        IDFMap.put(singleSearchWord, testMap.getIDF(singleSearchWord));

         List<Page> pages = PageRanker.rankPages("simplePageRanker", query, pageSet, IDFMap);
        
         int last = Integer.MAX_VALUE;
         for(Page page: pages){
             int rank = page.getRank();
             assertTrue(rank <= last);
             last = rank;
         }

     }

     @Test
     void pageRanker_simplePageRanker_CorrectDecreasingSorting_MultipleWord(){
        List<String> query = new ArrayList<>() {{ add(multiSearchWord); }};
        Set<String> urlSet= testMap.getUrl(singleSearchWord);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(testMap.getPage(url));
        }

        Map<String, Double> IDFMap = new HashMap<>();
        String[] wordArray = multiSearchWord.split(" +");
        IDFMap.put(wordArray[0], testMap.getIDF(wordArray[0]));
        IDFMap.put(wordArray[1], testMap.getIDF(wordArray[1]));

         List<Page> pages = PageRanker.rankPages("simplePageRanker", query, pageSet, IDFMap);
        
         int last = Integer.MAX_VALUE;
         for(Page page: pages){
             int rank = page.getRank();
             assertTrue(rank <= last);
             last = rank;
         }

     }

    @Test
     void pageRanker_simplePageRanker_CorrectDecreasingSorting_MultipleOrWord(){
        List<String> query = new ArrayList<>() {{ add(multiOrSearchWord); }};
        Set<String> urlSet= testMap.getUrl(singleSearchWord);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(testMap.getPage(url));
        }

        Map<String, Double> IDFMap = new HashMap<>();
        String[] wordArray = multiOrSearchWord.split(" +");
        IDFMap.put(wordArray[0], testMap.getIDF(wordArray[0]));
        IDFMap.put(wordArray[2], testMap.getIDF(wordArray[2]));

         List<Page> pages = PageRanker.rankPages("simplePageRanker", query, pageSet, IDFMap);
        
         int last = Integer.MAX_VALUE;
         for(Page page: pages){
             int rank = page.getRank();
             assertTrue(rank <= last);
             last = rank;
         }

     }

     @Test
     void pageRanker_titlePageRanker_CorrectDecreasingSorting_SingleWord(){
        List<String> query = new ArrayList<>() {{ add(singleSearchWord); }};
        Set<String> urlSet= testMap.getUrl(singleSearchWord);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(testMap.getPage(url));
        }

        
        Map<String, Double> IDFMap = new HashMap<>();
        IDFMap.put(singleSearchWord, testMap.getIDF(singleSearchWord));

         List<Page> pages = PageRanker.rankPages("titlePageRanker", query, pageSet, IDFMap);
        
         int last = Integer.MAX_VALUE;
         for(Page page: pages){
             int rank = page.getRank();
             assertTrue(rank <= last);
             last = rank;
         }

     }

    @Test
     void pageRanker_titlePageRanker_CorrectDecreasingSorting_MultipleWord(){
        List<String> query = new ArrayList<>() {{ add(multiSearchWord); }};
        Set<String> urlSet= testMap.getUrl(singleSearchWord);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(testMap.getPage(url));
        }

        Map<String, Double> IDFMap = new HashMap<>();
        String[] wordArray = multiSearchWord.split(" +");
        IDFMap.put(wordArray[0], testMap.getIDF(wordArray[0]));
        IDFMap.put(wordArray[1], testMap.getIDF(wordArray[1]));

         List<Page> pages = PageRanker.rankPages("titlePageRanker", query, pageSet, IDFMap);
        
         int last = Integer.MAX_VALUE;
         for(Page page: pages){
             int rank = page.getRank();
             assertTrue(rank <= last);
             last = rank;
         }

     }
    @Test
     void pageRanker_titlePageRanker_CorrectDecreasingSorting_MultipleOrWord(){
        List<String> query = new ArrayList<>() {{ add(multiOrSearchWord); }};
        Set<String> urlSet= testMap.getUrl(singleSearchWord);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(testMap.getPage(url));
        }

        Map<String, Double> IDFMap = new HashMap<>();
        String[] wordArray = multiOrSearchWord.split(" +");
        IDFMap.put(wordArray[0], testMap.getIDF(wordArray[0]));
        IDFMap.put(wordArray[2], testMap.getIDF(wordArray[2]));

         List<Page> pages = PageRanker.rankPages("titlePageRanker", query, pageSet, IDFMap);
        
         int last = Integer.MAX_VALUE;
         for(Page page: pages){
             int rank = page.getRank();
             assertTrue(rank <= last);
             last = rank;
         }

     }

 }
