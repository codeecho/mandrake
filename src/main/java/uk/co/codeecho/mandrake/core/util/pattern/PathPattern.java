package uk.co.codeecho.mandrake.core.util.pattern;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathPattern {

    private String path;
    private Pattern pattern;
    private List<String> parameters;

    public PathPattern(String path) {
        this.path = path;
        String regex = path.replace("{", "(?<").replace("}", ">.*)");
        pattern = Pattern.compile(regex);
        parameters = pattern.groupNames();
    }

    public String getPath() {
        return path;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public PathPatternMatchResult test(String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            Map<String, String> pathParams = new HashMap<String, String>();
            for (String pathParamName : parameters) {
                pathParams.put(pathParamName, matcher.group(pathParamName));
            }
            return new PathPatternMatchResult(true, pathParams);
        } else {
            return new PathPatternMatchResult(false, new HashMap<String, String>());
        }
    }

}
