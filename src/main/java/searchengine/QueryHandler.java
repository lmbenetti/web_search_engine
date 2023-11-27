package searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {
    private WebMapper webMapper;
    
    public QueryHandler() throws IOException {
        webMapper = new WebMapper();
    }

    public List<Page> getMatchingWebPages(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Page> listToReturn = new ArrayList<Page>();
        if (webMapper.getWebMap().containsKey(queryLowerCase)){
            listToReturn = webMapper.getWebMap().get(queryLowerCase);
            return listToReturn;
        }
        return listToReturn;
    }
}
