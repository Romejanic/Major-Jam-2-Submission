package com.jam.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import javax.swing.JOptionPane;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import com.jam.render.Renderer;

public class Main {

	public static final String GAME_NAME = "Major Jam 2";
	public static final int OPENGL_VERSION_MAJOR = 3;
	public static final int OPENGL_VERSION_MINOR = 2;
	
	private long window = NULL;
	private Renderer renderer = new Renderer();
	
	public void run() throws Exception {
		// init
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()) {
			throw new RuntimeException("Failed to start GLFW!");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, OPENGL_VERSION_MAJOR);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, OPENGL_VERSION_MINOR);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		window = glfwCreateWindow(800, 600, "Major Jam 2", NULL, NULL);
		if(window == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create game window!");
		}
		GLFWVidMode vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vm.width()-800)/2, (vm.height()-600)/2);
		glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
			renderer.updateSize(w, h);
		});
		glfwMakeContextCurrent(window);
		renderer.init();
		try(MemoryStack stack = MemoryStack.stackPush()) {
			// get current framebuffer size
			IntBuffer wb = stack.callocInt(1);
			IntBuffer hb = stack.callocInt(1);
			glfwGetFramebufferSize(window, wb, hb);
			renderer.updateSize(wb.get(), hb.get());
		}
		glfwShowWindow(window);
		// loop
		while(!glfwWindowShouldClose(window)) {
			renderer.render();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		// destroy
		renderer.destroy();
		Callbacks.glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwSetErrorCallback(null).free();
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		try {
			new Main().run();
		} catch(Exception e) {
			System.err.println("!! CRASHED !!");
			System.err.print("Caused by ");
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(
				null,
				"Sorry, the game has crashed!\nHere is the error message:\n\n" + e.toString(),
				"Crashed!",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
}