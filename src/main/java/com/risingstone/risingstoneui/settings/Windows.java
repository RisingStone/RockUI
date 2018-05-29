package com.risingstone.risingstoneui.settings;

import com.risingstone.risingstoneui.xml.XmlAnnotation;
import com.risingstone.risingstoneui.xml.XmlMapperComponent;
import com.risingstone.risingstoneui.xml.XmlNode;

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

    @XmlAnnotation(key = "Window", required = false)
    public List<Window> windows;


    public Windows() {

    }

    public Windows(String appName, String author, String url, String mapper, List<Window> windows) {
        this.appName = appName;
        this.author = author;
        this.url = url;
        this.mapper = mapper;
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

    public List<Window> getWindows() {
        return windows;
    }

    public void setWindows(List<Window> windows) {
        this.windows = windows;
    }

    @Override
    public String toString() {
        return "Windows{" +
                "appName='" + appName + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", mapper='" + mapper + '\'' +
                ", windows=" + windows +
                '}';
    }

    @Override
    public Object StringToValue(Field field, XmlNode node) {
        return null;
    }
}