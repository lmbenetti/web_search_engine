package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


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
}
