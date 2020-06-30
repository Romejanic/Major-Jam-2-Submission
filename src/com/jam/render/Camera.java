package com.jam.render;

import org.joml.Matrix4f;

import com.jam.math.Transform2D;
import com.jam.util.Util;

public class Camera {

	public final Transform2D transform = new Transform2D();
	public final float size = 1f;
	
	private final Matrix4f projMatrix = new Matrix4f();
	private final Matrix4f viewMatrix = new Matrix4f();
	private final Matrix4f camMatrix  = new Matrix4f();
	
	public Matrix4f calcCameraMatrix(int width, int height) {
		// update projection matrix
		float aspect = (float)width / (float)height;
		this.projMatrix.setOrtho(-size * aspect, size * aspect, -size, size, -10f, 10f);
		// update view matrix
		this.viewMatrix
			.identity()
			.translate(transform.position.negate(Util.TEMP_VEC3))
			.rotate(transform.rotation);
		// multiply them together
		this.projMatrix.mul(this.viewMatrix, this.camMatrix);
		return this.camMatrix;
	}
	
}