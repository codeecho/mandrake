package uk.co.codeecho.mandrake.core.util.pattern;

import java.util.Map;

public class PathPatternMatchResult {

    private boolean matches;
    private Map<String, String> parameters;

    public PathPatternMatchResult(boolean matches, Map<String, String> parameters) {
        this.matches = matches;
        this.parameters = parameters;
    }

    public boolean matches() {
        return matches;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
    
}
