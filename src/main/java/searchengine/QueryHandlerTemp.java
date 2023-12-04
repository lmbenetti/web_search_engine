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
    private WebMapperTemp webMap;



    public QueryHandlerTemp() {
        webMap = new WebMapperTemp();
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


        /** 
     * A method that checks wheter the user input is a valid query or no. To decide that it checks if
     * it is not empty abd if it contains at least one letter and not just a single special character.
     * @param query A String that contains what the user has typed in the search block. 
     * @return boolean Returns true if the query is valid or false if it is not.
     */
    private boolean validateQuery(String query) {
        // check that query has content
        if (query == null || query.trim().isEmpty()) {
            return false;
        }
        //check for and exclude "or"
        // check for minimum 1 letter and that is not just single special character
        String regex = ".*[a-z]+.*";
        return query.matches(regex);
    }


    //Returns pages mathing a word as a HashSet
    private List<Page> getPagesSingleWordQuery(String query){

        Set<String> urlSet = webMap.getUrl(query);
        Set<Page> pageSet = new HashSet<Page>();

        //refactor so WebMapper takes care of everything locally (no need to call .getWebMap)
        if (urlSet.isEmpty()){
            return new ArrayList<Page>();
        }

        for(String url : urlSet){

            pageSet.add(webMap.getPage(url));
        }
        return PageRanker.rankPages("simplePageRanker", new ArrayList<>(Arrays.asList(query.split(" "))), pageSet);
    }



    private List<Page> getPagesMultiWordQuery(String query){

        String[] orSections = getOrSections(query);
        String[] andSection;


        List<Set<String>> orSets = new ArrayList<Set<String>>();
        List<Set<String>> andSets = new ArrayList<Set<String>>();


        for(int o = 0; o  < orSections.length; o++){

            andSection = orSections[o].split(" +");
            for(int a = 0; a< andSection.length; a++){
                andSets.add(webMap.getUrl(andSection[a]));
            }
            orSets.add(logicalAnd(andSets));
            andSets.clear();
        }


        Set<String> urlSet = logicalOr(orSets);
        Set<Page> pageSet = new HashSet<Page>();

        for(String url: urlSet){
            pageSet.add(webMap.getPage(url));
        }

        return PageRanker.rankPages("simplePageRanker", new ArrayList<>(Arrays.asList(query.split(" "))), pageSet);
    }

    private Set<String> logicalOr(List<Set<String>> orSets){

        Set<String> postOr = new HashSet<String>();

        for(Set<String> pageSet : orSets){
            postOr.addAll(pageSet);
        }

        return postOr;
    }

    private Set<String> logicalAnd(List<Set<String>> andSets){

        Iterator<Set<String>> itr = andSets.iterator();
        Set<String> postAnd = itr.next();

        while(itr.hasNext()){
            postAnd.retainAll(itr.next());
        }

        return postAnd;

    }

    //Helpermethods (all package private for now)

    private String decodeQuery(String originalQuery){
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

    private String[] getOrSections(String query){
        Pattern pattern = Pattern.compile("\\b\\s+or\\s+\\b", Pattern.CASE_INSENSITIVE);
        return pattern.split(query);
    }


    public static void main(String[] args) {
        QueryHandlerTemp q = new QueryHandlerTemp();
        List<Page> a = q.processQuery("banana or education");
        System.out.println(a.size());
    }
}
