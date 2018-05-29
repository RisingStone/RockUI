package com.risingstone.risingstoneui.xml;

public enum XmlClassPaths {
    rockui("com.risingstone.risingstoneui.ui"),
    extension("com.risingstone.risingstoneui.ui.extension"),
    settings("com.risingstone.risingstoneui.settings");

    String classPath;

    XmlClassPaths(String classpath) {
        this.classPath = classpath;
    }

    public String getClassPath() {
        return classPath;
    }
}
