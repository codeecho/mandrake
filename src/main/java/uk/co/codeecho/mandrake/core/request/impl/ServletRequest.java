package uk.co.codeecho.mandrake.core.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.request.Request;

public class ServletRequest implements Request{

    private HttpServletRequest request;
    private Map<String, String> pathParameters;

    public ServletRequest(HttpServletRequest request, Map<String, String> pathParameters) {
        this.request = request;
        this.pathParameters = pathParameters;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.valueOf(request.getMethod());
    }

    @Override
    public String getPath() {
        String path = request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length());
        if(path.isEmpty()){
            path = "/";
        }
        return path;
    }

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public String getParameter(String name, String defaultValue) {
        String value = getParameter(name);
        if(value == null){
            value = defaultValue;
        }
        return value;
    }

    @Override
    public String getPathParameter(String name) {
        return pathParameters.get(name);
    }

    @Override
    public void setAttribute(String key, Object value) {
        request.setAttribute(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return request.getAttribute(key);
    }

    @Override
    public InputStream getBody() throws IOException {
        return request.getInputStream();
    }
    
}
