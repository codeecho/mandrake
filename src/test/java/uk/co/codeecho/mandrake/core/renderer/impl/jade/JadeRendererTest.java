
package uk.co.codeecho.mandrake.core.renderer.impl.jade;

import uk.co.codeecho.mandrake.core.renderer.impl.jade.JadeRenderer;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class JadeRendererTest {

    public JadeRendererTest() {
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
    public void testRenderAssets() throws Exception {
        String template = "asset-test.jade";
        Map<String, Object> model = new HashMap<String, Object>();
        JadeRenderer renderer = new JadeRenderer("");
        String result = renderer.render(template, model);
        assertEquals("<div><script type=\"text/javascript\" src=\"webjars/jquery/2.1.4/jquery.js\"> </script></div>", result);
    }

}