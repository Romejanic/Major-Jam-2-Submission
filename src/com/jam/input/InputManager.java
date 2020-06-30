package com.jam.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.system.MemoryStack;

public class InputManager {

	private long handle;
	private double mx;
	private double my;
	
	private final boolean[] keyStates = new boolean[Key.values().length];
	private final boolean[] mouseBtnStates = new boolean[MouseButton.values().length];
	
	private InputManager(long handle) {
		this.handle = handle;
	}
	
	public boolean isKeyDown(Key key) {
		for(int code : key.codes) {
			if(glfwGetKey(handle, code) == GLFW_PRESS) return true;
		}
		return false;
	}
	
	public boolean isKeyPressed(Key key) {
		return isKeyDown(key) && !keyStates[key.ordinal()];
	}
	
	public boolean isMouseButtonDown(MouseButton btn) {
		return glfwGetMouseButton(handle, btn.ordinal()) == GLFW_PRESS;
	}
	
	public boolean isMouseButtonPressed(MouseButton btn) {
		return isMouseButtonDown(btn) && !mouseBtnStates[btn.ordinal()];
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
	
	public void postUpdate() {
		for(int i = 0; i < keyStates.length; i++) {
			keyStates[i] = isKeyDown(Key.values()[i]);
		}
		for(int i = 0; i < mouseBtnStates.length; i++) {
			mouseBtnStates[i] = isMouseButtonDown(MouseButton.values()[i]);
		}
	}

	public static InputManager newFromWindow(long window) {
		return new InputManager(window);
	}
	
}