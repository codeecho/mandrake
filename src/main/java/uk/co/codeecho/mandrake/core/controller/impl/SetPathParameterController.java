package uk.co.codeecho.mandrake.core.controller.impl;

import java.util.HashMap;
import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.Controller;
import uk.co.codeecho.mandrake.core.controller.ControllerChain;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;
import uk.co.codeecho.mandrake.core.request.impl.WrappedRequest;

public class SetPathParameterController implements Controller {

    private String paramName;
    private String paramValue;

    public SetPathParameterController(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    @Override
    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception {
        Map<String, String> pathParameters = new HashMap<String, String>();
        pathParameters.put(paramName, paramValue);
        return chain.advance(new WrappedRequest(request, pathParameters));
    }

}
