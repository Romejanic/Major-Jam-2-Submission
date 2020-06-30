package com.jam.render;

import org.lwjgl.opengl.GL;

import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList;

import static org.lwjgl.opengl.GL32.*;

import org.lwjgl.glfw.GLFW;

public class Renderer {
	
	private SpriteRenderer spriteRenderer = new SpriteRenderer();
	
	private float delta = 1f;
	private float lastFrame = 0f;
	
	private Sprite testSprite;
	
	public void init() throws Exception {
		GL.createCapabilities();
		System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
		System.out.println("OpenGL Renderer: " + glGetString(GL_RENDERER) + " (" + glGetString(GL_VENDOR) + ")");
		// init gl
		glClearColor(0.4f, 0.6f, 0.9f, 1f);
		glEnable(GL_DEPTH_TEST);
//		glEnable(GL_CULL_FACE);
		glDepthFunc(GL_LEQUAL);
		glCullFace(GL_BACK);
		// init other renderers
		this.spriteRenderer.init();
		// test code
		testSprite = new Sprite(SpriteList.getSprite("test"));
		testSprite.transform.scale.mul(0.5f);
		this.spriteRenderer.notify(testSprite);
	}
	
	public void render() {
		// update delta
		float time = (float)GLFW.glfwGetTime();
		delta = time - lastFrame;
		lastFrame = time;
		// spin sprite
		testSprite.transform.rotation.rotateY(delta);
		// draw frame
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		this.spriteRenderer.render();
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
		glViewport(0, 0, width, height);
	}
	
}