package com.jam.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jam.render.data.Texture;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList.SpriteData;
import com.jam.render.sprites.SpriteMesh;

public class SpriteRenderer {

	private final HashMap<SpriteData,List<Sprite>> batches = new HashMap<SpriteData,List<Sprite>>();
	private Texture spritesheet;
	
	protected void init() throws Exception {
		// load data
		SpriteMesh.get();
		this.spritesheet = Texture.load("sprites.png");
	}
	
	protected void render() {
		if(batches.isEmpty())
			return; // we do nothing =D
		// bind everything
		SpriteMesh mesh = SpriteMesh.get();
		mesh.bind();
		this.spritesheet.bind(0);
		// draw each batch
		for(SpriteData spriteData : batches.keySet()) {
			// TODO: set texture coordinate uniforms
			List<Sprite> batch = batches.get(spriteData);
			for(Sprite sprite : batch) {
				// TODO: set transform uniform
				mesh.draw();
			}
		}
		// unbind everything
		this.spritesheet.unbind();
		mesh.unbind();
	}
	
	protected void destroy() {
		SpriteMesh.get().delete();
		this.spritesheet.delete();
	}
	
	public void notify(Sprite sprite) {
		List<Sprite> batch = batches.get(sprite.sprite);
		if(!batches.containsKey(sprite.sprite)) {
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