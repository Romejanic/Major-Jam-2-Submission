package com.jam.render;

import static org.lwjgl.opengl.GL32.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.joml.Matrix4f;

import com.jam.render.data.LineMesh;
import com.jam.render.data.Shader;
import com.jam.render.sprites.SpriteMesh;
import com.jam.ui.UiLine;
import com.jam.ui.UiSprite;

public class UiRenderer {

	private final List<UiSprite> sprites = new ArrayList<UiSprite>();
	private final LinkedList<UiSprite> queuedSprites = new LinkedList<UiSprite>();
	private final List<UiLine> lines = new ArrayList<UiLine>();

	private SpriteMesh mesh;
	private Shader shader;
	private Matrix4f projMat = new Matrix4f();

	private LineMesh lineMesh;
	private Shader lineShader;

	protected void init() throws Exception {
		this.mesh = SpriteMesh.get();
		this.shader = Shader.load("ui");
		this.lineMesh = new LineMesh();
		this.lineShader = Shader.load("line");
	}

	protected void render(int w, int h) {
		this.updateProjMat(w, h);
		this.sprites.sort((a,b) -> { return a.sortingOrder-b.sortingOrder; });
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// draw lines
		this.lineMesh.bind();
		this.lineShader.bind();
		this.lineShader.uniformMat4("projMat", this.projMat);
		{
			for(UiLine line : this.lines) {
				if(!line.enabled) continue;
				this.lineShader.uniformVec2("lineEnds[0]", line.a);
				this.lineShader.uniformVec2("lineEnds[1]", line.b);
				line.color.uniformColor(this.lineShader, "color");
				this.lineMesh.draw();
			}
		}
		this.lineShader.unbind();
		this.lineMesh.unbind();
		// draw sprites
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
		this.lineMesh.delete();
		this.lineShader.delete();
	}

	protected void clearBatches() {
		this.sprites.clear();
		this.queuedSprites.clear();
		this.lines.clear();
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

	public void notify(UiLine line) {
		this.lines.add(line);
	}

	public void denotify(UiLine line) {
		if(!this.lines.contains(line)) return;
		this.lines.remove(line);
	}

}