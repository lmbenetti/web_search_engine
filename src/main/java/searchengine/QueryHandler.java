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
        String toReturn = URLDecoder.decode(originalQuery, StandardCharsets.UTF_8.toString());
        return toReturn;
    }

    public List<Page> getMatchingWebPages(String query) throws UnsupportedEncodingException {
        String queryLowerCase = decodedQuery(query).toLowerCase();
        queryLowerCase = queryLowerCase.trim();
        List<Page> listToReturn = new ArrayList<Page>();
        if (webMapper.getWebMap().containsKey(queryLowerCase)){
            listToReturn = webMapper.getWebMap().get(queryLowerCase);
            return listToReturn;
        }
        return listToReturn;
    }
}
