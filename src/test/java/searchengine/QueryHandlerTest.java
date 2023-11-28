package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryHandlerTest {
    private QueryHandler systemUnderTest;

    @BeforeAll 
    void init(){
        try {
            systemUnderTest = new QueryHandler();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Check config.txt for any problems");
        }

    }

    @Test
    void ProcessQuery_CorrectInput_SimpleQuery(){
        try {
            assertFalse(systemUnderTest.processQuery(" bag ").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' bag ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }

    }

    @Test
    void ProcessQuery_IncorrectInput_SimpleQuery(){
        try {
            assertTrue(systemUnderTest.processQuery(" thiswordshouldnotexistinthedictionaryandshouldneverbesearchable ").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for unsearchable simple word failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }
    }

    
    /*we want this test to be true in the future, so let's keep up the good work */
    @Test
    void ProcessQuery_CorrectInput_SpecialCharacters(){
        try {
            assertFalse(systemUnderTest.processQuery(" ?bag! ").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' ?bag! ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }

    }

    @Test
    void ProcessQuery_CorrectInput_OneLetterOff(){
        try {
            assertFalse(systemUnderTest.processQuery(" applo ").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' applo ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }

    }

    @Test
    void ProcessQuery_CorrectInput_CapitalLetter(){
        try {
            assertFalse(systemUnderTest.processQuery(" Apple ").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' applo ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }
    }

    @Test
    void ProcessQuery_CorrectInput_MultipleWords_LongerReturnList(){
        try {
            assertTrue(systemUnderTest.processQuery("apple bee man").size() > systemUnderTest.processQuery("apple").size());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' applo ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }
    }


    @Test
    void ProcessQuery_CorrectInput_MultipleWords_(){
        try {
            assertFalse(systemUnderTest.processQuery("man isle").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' applo ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }
    }

    @Test
    void ProcessQuery_CorrectInput_MultipleWords_Connected_Words(){
        try {
            assertTrue(systemUnderTest.processQuery("fruit banana").isEmpty());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Test for the word ' applo ' failed due to Exception. \nPlease check encoding of characters.");
            assertTrue(false);
        }
    }

}
