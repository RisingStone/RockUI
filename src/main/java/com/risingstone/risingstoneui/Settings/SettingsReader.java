package com.risingstone.risingstoneui.Settings;


import com.risingstone.risingstoneui.File.FileUtils;
import com.risingstone.risingstoneui.Xml.XMLNode;
import com.risingstone.risingstoneui.Xml.XmlReader;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

public class SettingsReader {

    public static Future<WindowSettings.WindowSetting> getDefaultWindowSettings(){
        FutureTask<WindowSettings.WindowSetting> settingsFutureTask = new FutureTask<>(() -> {
            XMLNode node = new XmlReader().readXMLFile(FileUtils.getfileFromResources("window_settings.xml")).get();
            return buildDefaultWindowSettings(node);
        });

        settingsFutureTask.run();
        return settingsFutureTask;
    }

    private static WindowSettings.WindowSetting buildDefaultWindowSettings(XMLNode node) {
        XMLNode defaultSettingsNode = XMLNode.getChildByNameDeep("Default", node);
        return buildWindowSetting(defaultSettingsNode);
    }

    private static WindowSettings.WindowSetting buildWindowSetting(XMLNode node) {
        WindowSettings.WindowSetting windowSettings = new WindowSettings.WindowSetting();

        if(windowSettings != null) {
            //ID and Title are in the attributes
            windowSettings.windowId = Long.parseLong(node.getAttributes().get("id"));
            windowSettings.windowTitle = node.getAttributes().get("name");

            //Grab child names of height and width and get vals
            windowSettings.windowHeight = Integer.parseInt(XMLNode.getChildByNameShallow("Height", node).getVal());
            windowSettings.windowWidth = Integer.parseInt(XMLNode.getChildByNameShallow("Width", node).getVal());

            //Do some massaging to allow default to be used
            String primaryMonitorString = XMLNode.getChildByNameShallow("PrimaryMonitor", node).getVal();
            long primaryMonitor = glfwGetPrimaryMonitor();
            if (!primaryMonitorString.equalsIgnoreCase("default")) {
                primaryMonitor = Long.parseLong(primaryMonitorString);
            }
            windowSettings.primaryMonitor = primaryMonitor;

            //Do some massaging to allow default to be used
            String preferredMonitorString = XMLNode.getChildByNameShallow("PreferredMonitor", node).getVal();
            long preferredMonitor = glfwGetPrimaryMonitor();
            if (!preferredMonitorString.equalsIgnoreCase("default")) {
                preferredMonitor = Long.parseLong(preferredMonitorString);
            }
            windowSettings.preferredMonitor = preferredMonitor;

            //Grab child name of refresh rate and grab the val
            windowSettings.refreshRate = Integer.parseInt(XMLNode.getChildByNameShallow("RefreshRate", node).getVal());
        }
        return windowSettings;
    }
}
