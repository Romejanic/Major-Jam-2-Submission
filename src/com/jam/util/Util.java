package com.jam.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.joml.Vector3f;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {

	public static final Vector3f TEMP_VEC3 = new Vector3f();
	
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
	
}