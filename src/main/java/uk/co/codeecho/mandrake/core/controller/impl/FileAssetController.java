package uk.co.codeecho.mandrake.core.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class FileAssetController extends AssetController {

    private File baseDirectory;

    public FileAssetController(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public FileAssetController(Map<String, String> contentTypes, File baseDirectory) {
        super(contentTypes);
        this.baseDirectory = baseDirectory;
    }

    @Override
    protected InputStream getAssetStream(String path) throws Exception {
        System.out.println(baseDirectory.getAbsolutePath());
        System.out.println(baseDirectory.exists());
        System.out.println("path = " + path);
        File file = new File(baseDirectory, path);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        return new FileInputStream(file);
    }

}
