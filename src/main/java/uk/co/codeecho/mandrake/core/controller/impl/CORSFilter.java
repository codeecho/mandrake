package uk.co.codeecho.mandrake.core.controller.impl;

import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.AfterChainController;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class CORSFilter extends AfterChainController{

    @Override
    public void after(Request request, Response response, Map<String, Object> model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "accept, content-type");
    }

}
