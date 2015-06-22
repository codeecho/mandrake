
package uk.co.codeecho.mandrake.core;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.router.Router;

public class HttpRequestHandlerTest {

    public HttpRequestHandlerTest() {
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
    public void testHandleRequest() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        HttpServletRequest mockRequest = mocksControl.createMock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mocksControl.createMock(HttpServletResponse.class);
        Router router = new Router();
        router.route(HttpMethod.GET, "/").response("It works!");
        HttpRequestHandler requestHandler = new HttpRequestHandler(router);
        expect(mockRequest.getMethod()).andReturn("GET");
        expect(mockRequest.getRequestURI()).andReturn("/context/servlet");
        expect(mockRequest.getServletPath()).andReturn("/servlet");
        expect(mockRequest.getContextPath()).andReturn("/context");
        mockResponse.setStatus(200);
        PrintWriter mockWriter = mocksControl.createMock(PrintWriter.class);
        expect(mockResponse.getWriter()).andReturn(mockWriter);
        mockWriter.write("It works!");
        mocksControl.replay();
        requestHandler.handleRequest(mockRequest, mockResponse);
        mocksControl.verify();
    }

}