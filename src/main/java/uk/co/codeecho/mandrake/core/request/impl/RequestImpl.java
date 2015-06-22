package uk.co.codeecho.mandrake.core.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.request.Request;

public class RequestImpl implements Request{

    private HttpMethod method;
    private String path;
    private String query;
    private Map<String, String> parameters = new HashMap<String, String>();
    private Map<String, String> pathParameters = new HashMap<String, String>();
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private InputStream body;

    public RequestImpl(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public RequestImpl(HttpMethod method, String path, String query) {
        this.method = method;
        this.path = path;
        this.query = query;
    }
    
    public RequestImpl(HttpMethod method, String path, String query, Map<String, String> parameters) {
        this.method = method;
        this.path = path;
        this.query = query;
        this.parameters = parameters;
    }

    public RequestImpl(HttpMethod method, String path, InputStream body) {
        this.method = method;
        this.path = path;
        this.body = body;
    }

    public RequestImpl(HttpMethod method, String path, Map<String, String> parameters, InputStream body) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.body = body;
    }
    
    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public String getParameter(String name, String defaultValue) {
        if(parameters.containsKey(name)){
            return parameters.get(name);
        }else{
            return defaultValue;
        }
    }

    @Override
    public String getPathParameter(String name) {
        return pathParameters.get(name);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }
    
}
