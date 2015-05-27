package uk.co.codeecho.mandrake.core.renderer;

import java.io.IOException;
import java.util.Map;

public interface Renderer {

    public String render(String template, Map<String, Object> model) throws IOException, RendererException;
    
}
