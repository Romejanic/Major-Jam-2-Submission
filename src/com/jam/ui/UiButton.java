package com.jam.ui;

import com.jam.room.Room;

public class UiButton extends UiSprite {

	private UiSprite overlaySprite;
	
	public UiButton(String spriteName, int posX, int posY, Room room) {
		super(spriteName, posX, posY);
		this.overlaySprite = new UiSprite("btn_highlight", posX, posY);
		room.addUiElement(this.overlaySprite);
		this.overlaySprite.enabled = false;
		this.scale = 3f;
	}
	
	@Override
	public void preRender() {
		this.overlaySprite.copyTransform(this);
		this.overlaySprite.enabled = Math.random() > 0.4d;
	}

}