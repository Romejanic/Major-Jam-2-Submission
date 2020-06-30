package com.jam.render.sprites;

import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

public class SpriteMesh {

	private static final float[] VERTICES = {
		-1.0f, -1.0f,
		-1.0f,  1.0f,
		 1.0f, -1.0f,
		
		 1.0f, -1.0f,
		 1.0f,  1.0f,
		-1.0f,  1.0f
	};

	private static SpriteMesh singleton;
	
	private int vao;
	private int vbo;
	private int vertexCount;
	
	public SpriteMesh() {
		this.vao = glGenVertexArrays();
		this.vbo = glGenBuffers();
		this.vertexCount = VERTICES.length / 2;
		// upload vertex data
		this.bind();
		glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = (FloatBuffer)stack.callocFloat(VERTICES.length).put(VERTICES).flip();
			glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		}
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		this.unbind();
	}
	
	public void bind() {
		glBindVertexArray(this.vao);
		glEnableVertexAttribArray(0);
	}
	
	public void unbind() {
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void draw() {
		glDrawArrays(GL_TRIANGLES, 0, this.vertexCount);
	}
	
	public void delete() {
		glDeleteBuffers(this.vbo);
		glDeleteVertexArrays(this.vao);
	}
	
	public static SpriteMesh get() {
		if(singleton == null) singleton = new SpriteMesh();
		return singleton;
	}
	
}