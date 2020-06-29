package com.jam.math;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform2D {

	public final Vector3f position = new Vector3f();
	public final Quaternionf rotation = new Quaternionf();
	public final Vector2f scale = new Vector2f(1f, 1f);
	
	private final Matrix4f matrix = new Matrix4f();
	
	public void update() {
		this.matrix.identity()
		.translate(this.position)
		.rotate(this.rotation)
		.scale(this.scale.x, this.scale.y, 1f);
	}
	
	public void identity() {
		this.position.set(0f);
		this.rotation.identity();
		this.scale.set(1f);
		this.matrix.identity();
	}
	
	public Matrix4fc toMatrix() {
		return this.matrix;
	}
	
}