
package uk.co.codeecho.mandrake.core.util.pattern;

import java.util.Set;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathPatternTest {

    public PathPatternTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void test() {
        PathPattern pathPattern = new PathPattern("/{type}/{id}/test");
        //assertEquals("/(?<type>.*)/(?<id>.*)/test", pathPattern.getPattern().pattern());
        assertTrue(pathPattern.getParameters().contains("type"));
        assertTrue(pathPattern.getParameters().contains("id"));
        assertFalse(pathPattern.test("/foobar").matches());
        assertTrue(pathPattern.test("/foo/123/test").matches());
        assertEquals("foo", pathPattern.test("/foo/123/test").getParameters().get("type"));
        assertEquals("123", pathPattern.test("/foo/123/test").getParameters().get("id"));
        pathPattern = new PathPattern("/test.html");
        assertTrue(pathPattern.test("/test.html").matches());
    }

    
}