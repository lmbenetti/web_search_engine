package searchengine;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.TestInstance.Lifecycle;

// import java.util.Random;
// import java.util.Set;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.List;
// import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.stream.Stream;
// import java.util.stream.Collectors;
// import java.util.Set;
// import java.util.HashSet;

// import java.io.IOException;
// import java.io.UnsupportedEncodingException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryHandlerTest {
    private QueryHandler systemUnderTest;

    @BeforeAll 
    void init(){
        systemUnderTest = new QueryHandler();
    }

    @Test
    void ProcessQuery_CorrectInput_SimpleQuery(){
        assertFalse(systemUnderTest.processQuery(" werner ").isEmpty());

    }

    @Test
    void ProcessQuery_IncorrectInput_SimpleQuery(){
        assertTrue(systemUnderTest.processQuery(" thiswordshouldnotexistinthedictionaryandshouldneverbesearchable ").isEmpty());

    }
     
    @Test
    void ProcessQuery_CorrectInput_SpecialCharacters(){
        assertTrue(systemUnderTest.processQuery(" ?werner! ").isEmpty());
    }


    @Test
    void ProcessQuery_CorrectInput_OneLetterOff(){
        assertTrue(systemUnderTest.processQuery(" applo ").isEmpty());

    }


    @Test
    void ProcessQuery_CorrectInput_CapitalLetter(){
        assertFalse(systemUnderTest.processQuery(" Union ").isEmpty());

    }

    @Test
    void ProcessQuery_CorrectInput_MultipleAndWords_ShorterReturnList(){
        assertTrue(systemUnderTest.processQuery("sun united").size() < systemUnderTest.processQuery("united").size());

    }


    @Test
    void ProcessQuery_CorrectInput_MultipleWords_ConnectedWords1(){
        assertFalse(systemUnderTest.processQuery("modern asia").isEmpty());
    }

    @Test
    void ProcessQuery_CorrectInput_MultipleWords_ConnectedWords2(){
        assertFalse(systemUnderTest.processQuery("union america").isEmpty());
    }

    @Test
    void ProcessQuery_CorrectInput_MultipleWords_SearchesForOr(){
        assertTrue(systemUnderTest.processQuery("Or").isEmpty());
    }

    @Test
    void ProcessQuery_CorrectInput_MultipleWords_StartsWithOr(){
        assertTrue(systemUnderTest.processQuery("Or union").isEmpty());
    }

    @Test
    void ProcessQuery_CorrectInput_MultipleWords_EndsWithOr(){
        assertTrue(systemUnderTest.processQuery("union or").isEmpty());
    }

    // @Test
    // void getOrStrings_CorrectInput_complexQuery(){
    //     String[] result = systemUnderTest.getOrSections("Husband or Wife");
    //     assertEquals("Husband", result[0]);
    //     assertEquals("Wife", result[1]);

    // }

    // @Test
    // void getOrStrings_CorrectInput_complexQuery2(){
    //     String[] result = systemUnderTest.getOrSections("Husband or Wife or Daugther OR Son Or or");
    //     assertEquals("Husband", result[0]);
    //     assertEquals("Wife", result[1]);
    //     assertEquals("Daugther", result[2]);
    //     assertEquals("Son", result[3]);
    //     assertEquals("or", result[4]);
    // }

    // @Test
    // void getOrStrings_CorrectInput_complexQuery3(){
    //     String[] result = systemUnderTest.getOrSections("Husband Wife or Wife Husband or Brother Daugther OR Son Sister Or or no");
    //     assertEquals("Husband Wife", result[0]);
    //     assertEquals("Wife Husband", result[1]);
    //     assertEquals("Brother Daugther", result[2]);
    //     assertEquals("Son Sister", result[3]);
    //     assertEquals("or no", result[4]);

    // }


    //testing logicalAnd and logicalOr

    // @Test
    // void logicalAnd_CorrectInput_CorrectPostAnd(){
    //     List<Set<Page>> testInput = new ArrayList<>();

    //     testInput.add(webMapHelper.getWebMap().get("united"));
    //     testInput.add(webMapHelper.getWebMap().get("states"));

    //     // assertFalse(systemUnderTest.logicalAnd(testInput).isEmpty());
    //     // assertTrue(systemUnderTest.logicalAnd(testInput).size() < testInput.get(1).size());

    // }

    // @Test
    // void logicalOr_CorrectInput_CorrectPostOr(){
    //     List<Set<Page>> testInput = new ArrayList<>();

    //     testInput.add(webMapHelper.getWebMap().get("united"));
    //     testInput.add(webMapHelper.getWebMap().get("banana"));

    //     // assertFalse(systemUnderTest.logicalOr(testInput).isEmpty());
    //     // assertTrue(systemUnderTest.logicalOr(testInput).size() >= testInput.get(1).size());
    // }

    @Test
    void processQuery_multipleCalls_sameOutput(){
        int one = systemUnderTest.processQuery("man woman").size();
        systemUnderTest.processQuery("woman man");
        int two = systemUnderTest.processQuery("man woman").size();
        assertEquals(one, two);
    }

    // @Test
    // void getPagesMultiWordQuery_multipleCalls_sameOutput(){
    //     int one = systemUnderTest.getPagesMultiWordQuery("man woman").size();
    //     systemUnderTest.getPagesMultiWordQuery("woman man");
    //     int two = systemUnderTest.getPagesMultiWordQuery("man woman").size();
    //     assertEquals(one, two);
    // }

}

