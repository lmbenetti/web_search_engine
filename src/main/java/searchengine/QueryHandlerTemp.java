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
            return getPagesMultiWordQuery(decodedQuery);
        }
        //if process query returns empty pagelist, we should print something to screen
        return new ArrayList<Page>();
    }

    boolean validateQuery(String query) {
        // check that query has content
        if (query == null || query.trim().isEmpty()) {
            return false;
        }
        // check for minimum 1 letter and that is not just single special character
        String regex = ".*[a-z]+.*";
        return query.matches(regex);
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

    List<Page>getPagesMultiWordQuery(String query){
        //step 1: parse string for operations

        String[] orSections = getOrSections(query);
        String[] andSection;


        List<Set<Page>> orSets = new ArrayList<Set<Page>>();
        List<Set<Page>> andSets = new ArrayList<Set<Page>>();

        for(int o = 0; o  < orSections.length; o++){

            andSection = orSections[o].split(" +");
            for(int a = 0; a< andSection.length; a++){
                andSets.add(webMap.getPageSet(andSection[a]));
            }
            orSets.add(logicalAnd(andSets));
            andSets.clear();
        }

        return PageRanker.rankPages("simplePageRanker", new ArrayList<>(Arrays.asList(query.split(" "))), logicalOr(orSets));

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
        return (!query.trim().contains(" ")); 


    }

    String[] getOrSections(String query){
        Pattern pattern = Pattern.compile("\\b\\s+or\\s+\\b", Pattern.CASE_INSENSITIVE);
        return pattern.split(query);
    }
    
}

