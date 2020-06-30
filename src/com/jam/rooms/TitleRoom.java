package com.jam.rooms;

import com.jam.main.Main;
import com.jam.room.Room;
import com.jam.ui.UiButton;
import com.jam.ui.UiSprite;

public class TitleRoom extends Room {
	
	private UiSprite logo;
	
	private float time = 0f;
	
	@Override
	public void populate() {
		// title logo
		this.logo = this.addUiElement(new UiSprite("title_logo", -150, 150));
		this.logo.scale = 6f;
		// play button
		this.addUiElement(new UiButton("btn_play", -40, 0, this).addListener(() -> {
			System.out.println("play was clicked");
		}));
		// quit button
		this.addUiElement(new UiButton("btn_quit", 40, 0, this).addListener(() -> {
			Main.getInstance().requestClose();
		}));
	}

	@Override
	public void updateRoom(float delta) {
		this.time += delta;
		// animate logo
		this.logo.posY = (int)(150 + 30 * Math.sin(this.time));
		this.logo.scale = 6f + 0.5f * (float)Math.cos(this.time);
	}

}