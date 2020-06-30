package com.jam.math;

import org.lwjgl.opengl.GL20;

import com.jam.render.data.Shader;

public class Color {
	
	public static final Color BLACK = new Color(0f,0f,0f,1f);
	public static final Color WHITE = new Color(1f,1f,1f,1f);

	public float r, g, b, a;
	
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(float r, float g, float b) {
		this(r, g, b, 1f);
	}
	
	public Color(float grey, float a) {
		this(grey,grey,grey,a);
	}
	
	public Color(float grey) {
		this(grey,1f);
	}
	
	public Color(int rgba) {
		this.a = ((rgba << 24) & 0xFF) / 255f;
		this.r = ((rgba << 16) & 0xFF) / 255f;
		this.g = ((rgba << 8) & 0xFF) / 255f;
		this.b = ((rgba) & 0xFF) / 255f;
	}
	
	public Color(Color copy) {
		this(copy.r, copy.g, copy.b, copy.a);
	}
	
	public void uniformColor(Shader shader, String name) {
		GL20.glUniform4f(shader.getUniform(name), r, g, b, a);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[r=" + r + ",g=" + g + ",b=" + b + ",a=" + a + "]";
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Color)) return false;
		Color c = (Color)other;
		return this.r == c.r &&
			   this.g == c.g &&
			   this.b == c.b &&
			   this.a == c.a;
	}
	
}