package com.risingstone.risingstoneui.Settings;

import com.risingstone.risingstoneui.Xml.XmlMapper;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

public class WindowSettings extends XmlMapper {

    //TODO figure out how to use center and 800 in the same feild...
    // maybe use something where I attempt to get a value in a switch or enum or something and then fall back to pixel values
    //I decided to use an interface that maps values for each xml type. example you want to map center for windows then call the map
    //from the class in the xml

    public long windowId;
    //Height for window
    public int windowHeight = 600;
    //Width for window
    public int windowWidth = 800;
    //Window position x
    public String windowPosX = "center";
    //Window position y
    public String windowPosY = "center";
    //Title of window
    public String windowTitle = "RockUI";
    //Window Tag
    public String windowTag = "main";
    //Monitor for window
    public long primaryMonitor = glfwGetPrimaryMonitor();
    //Preferred monitor for window
    public long preferredMonitor = -1;
    //Refresh rate for window
    public int refreshRate = 30;

    public WindowSettings() {

    }

    public WindowSettings(long windowId, int windowHeight, int windowWidth, String windowPosX, String windowPosY, String windowTitle, String windowTag, long primaryMonitor, long preferredMonitor, int refreshRate) {
        this.windowId = windowId;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.windowPosX = windowPosX;
        this.windowPosY = windowPosY;
        this.windowTitle = windowTitle;
        this.windowTag = windowTag;
        this.primaryMonitor = primaryMonitor;
        this.preferredMonitor = preferredMonitor;
        this.refreshRate = refreshRate;
    }

    @Override
    public String toString() {
        return "WindowSetting{" +
                "windowId=" + windowId +
                ", windowHeight=" + windowHeight +
                ", windowWidth=" + windowWidth +
                ", windowPosX='" + windowPosX + '\'' +
                ", windowPosY='" + windowPosY + '\'' +
                ", windowTitle='" + windowTitle + '\'' +
                ", windowTag='" + windowTag + '\'' +
                ", primaryMonitor=" + primaryMonitor +
                ", preferredMonitor=" + preferredMonitor +
                ", refreshRate=" + refreshRate +
                '}';
    }

    @Override
    public int StringToValue() {
        return 0;
    }
}