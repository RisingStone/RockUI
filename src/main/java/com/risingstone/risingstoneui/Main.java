package com.risingstone.risingstoneui;

import com.risingstone.risingstoneui.File.FileUtils;
import com.risingstone.risingstoneui.Settings.SettingsReader;
import com.risingstone.risingstoneui.Settings.WindowSettings;
import com.risingstone.risingstoneui.Xml.XmlNode;
import com.risingstone.risingstoneui.Xml.XmlReader;
import com.risingstone.risingstoneui.command.KeyHandler;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    // The window handle
    private long window;
    private List<Long> windows;


    WindowSettings defaultWindow;
    List<WindowSettings> windowSettings;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        //Load settings
        try {
            this.defaultWindow = SettingsReader.getDefaultWindowSettings().get();
            this.windowSettings = SettingsReader.getWindowSettings().get();
            System.out.println(this.defaultWindow);
            System.out.println(this.windows);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(this.defaultWindow.windowWidth, this.defaultWindow.windowHeight, this.defaultWindow.windowTitle, NULL, NULL);
        if ( window == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        showWindow(window, this.defaultWindow);

        for(WindowSettings window : this.windowSettings){
            System.out.println("trying to make new window: " + window.windowTitle);
            window.windowId = glfwCreateWindow(window.windowWidth, window.windowHeight, window.windowTitle, NULL, NULL);
            if ( window.windowId == NULL ) {
                throw new RuntimeException("Failed to create the GLFW window");
            }
            showWindow(window.windowId, window);
        }
    }

    private void showWindow(long window, WindowSettings setting) {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, new KeyHandler());


        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //Get all connected monitors
            PointerBuffer pb = glfwGetMonitors();
            while(pb.hasRemaining()){
                long monitor = pb.get();
                String name = glfwGetMonitorName(monitor);
                System.out.println(String.format("Monitor Address: %s monitor name: %s", monitor, name));
            }

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        XmlReader reader = new XmlReader();
        Future<XmlNode> xmlReaderFuture = reader.readXMLFile(FileUtils.getfileFromResources("window_settings.xml"));
        try {
            System.out.println(xmlReaderFuture.get(1000l, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        new Main().run();
    }

}