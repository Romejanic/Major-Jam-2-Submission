package com.jam.render;

import static org.lwjgl.opengl.GL32.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4fc;

import com.jam.render.data.Shader;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;
import com.jam.render.sprites.SpriteMesh;

public class SpriteRenderer {

	private final HashMap<SpriteData,List<Sprite>> batches = new HashMap<SpriteData,List<Sprite>>();
	private Shader spriteShader;
	
	protected void init() throws Exception {
		// load data
		SpriteList.load();
		SpriteMesh.get();
		this.spriteShader = Shader.load("sprite");
	}
	
	protected void render(Matrix4fc camMatrix) {
		if(batches.isEmpty())
			return; // we do nothing =D
		// bind everything
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		SpriteMesh mesh = SpriteMesh.get();
		mesh.bind();
		this.spriteShader.bind();
		glUniform1i(this.spriteShader.getUniform("spritesheet"), 0);
		this.spriteShader.uniformMat4("cameraTransform", camMatrix);
		// draw each batch
		for(SpriteData spriteData : batches.keySet()) {
			this.spriteShader.uniformSpriteData("spriteInfo", spriteData);
			List<Sprite> batch = batches.get(spriteData);
			for(Sprite sprite : batch) {
				if(!sprite.enabled) continue;
				sprite.transform.update();
				spriteShader.uniformMat4("transform", sprite.transform.toMatrix());
				sprite.tint.uniformColor(this.spriteShader, "tintColor");
				mesh.draw();
			}
		}
		// unbind everything
		this.spriteShader.unbind();
		mesh.unbind();
		glDisable(GL_BLEND);
	}
	
	protected void destroy() {
		SpriteMesh.get().delete();
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
	
}