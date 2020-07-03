package com.jam.render;

import static org.lwjgl.opengl.GL32.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.joml.Matrix4f;

import com.jam.render.data.Shader;
import com.jam.render.sprites.SpriteMesh;
import com.jam.ui.UiSprite;

public class UiRenderer {

	private final List<UiSprite> sprites = new ArrayList<UiSprite>();
	private final LinkedList<UiSprite> queuedSprites = new LinkedList<UiSprite>();
	
	private SpriteMesh mesh;
	private Shader shader;
	private Matrix4f projMat = new Matrix4f();
	
	protected void init() throws Exception {
		this.mesh = SpriteMesh.get();
		this.shader = Shader.load("ui");
	}
	
	protected void render(int w, int h) {
		this.updateProjMat(w, h);
		this.sprites.sort((a,b) -> { return a.sortingOrder-b.sortingOrder; });
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		this.mesh.bind();
		this.shader.bind();
		this.shader.uniformMat4("projMat", this.projMat);
		{
			for(UiSprite sprite : this.sprites) {
				if(!sprite.enabled) continue;
				sprite.preRender();
				this.shader.uniformMat4("transform", sprite.getTransform());
				this.shader.uniformSpriteData("spriteInfo", sprite.sprite);
				sprite.tint.uniformColor(this.shader, "tintColor");
				this.mesh.draw();
			}
		}
		this.shader.unbind();
		this.mesh.unbind();
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		// add queued sprites
		if(!this.queuedSprites.isEmpty()) {
			this.sprites.addAll(this.queuedSprites);
			this.queuedSprites.clear();
		}
	}
	
	protected void destroy() {
		this.shader.delete();
	}
	
	protected void clearBatches() {
		this.sprites.clear();
		this.queuedSprites.clear();
	}
	
	private void updateProjMat(int w, int h) {
		this.projMat.setOrthoSymmetric(w, h, -1f, 1f);
	}
	
	public void notify(UiSprite sprite) {
		this.queuedSprites.add(sprite);
	}
	
	public void denotify(UiSprite sprite) {
		if(!this.sprites.contains(sprite)) return;
		this.sprites.remove(sprite);
	}
	
}