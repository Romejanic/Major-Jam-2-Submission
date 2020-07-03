package com.jam.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4fc;

import com.jam.render.data.Shader;
import com.jam.render.sprites.SpriteList.SpriteData;
import com.jam.render.sprites.SpriteMesh;
import com.jam.render.tilemap.Tilemap;

public class TilemapRenderer {

	private final HashMap<SpriteData,List<Tilemap>> batches = new HashMap<SpriteData,List<Tilemap>>();
	
	private SpriteMesh mesh;
	private Shader shader;
	
	protected void init() throws Exception {
		this.mesh = SpriteMesh.get();
		this.shader = Shader.load("tilemap");
	}
	
	protected void render(Matrix4fc camMatrix) {
		this.mesh.bind();
		this.shader.bind();
		this.shader.uniformMat4("cameraTransform", camMatrix);
		{
			for(SpriteData sprite : this.batches.keySet()) {
				this.shader.uniformSpriteData("spriteInfo", sprite);
				List<Tilemap> batch = this.batches.get(sprite);
				for(Tilemap tile : batch) {
					tile.transform.update();
					this.shader.uniformMat4("transform", tile.transform.toMatrix());
					this.shader.uniformVec2("texScale", tile.texScaling);
					tile.tint.uniformColor(this.shader, "tintColor");
					this.mesh.draw();
				}
			}
		}
		this.shader.unbind();
		this.mesh.unbind();
	}
	
	protected void destroy() {
		this.shader.delete();
	}
	
	protected void clearBatches() {
		this.batches.clear();
	}
	
	public void notify(Tilemap tilemap) {
		List<Tilemap> batch = this.batches.get(tilemap.sprite);
		if(batch == null) {
			batch = new ArrayList<Tilemap>();
			this.batches.put(tilemap.sprite, batch);
		}
		batch.add(tilemap);
	}
	
	public void denotify(Tilemap tilemap) {
		if(!this.batches.containsKey(tilemap.sprite)) return;
		List<Tilemap> batch = this.batches.get(tilemap.sprite);
		this.batches.get(tilemap.sprite).remove(tilemap);
		if(batch.isEmpty()) {
			this.batches.remove(tilemap.sprite);
		}
	}
	
}