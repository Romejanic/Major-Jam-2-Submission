package com.jam.render.data;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

public class LineMesh {

	private static final float[] VERTICES = {
			0f, 1f
	};

	private int vao;
	private int vbo;

	public LineMesh() {
		this.vao = glGenVertexArrays();
		this.vbo = glGenBuffers();
		// upload vertex data
		this.bind();
		glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = (FloatBuffer)stack.callocFloat(VERTICES.length).put(VERTICES).flip();
			glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		}
		glVertexAttribPointer(0, 1, GL_FLOAT, false, 0, 0);
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
		glDrawArrays(GL_LINES, 0, VERTICES.length);
	}

	public void delete() {
		glDeleteBuffers(this.vbo);
		glDeleteVertexArrays(this.vao);
	}

}