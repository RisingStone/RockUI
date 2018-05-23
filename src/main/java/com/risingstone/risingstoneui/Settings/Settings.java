package com.risingstone.risingstoneui.Settings;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

public class Settings {

    public static class WindowSetting{

        public long windowId;
        //Height for window
        public int windowHeight = 600;
        //Width for window
        public int windowWidth = 800;
        //Title of window
        public String windowTitle = "RockUI";
        //Monitor for window
        public long primaryMonitor = glfwGetPrimaryMonitor();
        //Preferred monitor for window
        public long preferredMonitor = -1;
        //Refresh rate for window
        public int refreshRate = 30;

        public WindowSetting() {
        }

        public WindowSetting(long windowId, int windowHeight, int windowWidth, String windowTitle, long primaryMonitor, long preferredMonitor, int refreshRate) {
            this.windowId = windowId;
            this.windowHeight = windowHeight;
            this.windowWidth = windowWidth;
            this.windowTitle = windowTitle;
            this.primaryMonitor = primaryMonitor;
            this.preferredMonitor = preferredMonitor;
            this.refreshRate = refreshRate;
        }

        @Override
        public String toString() {
            return "WindowSettings{" +
                    "windowId=" + windowId +
                    ", windowHeight=" + windowHeight +
                    ", windowWidth=" + windowWidth +
                    ", windowTitle='" + windowTitle + '\'' +
                    ", primaryMonitor=" + primaryMonitor +
                    ", preferredMonitor=" + preferredMonitor +
                    '}';
        }
    }

    //Default settings
    public static WindowSetting defaultWindow = new WindowSetting(
            12345l,
            800,
            600,
            "RockUI",
            glfwGetPrimaryMonitor(),
            1,
            30);

    //Settings per window
    public static WindowSetting[] windows;

}
