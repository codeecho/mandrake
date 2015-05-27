package uk.co.codeecho.mandrake.core.controller.impl;

import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.AbstractController;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class GetEntityController extends AbstractController{

    @Override
    protected void handle(Request request, Response response, Map<String, Object> model) throws Exception {
        DataBackedObject entity = EntityManagerFactory.getManager().findById(request.getParameter("id"));
        if(entity == null){
            exit(Response.status(404).build());
        }else{
            response.setBody(entity);
        }
    }

}
