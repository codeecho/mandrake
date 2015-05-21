package uk.co.codeecho.mandrake.core.request;

public class ResponseBuilder {

    private Response response = new Response();
    
    public ResponseBuilder status(int status){
        response.setStatus(status);
        return this;
    }
    
    public ResponseBuilder body(Object body){
        response.setBody(body);
        return this;
    }
    
    public ResponseBuilder header(String name, String value){
        response.setHeader(name, value);
        return this;
    }
    
    public Response build(){
        return response;
    }
    
}
