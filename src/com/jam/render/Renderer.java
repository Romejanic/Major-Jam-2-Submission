package com.jam.render;

import org.lwjgl.opengl.GL;

import com.jam.render.sprites.SpriteMesh;

import static org.lwjgl.opengl.GL32.*;

public class Renderer {

	private int width, height;
	
	private SpriteRenderer spriteRenderer = new SpriteRenderer();
	
	public void init() {
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
		this.width = width;
		this.height = height;
		glViewport(0, 0, width, height);
	}
	
}