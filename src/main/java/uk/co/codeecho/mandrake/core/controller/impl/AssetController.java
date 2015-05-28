package uk.co.codeecho.mandrake.core.controller.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.codeecho.mandrake.core.controller.AbstractController;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;

public abstract class AssetController extends AbstractController{

    public static Map<String, String> CONTENT_TYPES = new HashMap<String, String>();
    
    {{
        CONTENT_TYPES.put(".js", "text/javascript");
        CONTENT_TYPES.put(".css", "text/css");
        CONTENT_TYPES.put(".html", "text/html");
        CONTENT_TYPES.put(".json", "application/json");
    }}
    
    private Map<String, String> contentTypes;

    public AssetController() {
        this(new HashMap<String, String>());
    }

    public AssetController(Map<String, String> contentTypes) {
        this.contentTypes = new HashMap<String, String>();
        this.contentTypes.putAll(CONTENT_TYPES);
        this.contentTypes.putAll(contentTypes);
    }
    
    public void setContentType(String extension, String contentType){
        contentTypes.put(extension, contentType);
    }
    
    @Override
    protected void handle(Request request, Response response, Map<String, Object> model) throws Exception {
        String path = request.getPath();
        InputStream stream = getAssetStream(path);
        if(stream == null){
            exit(Response.status(404).build());
        }else{
            for(Entry<String, String> entry: contentTypes.entrySet()){
                if(path.endsWith(entry.getKey())){
                    response.setContentType(entry.getValue());
                    break;
                }
            }
            response.setBody(stream);
        }
    }
    
    protected abstract InputStream getAssetStream(String path) throws Exception;
    
}
