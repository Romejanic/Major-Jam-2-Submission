package com.jam.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.jam.math.Transform2D;
import com.jam.util.Util;

public class Camera {

	public final Transform2D transform = new Transform2D();
	public float size = 10f;
	
	private final Matrix4f projMatrix = new Matrix4f();
	private final Matrix4f viewMatrix = new Matrix4f();
	private final Matrix4f camMatrix  = new Matrix4f();
	
	private int screenWidth, screenHeight;
	
	public Matrix4f calcCameraMatrix(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
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
	
	public Vector2f screenPointToWorld(double x, double y) {
		// screen(x,y) -> ndc(x,y)
		// ndc(x,y) * invCamera -> world(x,y)
		// |>	ndc(x,y) * invProj -> view(x,y)
		// |>	view(x,y) * invView -> world(x,y)
		Vector4f pos = Util.TEMP_VEC4.set((float)x, (float)y, 0f, 1f);
		pos.x = (pos.x / this.screenWidth) * 2f - 1f;
		pos.y = (pos.y / this.screenHeight) * 2f - 1f;
		Matrix4f invCam = this.camMatrix.invert(Util.TEMP_MAT4);
		invCam.transform(pos);
		return Util.TEMP_VEC2.set(pos.x, pos.y);
	}
	
	public Vector2f worldPointToScreen(Vector3f vec) {
		Vector4f pos = Util.TEMP_VEC4.set(vec, 1f);
		this.camMatrix.transform(pos);
		pos.x = (pos.x * 0.5f + 0.5f) * this.screenWidth;
		pos.y = (pos.y * 0.5f + 0.5f) * this.screenHeight;
		return Util.TEMP_VEC2.set(pos.x, pos.y);
	}
	
}