package uk.co.codeecho.mandrake.core.controller.impl;

import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.Controller;
import uk.co.codeecho.mandrake.core.controller.ControllerChain;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class ResponseController implements Controller {

    private Response response;

    public ResponseController(Response response) {
        this.response = response;
    }
    
    @Override
    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception {
        return chain.advance(this.response);
    }

}
