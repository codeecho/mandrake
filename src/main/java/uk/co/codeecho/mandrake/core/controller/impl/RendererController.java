package uk.co.codeecho.mandrake.core.controller.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import uk.co.codeecho.mandrake.core.controller.AbstractController;
import uk.co.codeecho.mandrake.core.renderer.Renderer;
import uk.co.codeecho.mandrake.core.renderer.RendererException;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public class RendererController extends AbstractController {

    private final Renderer renderer;
    private final String template;

    public RendererController(Renderer renderer, String template) {
        this.renderer = renderer;
        this.template = template;
    }

    @Override
    protected void handle(Request request, Response response, Map<String, Object> model) throws IOException, RendererException {
        response.setBody(renderer.render(template, model));
    }

}
