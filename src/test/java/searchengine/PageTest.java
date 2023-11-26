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


@TestInstance(Lifecycle.PER_CLASS)
public class PageTest {
    private Page systemUnderTest;

    @BeforeAll 
    void init(){
    systemUnderTest=new Page(new ArrayList<String>(Arrays.asList("*PAGE:https://en.wikipedia.org/wiki/United_States\nUnited States\nthe\nunited\nstates\nof\namerica".split("\n"))));
    }

    @Test
    void pageConstructor_CorrectInput_TitleTest(){
        assertEquals("United States", systemUnderTest.getTitle());
    }

    @Test
    void pageConstructor_CorrectInput_URLTest(){
        assertEquals("https://en.wikipedia.org/wiki/United_States", systemUnderTest.getUrl());
    }


    @Test
    void pageConstructor_CorrectInput_WordFrequencyTest(){
        List<String> input = new ArrayList<String>(Arrays.asList("*PAGE:https://en.wikipedia.org/wiki/United_States\nUnited States\nthe\nthe\nunited\nstates\nof\namerica".split("\n")));
        System.out.println(input);
        Page test = new Page(input);
        assertEquals((Integer) 2, test.getWordFrequency("the"));
    }


    @Test
    void pageConstructor_CorrectInput_KeySetTest(){
        List<String> input = new ArrayList<String>(Arrays.asList("*PAGE:https://en.wikipedia.org/wiki/United_States\nUnited States\nthe\nthe\nunited\nstates\nof\namerica".split("\n")));
        System.out.println(input);
        Page test = new Page(input);
        Set<String> expected = new HashSet<String>();
        expected.add("the");
        expected.add("united");
        expected.add("states");
        expected.add("of");
        expected.add("america");

        assertEquals(expected, systemUnderTest.getWebSiteWords());
    }

 /* 
    @Test
    void pageConstructor_CorrectInput_MapTest(){
        List<String> input = new ArrayList<String>(Arrays.asList("*PAGE:https://en.wikipedia.org/wiki/United_States\nUnited States\nthe\nunited\nstates\nof\namerica".split("\n")));
        System.out.println(input);
        Page test = new Page(input);
        Map<String, Integer> expected =  Stream.of(new Object[][] { 
        {"the", 1},
        { "united", 1 }, 
        { "states", 1 }, 
        { "of", 1 }, 
        { "america", 1 }, 
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
        assertEquals(expected, test.getWordFrequencyMap());
    }

    */

    
}
