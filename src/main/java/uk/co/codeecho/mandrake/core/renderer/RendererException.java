package uk.co.codeecho.mandrake.core.renderer;

public class RendererException extends Exception{

    public RendererException(String message) {
        super(message);
    }

    public RendererException(String message, Throwable cause) {
        super(message, cause);
    }

    public RendererException(Throwable cause) {
        super(cause);
    }

}
