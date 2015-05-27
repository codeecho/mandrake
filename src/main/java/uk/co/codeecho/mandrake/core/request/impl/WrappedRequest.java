package uk.co.codeecho.mandrake.core.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.request.Request;

public class WrappedRequest implements Request{

    private Request request;
    private Map<String, String> pathParameters;

    public WrappedRequest(Request request, Map<String, String> pathParameters) {
        this.request = request;
        this.pathParameters = pathParameters;
    }

    @Override
    public HttpMethod getMethod() {
        return request.getMethod();
    }

    @Override
    public String getPath() {
        return request.getPath();
    }

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public String getParameter(String name, String defaultValue) {
        return request.getParameter(name, defaultValue);
    }

    @Override
    public String getPathParameter(String name) {
        if(pathParameters.containsKey(name)){
            return pathParameters.get(name);
        }
        return request.getPathParameter(name);
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
        return request.getBody();
    }
    
}
