package uk.co.codeecho.mandrake.core.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private int status = 200;
    private Object body;
    private Map<String, String> headers = new HashMap<String, String>();

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
    
    public void setHeader(String name, String value){
        headers.put(name, value);
    }
    
    public String getHeader(String name){
        return headers.get(name);
    }
    
    public static ResponseBuilder ok(){
        return new ResponseBuilder().status(200);
    }
    
    public static ResponseBuilder ok(Object body){
        return new ResponseBuilder().status(200).body(body);
    }
    
    public static ResponseBuilder status(int status){
        return new ResponseBuilder().status(status);
    }
    
}
