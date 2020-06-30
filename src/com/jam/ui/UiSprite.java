package com.jam.ui;

import org.joml.Matrix4f;

import com.jam.math.Color;
import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;

public class UiSprite {

	public SpriteData sprite;
	public int posX, posY, width, height;
	public float rotation = 0f;
	public float scale = 1f;
	public Color tint = new Color(Color.WHITE);
	public boolean enabled = true;
	
	private Matrix4f transform = new Matrix4f();
	
	public UiSprite(String spriteName, int posX, int posY) {
		this.sprite = SpriteList.getSprite(spriteName);
		this.posX = posX;
		this.posY = posY;
		this.width = this.sprite.w;
		this.height = this.sprite.h;
	}
	
	public Matrix4f getTransform() {
		return this.transform
				.identity()
				.translate(this.posX, this.posY, 0f)
				.rotateZ(this.rotation)
				.scale(this.width, this.height, 1f)
				.scale(this.scale);
	}
	
	public void copyTransform(UiSprite sprite) {
		this.posX = sprite.posX;
		this.posY = sprite.posY;
		this.rotation = sprite.rotation;
		this.scale = sprite.scale;
	}
	
	public void preRender() {}
	
}