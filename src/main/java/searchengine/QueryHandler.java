package searchengine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    public List<Page> processQuery (String query) throws UnsupportedEncodingException {
        String formatedQuery = decodedQuery(query).toLowerCase().trim();
        List<Page> listToReturn = new ArrayList<Page>();
        if(isSimpleWord(formatedQuery)){
            listToReturn=getMatchingPages(formatedQuery);
        } 
        return listToReturn;
    }     
}