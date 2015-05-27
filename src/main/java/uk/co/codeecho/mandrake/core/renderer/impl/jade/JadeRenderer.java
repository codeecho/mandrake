package uk.co.codeecho.mandrake.core.renderer.impl.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeException;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import uk.co.codeecho.mandrake.core.renderer.Renderer;
import uk.co.codeecho.mandrake.core.renderer.RendererException;

public class JadeRenderer implements Renderer {

    private final JadeConfiguration jadeConfig;
    private final String baseDir;

    public JadeRenderer() {
        this("views/");
    }

    public JadeRenderer(String baseDir) {
        jadeConfig = new JadeConfiguration();
        jadeConfig.setTemplateLoader(new ClasspathTemplateLoader());
        jadeConfig.setCaching(false);
        jadeConfig.setPrettyPrint(false);
        Map<String, Object> helpers = new HashMap<String, Object>();
        jadeConfig.setSharedVariables(helpers);
        jadeConfig.setFilter("assets", new AssetLoader());
        this.baseDir = baseDir;
    }

    public JadeRenderer(JadeConfiguration jadeConfig) {
        this(jadeConfig, "views/");
    }

    public JadeRenderer(JadeConfiguration jadeConfig, String baseDir) {
        this.jadeConfig = jadeConfig;
        this.baseDir = baseDir;
    }

    @Override
    public String render(String template, Map<String, Object> model) throws IOException, RendererException {
        try {
            JadeTemplate jadeTemplate = jadeConfig.getTemplate(baseDir + template);
            String html = jadeConfig.renderTemplate(jadeTemplate, model);
            return html;
        } catch (JadeException ex) {
            throw new RendererException(ex);
        }
    }

}
