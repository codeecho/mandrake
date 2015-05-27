package uk.co.codeecho.mandrake.core.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class ControllerChainImpl implements ControllerChain {

    private Request request;
    private Map<String, Object> model;
    private LinkedList<Controller> controllers;

    private Response response = Response.ok().build();

    public ControllerChainImpl(Request request, LinkedList<Controller> controllers) {
        this.request = request;
        this.model = new HashMap<String, Object>();
        this.controllers = new LinkedList<Controller>(controllers);
    }

    @Override
    public Response advance() throws Exception {
        if (controllers.isEmpty()) {
            return response;
        }
        Controller controller = controllers.pop();
        return controller.handle(request, response, model, this);
    }

    @Override
    public Response advance(Response response) throws Exception {
        if (response != null) {
            this.response = response;
        }
        return advance();
    }

}
