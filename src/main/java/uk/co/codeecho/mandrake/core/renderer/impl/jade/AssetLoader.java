package uk.co.codeecho.mandrake.core.renderer.impl.jade;

import de.neuland.jade4j.filter.CachingFilter;
import java.util.Map;
import org.webjars.WebJarAssetLocator;

public class AssetLoader extends CachingFilter {

    private WebJarAssetLocator webjarAssetLoader = new WebJarAssetLocator();

    @Override
    protected String convert(String assets, Map<String, Object> map) {
        StringBuilder result = new StringBuilder();
        for (String asset : assets.split("\n")) {
            String file = asset;
            if (asset.startsWith("webjar:")) {
                file = webjarAssetLoader.getFullPath(asset.substring(7));
                file = file.replace("META-INF/resources/", "");
            }else{
                file = "assets/" + file;
            }
            if(file.endsWith(".js")){
                result.append("<script type=\"text/javascript\" src=\"").append(file).append("\"> </script>");
            }else if(file.endsWith(".css")){
                result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(file).append("\">");
            }
        }
        return result.toString();
    }

}
