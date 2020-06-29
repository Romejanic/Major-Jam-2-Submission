package com.jam.render.data;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;

import com.jam.util.Util;

public class Shader {

	private final String name;
	private final int program;
	private final HashMap<String,Integer> uniforms = new HashMap<String,Integer>();
	
	public Shader(String name, int program) {
		this.name = name;
		this.program = program;
	}
	
	public void bind() {
		glUseProgram(this.program);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void delete() {
		glDeleteProgram(this.program);
	}
	
	public int getUniformLocation(String name) {
		if(!this.uniforms.containsKey(name)) {
			int location = glGetUniformLocation(this.program, name);
			if(location < 0) {
				System.err.println("Uniform " + name + " not found for program " + this.name);
			}
			this.uniforms.put(name, location);
			return location;
		}
		return this.uniforms.get(name);
	}
	
	public static Shader load(String name) throws Exception {
		int vs = loadShader(name+"_vs", GL_VERTEX_SHADER);
		int fs = loadShader(name+"_fs", GL_FRAGMENT_SHADER);
		int program = glCreateProgram();
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			String log = glGetProgramInfoLog(program);
			glDeleteShader(vs);
			glDeleteShader(fs);
			glDeleteProgram(program);
			throw new RuntimeException("Failed to link program " + name + "!\n" + log);
		}
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		
		return new Shader(name, program);
	}
	
	private static int loadShader(String path, int type) throws Exception {
		int shader = glCreateShader(type);
		try {
			glShaderSource(shader, Util.readFully("shaders/"+path+".glsl"));
			glCompileShader(shader);
			if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
				String log = glGetShaderInfoLog(shader);
				throw new RuntimeException("Failed to compile shader " + path + "!\n" + log);
			}
			return shader;
		} catch(Exception e) {
			glDeleteShader(shader);
			throw e;
		}
	}
	
}