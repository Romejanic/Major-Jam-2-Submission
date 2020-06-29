package com.jam.render;

import com.jam.render.sprites.SpriteMesh;

public class SpriteRenderer {

	protected void init() {
		SpriteMesh.get();
	}
	
	protected void render() {
		
	}
	
	protected void destroy() {
		SpriteMesh.get().delete();
	}
	
}