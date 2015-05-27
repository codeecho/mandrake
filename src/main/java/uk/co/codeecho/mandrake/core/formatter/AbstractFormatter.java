package uk.co.codeecho.mandrake.core.formatter;

import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.ControllerChain;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public abstract class AbstractFormatter<B, R> implements Formatter{

    private String contentType;

    public AbstractFormatter(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public Response handle(Request request, Response response, Map<String, Object> model, ControllerChain chain) throws Exception {
        R result = handle((B) response.getBody());
        response.setBody(model);
        response.setHeader("Content-Type", contentType);
        return chain.advance();
    }
    
    public abstract R handle(B body) throws FormatException;

}
