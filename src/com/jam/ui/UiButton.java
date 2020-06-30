package com.jam.ui;

import com.jam.main.Main;
import com.jam.room.Room;

public class UiButton extends UiSprite {

	private UiSprite overlaySprite;
	private ButtonListener listener;
	
	public UiButton(String spriteName, int posX, int posY, Room room) {
		super(spriteName, posX, posY);
		this.overlaySprite = new UiSprite("btn_highlight", posX, posY);
		room.addUiElement(this.overlaySprite);
		this.overlaySprite.enabled = false;
		this.overlaySprite.sortingOrder = 1;
		this.scale = 3f;
	}
	
	public UiButton addListener(ButtonListener listener) {
		this.listener = listener;
		return this;
	}
	
	@Override
	public void preRender() {
		this.overlaySprite.copyTransform(this);
		boolean hover = this.isMouseOver();
		this.overlaySprite.enabled = hover;
//		if(hover) // TODO: if(hover && mouse pressed) listener
	}
	
	private boolean isMouseOver() {
		float w = (this.width * this.scale);
		float h = (this.height * this.scale);
		float mx = (float)(Main.getInput().getMouseX() - Main.getInstance().getFrameWidth() * 0.5d);
		float my = (float)(Main.getInput().getMouseY() - Main.getInstance().getFrameHeight() * 0.5d);
		return mx > this.posX - w &&
			   my > this.posY - h &&
			   mx < this.posX + w &&
			   my < this.posY + h;
	}
	
	public interface ButtonListener {
		void pressed();
	}

}