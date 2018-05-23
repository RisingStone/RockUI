package com.risingstone.risingstoneui.command;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.dyncall.DynCallback.dcbArgInt;
import static org.lwjgl.system.dyncall.DynCallback.dcbArgPointer;

public class KeyHandler implements GLFWKeyCallbackI {

    private Set<Integer> activeKeys = new HashSet<>();

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        System.out.println(String.format("Key Pressed: %d action: %d scancode: %d mods: #d", key, action, scancode, mods));
        if(action == GLFW_RELEASE){
            if(activeKeys.contains(key)){
                handleKeyPress(key, window);
            }
        }else if(action == GLFW_PRESS){
            activeKeys.add(key);
        }else if(action == GLFW_REPEAT){
            //Not Sure what to do with a repeat.
        }
    }

    private void handleKeyPress(int key, long window){
        switch(key){
            case GLFW_KEY_ESCAPE:
                glfwSetWindowShouldClose(window, true);
                break;
            default:
                dispatchKeyPress(key);
                break;
        }
    }

    private void dispatchKeyPress(int key){

    }

    public Set<Integer> getActiveKeys() {
        return activeKeys;
    }
}
