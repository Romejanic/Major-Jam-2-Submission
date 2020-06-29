package com.jam.util;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {

	public static final Gson GSON = new GsonBuilder().create();

	public static InputStream getResource(String name) throws FileNotFoundException {
		String fullPath = "/res" + (name.startsWith("/") ? "" : "/") + name;
		InputStream in = Util.class.getResourceAsStream(fullPath);
		if(in == null) {
			throw new FileNotFoundException(fullPath);
		}
		return in;
	}
	
}