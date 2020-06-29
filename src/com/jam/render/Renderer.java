package com.jam.render;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL32.*;

public class Renderer {
	
	private SpriteRenderer spriteRenderer = new SpriteRenderer();
	
	public void init() throws Exception {
		GL.createCapabilities();
		// init gl
		glClearColor(0.4f, 0.6f, 0.9f, 1f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glDepthFunc(GL_LEQUAL);
		glCullFace(GL_BACK);
		// init other renderers
		this.spriteRenderer.init();
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		this.spriteRenderer.render();
	}
	
	public void destroy() {
		this.spriteRenderer.destroy();
	}
	
	public void updateSize(int width, int height) {
		glViewport(0, 0, width, height);
	}
	
}