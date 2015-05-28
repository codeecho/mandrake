package uk.co.codeecho.mandrake.core.controller.impl;

import java.io.InputStream;
import java.util.Map;

public class ClasspathAssetController extends AssetController {

    public ClasspathAssetController() {
    }

    public ClasspathAssetController(Map<String, String> contentTypes) {
        super(contentTypes);
    }

    @Override
    protected InputStream getAssetStream(String path) throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("META-INF/resources" + path);
        return stream;
    }

}
