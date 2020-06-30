package com.jam.ui;

import com.jam.main.Main;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList;
import com.jam.room.Actor;

public class ButtonActor extends Actor {

	private Sprite enabledSprite;
	private ButtonListener listener;
	
	public ButtonActor(String buttonSprite) {
		this.addSprite(new Sprite(SpriteList.getSprite(buttonSprite)));
		this.enabledSprite = new Sprite(SpriteList.getSprite("btn_highlight"));
		this.addSprite(this.enabledSprite);
		this.enabledSprite.enabled = false;
		this.transform.position.z = 9.9f;
		this.enabledSprite.transform.position.z = 0.05f;
	}
	
	public ButtonActor setListener(ButtonListener listener) {
		this.listener = listener;
		return this;
	}
	
	@Override
	public void update(float delta) {
		this.transform.update();
		this.enabledSprite.enabled = this.enabledSprite.isMouseOver();
	}
	
	public interface ButtonListener {
		void pressed();
	}
	
}