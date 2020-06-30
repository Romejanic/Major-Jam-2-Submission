package com.jam.render;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL32.*;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class Renderer {
	
	private SpriteRenderer spriteRenderer = new SpriteRenderer();
	
	private float delta = 1f;
	private float lastFrame = 0f;
	private int width;
	private int height;
	
	private Camera camera = new Camera();
	
	public void init() throws Exception {
		GL.createCapabilities();
		System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
		System.out.println("OpenGL Renderer: " + glGetString(GL_RENDERER) + " (" + glGetString(GL_VENDOR) + ")");
		// init gl
		glClearColor(0.4f, 0.6f, 0.9f, 1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		// init other renderers
		this.spriteRenderer.init();
	}
	
	public void render() {
		// update delta
		float time = (float)GLFW.glfwGetTime();
		delta = time - lastFrame;
		lastFrame = time;
		// update camera
		Matrix4f camMatrix = this.camera.calcCameraMatrix(this.width, this.height);
		// draw frame
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		this.spriteRenderer.render(camMatrix);
		// check for OpenGL errors
		int err;
		do {
			err = glGetError();
			if(err != GL_NO_ERROR) {
				System.err.println("OpenGL ERROR: " + err);
			}
		} while(err != GL_NO_ERROR);
	}
	
	public void destroy() {
		this.spriteRenderer.destroy();
	}
	
	public void updateSize(int width, int height) {
		this.width = width;
		this.height = height;
		glViewport(0, 0, width, height);
	}
	
}