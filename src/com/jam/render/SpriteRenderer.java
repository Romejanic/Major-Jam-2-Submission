package com.jam.render;

import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryStack;

import com.jam.render.data.Shader;
import com.jam.render.data.Texture;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;
import com.jam.render.sprites.SpriteMesh;

public class SpriteRenderer {

	private final HashMap<SpriteData,List<Sprite>> batches = new HashMap<SpriteData,List<Sprite>>();
	
	private Texture spritesheet;
	private Shader spriteShader;
	
	protected void init() throws Exception {
		// load data
		SpriteList.load();
		SpriteMesh.get();
		this.spritesheet = Texture.load("sprites.png");
		this.spriteShader = Shader.load("sprite");
	}
	
	protected void render() {
		if(batches.isEmpty())
			return; // we do nothing =D
		// bind everything
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		SpriteMesh mesh = SpriteMesh.get();
		mesh.bind();
		this.spritesheet.bind(0);
		this.spriteShader.bind();
		glUniform1i(this.spriteShader.getUniform("spritesheet"), 0);
		// draw each batch
		for(SpriteData spriteData : batches.keySet()) {
			glUniform4i(this.spriteShader.getUniform("spriteInfo"), spriteData.x, spriteData.y, spriteData.w, spriteData.h);
			List<Sprite> batch = batches.get(spriteData);
			for(Sprite sprite : batch) {
				sprite.transform.update();
				this.uniformMat4("transform", sprite.transform.toMatrix());
				mesh.draw();
			}
		}
		// unbind everything
		this.spriteShader.unbind();
		this.spritesheet.unbind();
		mesh.unbind();
		glDisable(GL_BLEND);
	}
	
	protected void destroy() {
		SpriteMesh.get().delete();
		this.spritesheet.delete();
		this.spriteShader.delete();
	}
	
	public void notify(Sprite sprite) {
		List<Sprite> batch = batches.get(sprite.sprite);
		if(batch == null) {
			batch = new ArrayList<Sprite>();
			batches.put(sprite.sprite, batch);
		}
		batch.add(sprite);
	}
	
	public void denotify(Sprite sprite) {
		if(!batches.containsKey(sprite.sprite))
			return;
		batches.get(sprite.sprite).remove(sprite);
		if(batches.get(sprite.sprite).isEmpty())
			batches.remove(sprite.sprite);
	}
	
	private void uniformMat4(String name, Matrix4fc matrix) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.callocFloat(16);
			glUniformMatrix4fv(this.spriteShader.getUniform(name), false, matrix.get(buffer));
		}
	}
	
}