package com.jam.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.system.MemoryStack;

public class InputManager {

	private long handle;
	private double mx;
	private double my;
	
	private InputManager(long handle) {
		this.handle = handle;
	}
	
	public boolean isKeyDown(Key key) {
		for(int code : key.codes) {
			if(glfwGetKey(handle, code) == GLFW_PRESS) return true;
		}
		return false;
	}
	
	public double getMouseX() {
		return mx;
	}
	
	public double getMouseY() {
		return my;
	}
	
	public void update() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			DoubleBuffer mxb = stack.callocDouble(1);
			DoubleBuffer myb = stack.callocDouble(1);
			glfwGetCursorPos(handle, mxb, myb);
			mx = mxb.get();
			my = myb.get();
		}
	}

	public static InputManager newFromWindow(long window) {
		return new InputManager(window);
	}
	
}