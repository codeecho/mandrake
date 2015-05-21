package uk.co.codeecho.mandrake.core.formatter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.codeecho.mandrake.core.formatter.AbstractFormatter;
import uk.co.codeecho.mandrake.core.formatter.FormatException;

public class JSONFormatter extends AbstractFormatter<Object, String>{

    private ObjectMapper mapper = new ObjectMapper();

    public JSONFormatter() {
        super("application/json");
    }

    public JSONFormatter(ObjectMapper mapper) {
        this();
        this.mapper = mapper;
    }

    @Override
    public String handle(Object body) throws FormatException {
        try {
            return mapper.writeValueAsString(body);
        } catch (JsonProcessingException ex) {
            throw new FormatException(ex);
        }
    }
 
}
