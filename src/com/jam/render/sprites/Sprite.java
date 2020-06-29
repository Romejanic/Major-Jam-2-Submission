package com.jam.render.sprites;

import com.jam.math.Transform2D;
import com.jam.render.sprites.SpriteList.SpriteData;

public class Sprite {

	public final Transform2D transform = new Transform2D();
	public SpriteData sprite;
	
	public Sprite(SpriteData sprite) {
		this.sprite = sprite;
	}
	
}