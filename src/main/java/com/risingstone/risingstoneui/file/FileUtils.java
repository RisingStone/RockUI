package com.risingstone.risingstoneui.file;

import java.io.File;

public class FileUtils {
    public static File getfileFromResources(String name){
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        return file;
    }
}
