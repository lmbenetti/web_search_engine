package searchengine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

import java.util.regex.Pattern;

public class QueryHandlerTemp {
    private WebMapper webMap;


    public QueryHandlerTemp() {
            webMap = new WebMapper();
    }


    public List<Page> processQuery(String query)
    {
        String decodedQuery = decodeQuery(query).toLowerCase().trim();

        if(validateQuery(decodedQuery)){
            if(isSimpleWord(decodedQuery)){
                return getPagesSingleWordQuery(decodedQuery);
            }

            return getPagesMuliWordQuery(decodedQuery);

            

        }
        //if process query returns empty pagelist, we should print something to screen
        return new ArrayList<Page>();
    }

    
    //toAdd (we want to avoid raising exceptions, so we check once before we process any query !) (it is also a requirement for the exam)
    boolean validateQuery(String query)
    {
        return true;
    }

    
    //Returns pages mathing a word as a HashSet
    List<Page> getPagesSingleWordQuery(String query){

        HashSet<Page> listToReturn = new HashSet<Page>();

        //refactor so WebMapper takes care of everything locally (no need to call .getWebMap)
        if (webMap.getWebMap().containsKey(query)){
            listToReturn = webMap.getWebMap().get(query);
        }
        return PageRanker.rankPages("simplePageRanker", new ArrayList<>(Arrays.asList(query.split(" "))), listToReturn);
    }

    

    List<Page> getPagesMuliWordQuery(String query){
        //the first goal is to get distinct and and or sections to parse-through
        //regex pattern for which is
        String[] orSections = getOrSections(query); 

        //List<Set<Page>> reflects and while List<List<Set<Page>>> reflects or
        List<Set<Page>> decompressedOrQuery = new ArrayList<Set<Page>>();

        for(int x = 0; x < orSections.length; x++){

            String[] andSections = orSections[x].split(" ");
            List<Set<Page>> decompressedAndQuery = new ArrayList<Set<Page>>();

            for(int y = 0; y < andSections.length; y++){
                decompressedAndQuery.add(webMap.getWebMap().get(andSections[y]));
            }

            decompressedOrQuery.add(logicalAnd(decompressedAndQuery));

        }

        return PageRanker.rankPages("simplePageRanker", new ArrayList<>(Arrays.asList(query.split(" "))),  logicalOr(decompressedOrQuery));
    }

    Set<Page> logicalOr(List<Set<Page>> orSets){

        Set<Page> postOr = new HashSet<Page>();

        for(Set<Page> pageSet : orSets){
            postOr.addAll(pageSet);
        }

        return postOr;
    }



    Set<Page> logicalAnd(List<Set<Page>> andSets){


        Iterator<Set<Page>> itr = andSets.iterator();
        Set<Page> postAnd = itr.next();

        while(itr.hasNext()){
            postAnd.retainAll(itr.next());
        }

        return postAnd;

    }




    //Helpermethods (all package private for now)

    String decodeQuery(String originalQuery){
        try {
            return URLDecoder.decode(originalQuery, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("The query from the webpage was faulty");
            return "";
        }
    }

    //Boolean which tests if word is simple or not (one word search or multiple word search)
    private boolean isSimpleWord(String query){
        return (!decodeQuery(query).trim().contains(" ")); 


    }

    String[] getOrSections(String query){
        Pattern pattern = Pattern.compile(" (or) ", Pattern.CASE_INSENSITIVE);
        return pattern.split(query);
    }
}

