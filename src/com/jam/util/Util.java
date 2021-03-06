package com.jam.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {

	public static final Vector2f TEMP_VEC2 = new Vector2f();
	public static final Vector3f TEMP_VEC3 = new Vector3f();
	public static final Vector4f TEMP_VEC4 = new Vector4f();
	public static final Matrix4f TEMP_MAT4 = new Matrix4f();
	
	public static final Gson GSON = new GsonBuilder().create();

	public static InputStream getResource(String name) throws FileNotFoundException {
		String fullPath = "/res" + (name.startsWith("/") ? "" : "/") + name;
		InputStream in = Util.class.getResourceAsStream(fullPath);
		if(in == null) {
			throw new FileNotFoundException(fullPath);
		}
		return in;
	}
	
	public static String readFully(String resourceName) throws IOException {
		InputStream in = getResource(resourceName);
		StringBuilder sb = new StringBuilder();
		byte[] buf = new byte[1024];
		int i;
		while((i = in.read(buf)) != -1) {
			sb.append(new String(buf, 0, i));
		}
		in.close();
		return sb.toString();
	}
	
	public static Vector2f getRandomPointOnUnitCircle() {
		double a = Math.random() * 2 * Math.PI;
		return new Vector2f((float)Math.cos(a), (float)Math.sin(a)).normalize();
	}
	
	public static Vector3f getRandomUnitDirection() {
		return new Vector3f(
			(float)Math.random() * 2f - 1f,
			(float)Math.random() * 2f - 1f,
			(float)Math.random() * 2f - 1f
		).normalize();
	}
	
	public static Vector3f midpoint(Vector3f a, Vector3f b) {
		return a.lerp(b, 0.5f, TEMP_VEC3);
	}
	
}