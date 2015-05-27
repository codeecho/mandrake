package uk.co.codeecho.mandrake.core.renderer;

public class RendererNotRegisteredException extends RuntimeException{

    public RendererNotRegisteredException(String template) {
        super("No renderer has been registered for file " + template);
    }

}
