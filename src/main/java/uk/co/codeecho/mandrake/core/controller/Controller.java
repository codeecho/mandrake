package uk.co.codeecho.mandrake.core.controller;

import java.util.Map;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public interface Controller {

    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception;
    
}
