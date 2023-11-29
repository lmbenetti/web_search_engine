package searchengine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

/**
 * The QueryHandler class handles the search queries.
 */
public class QueryHandler {
    private WebMapper webMapper;
    
    /**
     * Constructor for QueryHandler.
     * Initializes the WebMapper.
     *
     * @throws IOException if there is an IO error during the initialization process.
     */
    public QueryHandler() throws IOException {
        webMapper = new WebMapper();
    }

    /**
     * Decodes the query string.
     *
     * @param originalQuery The URL encoded query string.
     * @return The decoded query string.
     * @throws UnsupportedEncodingException if the charset is not supported.
     */
    private String decodedQuery(String originalQuery)throws UnsupportedEncodingException{
        return URLDecoder.decode(originalQuery, StandardCharsets.UTF_8.toString());
    }

    /**
     * Checks if query string is a single word.
     *
     * @param query The query string to be checked.
     * @return true if it is a single word and false if it is multiple words.
     * @throws UnsupportedEncodingException if the charset is not supported.
     */
    private boolean isSimpleWord(String query) throws UnsupportedEncodingException{
        return (!decodedQuery(query).trim().contains(" ")); 
    }

    /**
     * Retrieves the list of pages that match the given query string.
     *
     * @param query A single word query string.
     * @return A list of Page objects that match the query string.
     */
    private List <Page> getMatchingPages(String query){
        List<Page> listToReturn = new ArrayList<Page>();
        if (webMapper.getWebMap().containsKey(query)){
            listToReturn = webMapper.getWebMap().get(query);
        }
        return listToReturn;
    }

    /**
     * Splits a query string into single words.
     *
     * @param query The query string to be split.
     * @return An ArrayList of all single words contained in the query string.
     */
    private ArrayList <String> getWords(String query){
        String queryFormated = query.replaceAll(" +", " ");
        ArrayList <String> listOfWords = new ArrayList<>(Arrays.asList(queryFormated.split(" ")));
        return listOfWords;
    }

    /**
     * Retrieves a list of pages that match with all words of a multi-word query.
     *
     * @param listOfWords An ArrayList of single words contained in a query string.
     * @return An ArrayList of Page objects where each page matches all words in the query.
     */
    private ArrayList <Page> getMatchinPagesMultipleWords(ArrayList<String> listOfWords){
          /*Shouldn't multiple word searches return more pages than single word searches? */
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

    /**
     * Processes a query string, decodes it, and retrieves the matching pages accordingly for both single- and multi-word queries.
     *
     * @param query The query string.
     * @return A list of Page objects where each page matches all words in the query.
     * @throws UnsupportedEncodingException if the charset is not supported.
     */
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