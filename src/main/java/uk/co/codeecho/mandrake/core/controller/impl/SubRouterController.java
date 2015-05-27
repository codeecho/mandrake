package uk.co.codeecho.mandrake.core.controller.impl;

import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.Controller;
import uk.co.codeecho.mandrake.core.controller.ControllerChain;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;
import uk.co.codeecho.mandrake.core.router.Router;

public class SubRouterController implements Controller{

    private Router router;

    public SubRouterController(Router router) {
        this.router = router;
    }

    @Override
    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception {
        return chain.advance(router.handle(request));
    }

}
