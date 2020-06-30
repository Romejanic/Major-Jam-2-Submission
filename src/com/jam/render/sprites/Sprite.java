package com.jam.render.sprites;

import org.joml.Vector4f;

import com.jam.main.Main;
import com.jam.math.Color;
import com.jam.math.Transform2D;
import com.jam.render.sprites.SpriteList.SpriteData;

public class Sprite {

	public final Transform2D transform = new Transform2D();
	public SpriteData sprite;
	public Color tint = new Color(Color.WHITE);
	public boolean enabled = true;
	
	public Sprite(SpriteData sprite) {
		this.sprite = sprite;
	}
	
	public boolean isMouseOver() {
		float mx = (float)Main.getInput().getMouseX();
		float my = (float)Main.getInput().getMouseY();
		this.transform.update();
		Vector4f pos = new Vector4f(mx, my, 0f, 1f);
		this.transform.toMatrix().transform(pos, pos);
		return false;
	}
	
}