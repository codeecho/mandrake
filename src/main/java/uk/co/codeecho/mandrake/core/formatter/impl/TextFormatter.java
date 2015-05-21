package uk.co.codeecho.mandrake.core.formatter.impl;

import uk.co.codeecho.mandrake.core.formatter.AbstractFormatter;

public class TextFormatter extends AbstractFormatter<Object, String> {

    public TextFormatter() {
        super("text/plain");
    }

    @Override
    public String handle(Object body) {
        return body.toString();
    }

}
