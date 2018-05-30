package com.risingstone.risingstoneui.settings;

import com.risingstone.risingstoneui.xml.XmlAnnotation;
import com.risingstone.risingstoneui.xml.XmlMapperComponent;

import java.lang.reflect.Field;
import java.util.List;


public class Windows extends XmlMapperComponent {

    @XmlAnnotation(key = "AppName")
    public String appName;

    @XmlAnnotation(key = "Author")
    public String author;

    @XmlAnnotation(key = "URL")
    public String url;

    @XmlAnnotation(key = "XMLMapper")
    public String mapper;

    @XmlAnnotation(key = "Version")
    public int version;

    @XmlAnnotation(key = "Window", type = XmlAnnotation.Type.Tag, required = false)
    public List<Window> windows;

    public Windows() {
        super();
    }

    public Windows(String appName, String author, String url, String mapper, int version, List<Window> windows) {
        this.appName = appName;
        this.author = author;
        this.url = url;
        this.mapper = mapper;
        this.version = version;
        this.windows = windows;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Window> getWindows() {
        return windows;
    }

    public void setWindows(List<Window> windows) {
        this.windows = windows;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Windows{");
        sb.append("appName='").append(appName).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", mapper='").append(mapper).append('\'');
        sb.append(", version=").append(version);
        sb.append(", windows=").append(windows);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Object StringToValue(Field field, String value) {
        if(field.getName().equalsIgnoreCase("version")){
            return Integer.parseInt(value);
        }
        return new Object();
    }
}