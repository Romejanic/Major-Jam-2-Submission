package com.jam.render.tilemap;

import com.jam.math.Color;
import com.jam.math.Transform2D;
import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;

public class Tilemap {

	public SpriteData sprite;
	public float scale = 10f;
	public Color tint = new Color(Color.WHITE);
	public Transform2D transform = new Transform2D();
	
	public Tilemap(String spriteName) {
		this.sprite = SpriteList.getSprite(spriteName);
		this.transform.scale.set(10f, 10f);
	}
	
}