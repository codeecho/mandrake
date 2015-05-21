package uk.co.codeecho.mandrake.core.renderer.impl.jade;

import uk.co.codeecho.mandrake.core.renderer.impl.jade.AssetLoader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AssetLoaderTest {

    public AssetLoaderTest() {
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
    public void testLoadJS() {
        AssetLoader assetLoader = new AssetLoader();
        String result = assetLoader.convert("test.js", null);
        assertEquals("<script type=\"text/javascript\" src=\"assets/test.js\"> </script>", result);
    }

    @Test
    public void testLoadCSS() {
        AssetLoader assetLoader = new AssetLoader();
        String result = assetLoader.convert("test.css", null);
        assertEquals("<link rel=\"stylesheet\" type=\"text/css\" href=\"assets/test.css\">", result);
    }

    @Test
    public void testLoadWebjarJS() {
        AssetLoader assetLoader = new AssetLoader();
        String result = assetLoader.convert("webjar:jquery.js", null);
        assertEquals("<script type=\"text/javascript\" src=\"webjars/jquery/2.1.4/jquery.js\"> </script>", result);
    }

    @Test
    public void testLoadNonExistantWebjarJS() {
        AssetLoader assetLoader = new AssetLoader();
        try {
            String result = assetLoader.convert("webjar:foobar.js", null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

}
