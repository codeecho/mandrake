package uk.co.codeecho.mandrake.core.controller;

import java.util.Map;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public abstract class AfterChainController extends AbstractController{

    @Override
    protected void handle(Request request, Response response, Map<String, Object> model) {
        
    }

    @Override
    public abstract void after(Request request, Response response, Map<String, Object> model);

}
