package uk.co.codeecho.mandrake.core.util.pattern;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPattern {

    private String path;
    private Pattern pattern;
    private Set<String> parameters;

    public PathPattern(String path) {
        this.path = path;
        String regex = path.replace("{", "(?<").replace("}", ">.*)");
        pattern = Pattern.compile(regex);
        parameters = getPathParameters(pattern);
    }

    public String getPath() {
        return path;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Set<String> getParameters() {
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

    private Set<String> getPathParameters(Pattern pattern) {
        try {
            Method namedGroupsMethod = Pattern.class.getDeclaredMethod("namedGroups");
            namedGroupsMethod.setAccessible(true);
            Map<String, Integer> namedGroups = (Map<String, Integer>) namedGroupsMethod.invoke(pattern);
            return namedGroups.keySet();
        } catch (IllegalAccessException ex) {
            return null;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (InvocationTargetException ex) {
            return null;
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (SecurityException ex) {
            return null;
        }
    }

}
