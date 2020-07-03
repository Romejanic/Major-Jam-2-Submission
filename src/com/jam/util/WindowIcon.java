package com.jam.util;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class WindowIcon {

	private static final String ICON_NAME = "pink_heart";

	public static void load(long window) throws IOException {
		SpriteData iconData = SpriteList.getSprite(ICON_NAME);
		InputStream in = Util.getResource("sprites.png");
		PNGDecoder png = new PNGDecoder(in);

		ByteBuffer fullBuf = BufferUtils.createByteBuffer(png.getWidth()*png.getHeight()*4);
		png.decode(fullBuf, png.getWidth() * 4, Format.RGBA);
		in.close();

		ByteBuffer iconBuf = BufferUtils.createByteBuffer(iconData.w*iconData.h*4);
		for(int y = 0; y < iconData.h; y++) {
			int yc = iconData.y + y;
			for(int x = 0; x < iconData.w; x++) {
				int xc = iconData.x + x;
				int i = (yc * iconData.w + xc) * 4;
				iconBuf.put(new byte[] {
						fullBuf.get(i),
						fullBuf.get(i+1),
						fullBuf.get(i+2),
						fullBuf.get(i+3)
				});
			}
		}
		iconBuf.flip();

		GLFWImage image = GLFWImage.malloc();
		GLFWImage.Buffer buffer = GLFWImage.malloc(1);
		image.set(iconData.w, iconData.h, iconBuf);
		buffer.put(0, image);
		glfwSetWindowIcon(window, buffer);

		buffer.free();
		image.free();
	}

}