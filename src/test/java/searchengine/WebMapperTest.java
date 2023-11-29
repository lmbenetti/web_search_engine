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

import java.io.IOException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebMapperTest {
    private WebMapper systemUnderTest_small;

    @BeforeAll 
    void init(){
        systemUnderTest_small = new WebMapper();
    }

    @Test
    void WebMapper_getWebMap_correctType(){
        assertEquals(systemUnderTest_small.getClass(), WebMapper.class);
    }

    @Test
    void WebMapper_getWebMap_containsPage(){
        assertEquals( Page.class, systemUnderTest_small.getWebMap().get("bag").iterator().next().getClass());
    }
    

    

}
