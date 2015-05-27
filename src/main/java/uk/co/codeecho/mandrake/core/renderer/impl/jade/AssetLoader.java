package uk.co.codeecho.mandrake.core.renderer.impl.jade;

import de.neuland.jade4j.filter.CachingFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.webjars.WebJarAssetLocator;

public class AssetLoader extends CachingFilter {

    private WebJarAssetLocator webjarAssetLoader = new WebJarAssetLocator();

    @Override
    protected String convert(String assets, Map<String, Object> map) {
        StringBuilder result = new StringBuilder();
        for (String asset : assets.split("\n")) {
            String file = asset;
            if (asset.startsWith("webjar:")) {
                String[] tokens = asset.split(":");
                if(tokens.length!=3){
                    throw new IllegalArgumentException("Webjar paths should be in the form webjar:{jar}:{path}");
                }
                String jar = tokens[1];
                String path = tokens[2];
                Properties properties = new Properties();
                InputStream stream = getClass().getClassLoader().getResourceAsStream("META-INF/maven/org.webjars/" + jar + "/pom.properties");
                if(stream == null){
                    throw new IllegalArgumentException("Could not find webjar for " + jar);
                }
                try {
                    properties.load(stream);
                    String version = properties.getProperty("version");
                    file = "webjars/" + jar + "/" + version + "/" + path;
                } catch (IOException ex) {
                    throw new IllegalArgumentException(ex);
                } finally {
                    try {
                        stream.close();
                    } catch (IOException ex) {

                    }
                }
                //file = webjarAssetLoader.getFullPath(asset.substring(7));
                //file = file.replace("META-INF/resources/", "");
            } else {
                file = "assets/" + file;
            }
            if (file.endsWith(".js")) {
                result.append("<script type=\"text/javascript\" src=\"").append(file).append("\"> </script>");
            } else if (file.endsWith(".css")) {
                result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(file).append("\">");
            }
        }
        return result.toString();
    }

}
