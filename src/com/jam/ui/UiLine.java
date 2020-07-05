package com.jam.ui;

import org.joml.Vector2f;

import com.jam.math.Color;

public class UiLine {

	public final Vector2f a = new Vector2f();
	public final Vector2f b = new Vector2f();
	public Color color = Color.WHITE;
	public boolean enabled = true;
	
	public UiLine(float ax, float ay, float bx, float by) {
		this.a.set(ax, ay);
		this.b.set(bx, by);
	}
	
	public UiLine(Vector2f a, Vector2f b) {
		this.a.set(a);
		this.b.set(b);
	}
	
}