package com.jam.render.sprites;

import com.jam.math.Color;
import com.jam.math.Transform2D;
import com.jam.render.sprites.SpriteList.SpriteData;

public class Sprite {

	public final Transform2D transform = new Transform2D();
	public SpriteData sprite;
	public Color tint = new Color(Color.WHITE);
	public boolean enabled = true;
	public int sortingOrder = 0;
	
	public Sprite(SpriteData sprite) {
		this.sprite = sprite;
	}
	
	public Sprite(String spriteName) {
		this(SpriteList.getSprite(spriteName));
	}
	
	public Sprite matchAspect() {
		this.transform.scale.x = (float)sprite.w / (float)sprite.h;
		return this;
	}
	
}