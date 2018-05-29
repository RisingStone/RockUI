package com.risingstone.risingstoneui.settings;

import com.risingstone.risingstoneui.xml.XmlAnnotation;
import com.risingstone.risingstoneui.xml.XmlMapperComponent;
import com.risingstone.risingstoneui.xml.XmlNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

public class Window extends XmlMapperComponent {

    @XmlAnnotation()
    public long WindowId;
    //Window Tag
    @XmlAnnotation()
    public String Tag = "main";
    //Height for window
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public int Height = 600;
    //Width for window
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public int Width = 800;
    //Window position x
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public String PosX = "center";
    //Window position y
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public String PosY = "center";
    //Title of window
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public String Title = "RockUI";
    //Monitor for window
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public long PrimaryMonitor = glfwGetPrimaryMonitor();
    //Preferred monitor for window
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public long PreferredMonitor = -1;
    //Refresh rate for window
    @XmlAnnotation(type = XmlAnnotation.Type.Tag)
    public int RefreshRate = 30;

    @XmlAnnotation(type = XmlAnnotation.Type.Tag, required = false)
    public List<Window> childWindows = new ArrayList<>();

    public Window() {
    }

    public Window(long windowId, int height, int width, String posX, String posY, String title) {
        WindowId = windowId;
        Height = height;
        Width = width;
        PosX = posX;
        PosY = posY;
        Title = title;
    }

    public Window(long windowId, int height, int width, String posX, String posY, String title, String tag, long primaryMonitor, long preferredMonitor, int refreshRate, List<Window> childWindows) {
        WindowId = windowId;
        Height = height;
        Width = width;
        PosX = posX;
        PosY = posY;
        Title = title;
        Tag = tag;
        PrimaryMonitor = primaryMonitor;
        PreferredMonitor = preferredMonitor;
        RefreshRate = refreshRate;
        this.childWindows = childWindows;
    }

    @Override
    public String toString() {
        return "Window{" +
                "WindowId=" + WindowId +
                ", Height=" + Height +
                ", Width=" + Width +
                ", PosX='" + PosX + '\'' +
                ", PosY='" + PosY + '\'' +
                ", Title='" + Title + '\'' +
                ", Tag='" + Tag + '\'' +
                ", PrimaryMonitor=" + PrimaryMonitor +
                ", PreferredMonitor=" + PreferredMonitor +
                ", RefreshRate=" + RefreshRate +
                ", childWindows=" + childWindows +
                '}';
    }


    @Override
    public Object StringToValue(Field field, XmlNode node) {
        return null;
    }
}
