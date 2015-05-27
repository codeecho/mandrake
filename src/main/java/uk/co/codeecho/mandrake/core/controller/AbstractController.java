package uk.co.codeecho.mandrake.core.controller;

import java.util.Map;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public abstract class AbstractController implements Controller{

    @Override
    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception {
        try{
            handle(request, response, model);
            Response result = chain.advance();
            after(request, result, model);
            return result;
        }catch(ExitException ex){
            if(ex.getResponse()!=null){
                return ex.getResponse();
            }else{
                return response;
            }
        }
    }
    
    protected abstract void handle(Request request, Response response, Map<String, Object> model) throws Exception;
    
    public void after(Request request, Response response, Map<String, Object> model) throws Exception{
        
    }
    
    protected void exit(){
        throw new ExitException();
    }
    
    protected void exit(Response response){
        throw new ExitException(response);
    }
    
    private class ExitException extends RuntimeException{
        private Response response;

        public ExitException() {
        }

        public ExitException(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }
    }

}
