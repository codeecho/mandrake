package uk.co.codeecho.mandrake.core.request;

import java.io.IOException;
import java.io.InputStream;

public interface Request {

    public HttpMethod getMethod();
    
    public String getPath();
    
    public String getParameter(String name);
    
    public String getParameter(String name, String defaultValue);
    
    public String getPathParameter(String name);
    
    public void setAttribute(String key, Object value);
    
    public Object getAttribute(String key);
    
    public InputStream getBody() throws IOException;
    
}
