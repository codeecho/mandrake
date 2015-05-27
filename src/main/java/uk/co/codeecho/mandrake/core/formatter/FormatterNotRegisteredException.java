package uk.co.codeecho.mandrake.core.formatter;

public class FormatterNotRegisteredException extends RuntimeException{

    public FormatterNotRegisteredException(String type) {
        super("No formatter has been registered for " + type);
    }

}
