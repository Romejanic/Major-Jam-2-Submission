package com.jam.input;

import static org.lwjgl.glfw.GLFW.*;

public enum Key {

	MOVE_FORWARD(GLFW_KEY_W, GLFW_KEY_UP),
	MOVE_BACKWARD(GLFW_KEY_S, GLFW_KEY_DOWN),
	MOVE_LEFT(GLFW_KEY_A, GLFW_KEY_LEFT),
	MOVE_RIGHT(GLFW_KEY_D, GLFW_KEY_RIGHT);
	
	public final int[] codes;
	
	Key(int... codes) {
		this.codes = codes;
	}
	
}