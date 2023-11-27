package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryHandlerTest {
    private QueryHandler systemUnderTest;

    @BeforeAll 
    void init(){
        systemUnderTest = new QueryHandler("data/enwiki-medium.txt");

    }


    @Test
    void QueryHandler_getMatchingWebPages_is_ListofPage(){
        assertEquals(Page.class, systemUnderTest.getMatchingWebPages("bee").get(0).getClass());
    }


}
