package searchengine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

public class QueryHandler {
    private WebMapper webMapper;
    
    //Constructor Class
    public QueryHandler() throws IOException {
        webMapper = new WebMapper();
    }

    //Helper Method to decode query
    private String decodedQuery(String originalQuery)throws UnsupportedEncodingException{
        return URLDecoder.decode(originalQuery, StandardCharsets.UTF_8.toString());
    }

    //Boolean which tests if word is simple or not (one word search or multiple word search)
    private boolean isSimpleWord(String query) throws UnsupportedEncodingException{
        return (!decodedQuery(query).trim().contains(" ")); 
    }

    //Returns pages mathing a word as a HashSet
    private List<Page> getMatchingPages(String query){
        HashSet<Page> listToReturn = new HashSet<Page>();
        if (webMapper.getWebMap().containsKey(query)){
            listToReturn = webMapper.getWebMap().get(query);
        }
        return PageRanker.rankPages("simplePageRanker", getWords(query), listToReturn);
    }

    //Returns ArrayList of Words in query
    private List <String> getWords(String query){
        String queryFormated = query.replaceAll(" +", " ");
        ArrayList <String> listOfWords = new ArrayList<>(Arrays.asList(queryFormated.split(" ")));
        return listOfWords;
    }

    //MultiplewordSearch
    private List<Page> getMatchinPagesMultipleWords(List<String> listOfWords){
        HashSet<Page> toReturn = new HashSet<Page>();
        HashMap <Page, Integer> pagesOfTheSearch = new HashMap<>();

        for (String word : listOfWords){
            for (Page page : getMatchingPages(word)){
                if (pagesOfTheSearch.containsKey(page)){
                    int toChange = pagesOfTheSearch.get(page)+1;
                    pagesOfTheSearch.put(page, toChange);
                }
                else pagesOfTheSearch.put(page, 1);
            }
        }
        for (HashMap.Entry<Page, Integer> entry : pagesOfTheSearch.entrySet()){
            int occurrences = entry.getValue();
            if(occurrences == listOfWords.size()){
                toReturn.add(entry.getKey());
            }
        }
        return PageRanker.rankPages("simplePageRanker", listOfWords, toReturn);
    }

    public List<Page> processQuery (String query) throws UnsupportedEncodingException {
        String formatedQuery = decodedQuery(query).toLowerCase().trim();
        List<Page> listToReturn = new ArrayList<Page>();
        if(isSimpleWord(formatedQuery)){
            listToReturn=getMatchingPages(formatedQuery);
        }
        if (!isSimpleWord(formatedQuery)){
           listToReturn = getMatchinPagesMultipleWords(getWords(formatedQuery));
        }


        return listToReturn;
    }
         
}