package com.jam.render.tilemap;

import org.joml.Vector2f;

import com.jam.math.Color;
import com.jam.math.Transform2D;
import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;

public class Tilemap {

	public SpriteData sprite;
	public Color tint = new Color(Color.WHITE);
	public Transform2D transform = new Transform2D();
	
	public Vector2f texScaling = new Vector2f(10f,-10f);
	
	public Tilemap(String spriteName) {
		this.sprite = SpriteList.getSprite(spriteName);
		this.transform.scale.set(10f, 10f);
	}
	
	public Tilemap setWidth(float w) {
		this.transform.scale.x = w;
		this.texScaling.x = w;
		this.texScaling.y = -10f;
		return this;
	}
	
	public Tilemap setHeight(float h) {
		this.transform.scale.y = h;
		this.texScaling.x = 10f;
		this.texScaling.y = -h;
		return this;
	}
	
	public Tilemap setSize(float w, float h) {
		this.transform.scale.set(w, h);
		this.texScaling.set(w, -h);
		return this;
	}
	
}