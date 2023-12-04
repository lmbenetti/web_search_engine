package searchengine;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.TestInstance.Lifecycle;

// import java.util.Random;
// import java.util.Set;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.List;
// import java.io.IOException;
import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.stream.Stream;
// import java.util.stream.Collectors;
// import java.util.Set;
// import java.util.HashSet;

@TestInstance(Lifecycle.PER_CLASS)
public class PageRankertest {
    private WebMapper testMap;

    @BeforeAll 
    void init(){
        testMap = new WebMapper();
    }

    @Test
    void pageRanker_CorrectDecreasingSorting(){
        String singleSearchWord = "united";
        List<String> query = new ArrayList() {{ add(singleSearchWord); }};
        List<Page> pages = PageRanker.rankPages("simplePageRanker", query, testMap.getWebMap().get(singleSearchWord));
        
        int last = Integer.MAX_VALUE;
        for(Page page: pages){
            int rank = page.getRank();
            assertTrue(rank < last);
            last = rank;
        }

    }

}
