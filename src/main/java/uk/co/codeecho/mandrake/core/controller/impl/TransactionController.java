package uk.co.codeecho.mandrake.core.controller.impl;

import biz.devspot.entity.framework.core.EntityManagerFactory;
import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.Controller;
import uk.co.codeecho.mandrake.core.controller.ControllerChain;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class TransactionController implements Controller{

    @Override
    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception {
        EntityManagerFactory.getManager().openTransaction();
        Response result = chain.advance();
        EntityManagerFactory.getManager().commitTransaction();
        return result;
    }

}
