package searchengine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class QueryHandler {
    private WebMapper webMapper;
    
    public QueryHandler() throws IOException {
        webMapper = new WebMapper();
    }

    private String decodedQuery(String originalQuery)throws UnsupportedEncodingException{
        return URLDecoder.decode(originalQuery, StandardCharsets.UTF_8.toString());
    }

    private boolean isSimpleWord(String query) throws UnsupportedEncodingException{
        return (!decodedQuery(query).trim().contains(" ")); 
    }

    private List <Page> getMatchingPages(String query){
        List<Page> listToReturn = new ArrayList<Page>();
        if (webMapper.getWebMap().containsKey(query)){
            listToReturn = webMapper.getWebMap().get(query);
        }
        return listToReturn;
    }

    private ArrayList <String> getWords(String query){
        String queryFormated = query.replaceAll(" +", " ");
        ArrayList <String> listOfWords = new ArrayList<>(Arrays.asList(queryFormated.split(" ")));
        return listOfWords;
    }
    /*Shouldn't multiple word searches return more pages than single word searches? */
    private ArrayList <Page> getMatchinPagesMultipleWords(ArrayList<String> listOfWords){
        ArrayList <Page> toReturn = new ArrayList<>();
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
        return toReturn;
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