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
        List<Page> listToReturn = new ArrayList<Page>();
        if (webMapper.getWebMap().containsKey(query)){
            listToReturn = webMapper.getWebMap().get(query);
            return listToReturn;
        }
        return listToReturn;
    }
}
