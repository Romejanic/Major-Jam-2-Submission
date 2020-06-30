package com.jam.input;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

	private long handle;
	
	private InputManager(long handle) {
		this.handle = handle;
	}
	
	public boolean isKeyDown(Key key) {
		for(int code : key.codes) {
			if(glfwGetKey(handle, code) == GLFW_PRESS) return true;
		}
		return false;
	}

	public static InputManager newFromWindow(long window) {
		return new InputManager(window);
	}
	
}