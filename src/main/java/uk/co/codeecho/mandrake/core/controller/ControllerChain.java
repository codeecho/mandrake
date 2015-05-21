package uk.co.codeecho.mandrake.core.controller;

import uk.co.codeecho.mandrake.core.request.Response;


public interface ControllerChain {

    public Response advance() throws Exception;
    
    public Response advance(Response response) throws Exception;
    
}
