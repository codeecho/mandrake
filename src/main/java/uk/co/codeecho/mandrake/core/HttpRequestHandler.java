package uk.co.codeecho.mandrake.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import uk.co.codeecho.mandrake.core.request.Response;
import uk.co.codeecho.mandrake.core.request.impl.ServletRequest;
import uk.co.codeecho.mandrake.core.router.Router;

public class HttpRequestHandler implements org.springframework.web.HttpRequestHandler {
    
    private final Router router;

    public HttpRequestHandler(Router router) {
        this.router = router;
    }
    
    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = router.handle(new ServletRequest(req, new HashMap<String, String>()));
        resp.setStatus(response.getStatus());
        for(Entry<String, String> header: response.getHeaders().entrySet()){
            resp.setHeader(header.getKey(), header.getValue());
        }
        Object body = response.getBody();
        if(body!=null){
            if(body instanceof String){
                resp.getWriter().write((String)body);
            }else if(body instanceof InputStream){
                IOUtils.copy((InputStream)body, resp.getOutputStream());
            }else{
                resp.getWriter().write(body.toString());
            }
        }
    }

}
