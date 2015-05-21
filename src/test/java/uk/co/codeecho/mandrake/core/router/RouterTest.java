
package uk.co.codeecho.mandrake.core.router;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.codeecho.mandrake.core.controller.Controller;
import uk.co.codeecho.mandrake.core.controller.ControllerChain;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class RouterTest {

    public RouterTest() {
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
    public void testHandle() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        Request mockRequest = mocksControl.createMock(Request.class);
        Controller mockController = mocksControl.createMock(Controller.class);
        Router router = new Router();
        router.route(HttpMethod.GET, "/test.html").to(mockController);
        expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
        expect(mockRequest.getPath()).andReturn("/test.html");
        Capture<Request> request = new Capture<Request>();
        expect(mockController.handle(capture(request), isA(Response.class), isA(Map.class), isA(ControllerChain.class))).andReturn(Response.ok().build());
        mocksControl.replay();
        Response result = router.handle(mockRequest);
        mocksControl.verify();
    }
    
    @Test
    public void testHandlePathParam() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        Request mockRequest = mocksControl.createMock(Request.class);
        Controller mockController = mocksControl.createMock(Controller.class);
        Router router = new Router();
        router.route(HttpMethod.GET, "/{path}").to(mockController);
        expect(mockRequest.getMethod()).andReturn(HttpMethod.GET);
        expect(mockRequest.getPath()).andReturn("/test.html");
        Capture<Request> requestCapture = new Capture<Request>();
        expect(mockController.handle(capture(requestCapture), isA(Response.class), isA(Map.class), isA(ControllerChain.class))).andReturn(Response.ok().build());
        mocksControl.replay();
        Response result = router.handle(mockRequest);
        Request request = requestCapture.getValue();
        assertEquals("test.html", request.getPathParameter("path"));
        mocksControl.verify();
    }

}