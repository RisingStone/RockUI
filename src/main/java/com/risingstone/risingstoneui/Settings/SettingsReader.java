package com.risingstone.risingstoneui.Settings;


import com.risingstone.risingstoneui.File.FileUtils;
import com.risingstone.risingstoneui.Xml.XmlNode;
import com.risingstone.risingstoneui.Xml.XmlReader;
import com.risingstone.risingstoneui.Xml.XmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

public class SettingsReader {

    public static Future<WindowSettings> getDefaultWindowSettings(){
        FutureTask<WindowSettings> settingsFutureTask = new FutureTask<>(() -> {
            XmlNode node = new XmlReader().readXMLFile(FileUtils.getfileFromResources("window_settings.xml")).get();
            return buildDefaultWindowSettings(node);
        });

        settingsFutureTask.run();
        return settingsFutureTask;
    }

    public static Future<List<WindowSettings>> getWindowSettings(){
        FutureTask<List<WindowSettings>> settingsFutureTask = new FutureTask<>(() -> {
            XmlNode node = new XmlReader().readXMLFile(FileUtils.getfileFromResources("window_settings.xml")).get();
            List<WindowSettings> list = new ArrayList<>();
            List<XmlNode> windowNodes = XmlUtils.getListOfChildrenByNameDeep("Window", node);
            for(XmlNode windowNode : windowNodes){
                list.add(buildWindowSetting(windowNode));
            }
            return list;
        });

        settingsFutureTask.run();
        return settingsFutureTask;
    }

    private static WindowSettings buildDefaultWindowSettings(XmlNode node) {
        XmlNode defaultSettingsNode = XmlUtils.getChildByNameDeep("Default", node);
        return buildWindowSetting(defaultSettingsNode);
    }

    private static WindowSettings buildWindowSetting(XmlNode node) {
        WindowSettings windowSettings = new WindowSettings();

        if(windowSettings != null) {
            //ID and Tag are in the attributes
            windowSettings.windowId = Long.parseLong(node.getAttributes().get("id"));
            windowSettings.windowTag = node.getAttributes().get("title");

            //Grab child names of Title and height and width and get vals
            windowSettings.windowTitle = XmlUtils.getChildByNameShallow("Title", node).getVal();
            windowSettings.windowHeight = Integer.parseInt(XmlUtils.getChildByNameShallow("Height", node).getVal());
            windowSettings.windowWidth = Integer.parseInt(XmlUtils.getChildByNameShallow("Width", node).getVal());

            //Do some massaging to allow default to be used
            String primaryMonitorString = XmlUtils.getChildByNameShallow("PrimaryMonitor", node).getVal();
            long primaryMonitor = glfwGetPrimaryMonitor();
            if (!primaryMonitorString.equalsIgnoreCase("default")) {
                primaryMonitor = Long.parseLong(primaryMonitorString);
            }
            windowSettings.primaryMonitor = primaryMonitor;

            //Do some massaging to allow default to be used
            String preferredMonitorString = XmlUtils.getChildByNameShallow("PreferredMonitor", node).getVal();
            long preferredMonitor = glfwGetPrimaryMonitor();
            if (!preferredMonitorString.equalsIgnoreCase("default")) {
                preferredMonitor = Long.parseLong(preferredMonitorString);
            }
            windowSettings.preferredMonitor = preferredMonitor;

            //Grab child name of refresh rate and grab the val
            windowSettings.refreshRate = Integer.parseInt(XmlUtils.getChildByNameShallow("RefreshRate", node).getVal());
        }
        return windowSettings;
    }
}
