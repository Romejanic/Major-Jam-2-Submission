package com.jam.render.data;

import static org.lwjgl.opengl.GL32.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;

import com.jam.util.Util;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {

	private int target;
	private int texture;
	
	public Texture(int target, int texture) {
		this.target = target;
		this.texture = texture;
	}
	
	public void bind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(this.target, this.texture);
	}
	
	public void unbind() {
		glBindTexture(this.target, 0);
	}
	
	public void delete() {
		glDeleteTextures(this.texture);
		this.texture = 0;
	}
	
	public static Texture load(String name) throws IOException {
		// load texture data
		PNGDecoder in = new PNGDecoder(Util.getResource("sprites.png"));
		Texture   tex = new Texture(GL_TEXTURE_2D, 0);
		try(MemoryStack stack = MemoryStack.stackPush()) {
			// read texture data into buffer
			ByteBuffer data = stack.calloc(in.getWidth() * in.getHeight() * 4);
			in.decode(data, in.getWidth(), Format.RGBA);
			data.flip();
			// create opengl texture
			tex.texture = glGenTextures();
			tex.bind(0);
			// upload texture data to gpu
			glTexImage2D(tex.target, 0, GL_RGBA, in.getWidth(), in.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
			// set some parameters
			glTexParameteri(tex.target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(tex.target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(tex.target, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(tex.target, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			tex.unbind();
		}
		// and we're done
		return tex;
	}
	
}