package com.risingstone.risingstoneui.settings;

import com.risingstone.risingstoneui.xml.XmlAnnotation;
import com.risingstone.risingstoneui.xml.XmlMapperComponent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

public class Window extends XmlMapperComponent {

    @XmlAnnotation(key="WindowId")
    public long WindowId;
    //Window Tag
    @XmlAnnotation(key="Tag")
    public String Tag = "main";
    //Height for window
    @XmlAnnotation(key = "Height")
    public int Height = 600;
    //Width for window
    @XmlAnnotation(key = "Width")
    public int Width = 800;
    //Window position x
    @XmlAnnotation(key = "PosX")
    public String PosX = "center";
    //Window position y
    @XmlAnnotation(key = "PosY")
    public String PosY = "center";
    //Title of window
    @XmlAnnotation(key = "Title")
    public String Title = "RockUI";
    //Monitor for window
    @XmlAnnotation(key = "PrimaryMonitor")
    public long PrimaryMonitor = glfwGetPrimaryMonitor();
    //Preferred monitor for window
    @XmlAnnotation(key = "PreferredMonitor")
    public long PreferredMonitor = -1;
    //Refresh rate for window
    @XmlAnnotation(key = "RefreshRate")
    public int RefreshRate = 30;

    @XmlAnnotation(key = "Window", type = XmlAnnotation.Type.Tag, required = false)
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

    public Window(long windowId, String tag, int height, int width, String posX, String posY, String title, long primaryMonitor, long preferredMonitor, int refreshRate, List<Window> childWindows) {
        WindowId = windowId;
        Tag = tag;
        Height = height;
        Width = width;
        PosX = posX;
        PosY = posY;
        Title = title;
        PrimaryMonitor = primaryMonitor;
        PreferredMonitor = preferredMonitor;
        RefreshRate = refreshRate;
        this.childWindows = childWindows;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Window{");
        sb.append("WindowId=").append(WindowId);
        sb.append(", Tag='").append(Tag).append('\'');
        sb.append(", Height=").append(Height);
        sb.append(", Width=").append(Width);
        sb.append(", PosX='").append(PosX).append('\'');
        sb.append(", PosY='").append(PosY).append('\'');
        sb.append(", Title='").append(Title).append('\'');
        sb.append(", PrimaryMonitor=").append(PrimaryMonitor);
        sb.append(", PreferredMonitor=").append(PreferredMonitor);
        sb.append(", RefreshRate=").append(RefreshRate);
        sb.append(", childWindows=").append(childWindows);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Object StringToValue(Field field, String attributeValue) {
        if(field.getName().equalsIgnoreCase("PrimaryMonitor")){
            if(attributeValue.equalsIgnoreCase("default")){
                return glfwGetPrimaryMonitor();
            }else{
                return Long.parseLong(attributeValue);
            }
        }else if(field.getName().equalsIgnoreCase("PreferredMonitor")){
            return Long.parseLong(attributeValue);
        }else if(field.getName().equalsIgnoreCase("RefreshRate")){
            return Integer.parseInt(attributeValue);
        }else if(field.getName().equalsIgnoreCase("Height")){
            return Integer.parseInt(attributeValue);
        }else if(field.getName().equalsIgnoreCase("Width")){
            return Integer.parseInt(attributeValue);
        }else if(field.getName().equalsIgnoreCase("RefreshRate")) {
            return Integer.parseInt(attributeValue);
        }
        return new Object();
    }
}
