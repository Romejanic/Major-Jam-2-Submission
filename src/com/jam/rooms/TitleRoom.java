package com.jam.rooms;

import com.jam.main.Main;
import com.jam.math.Color;
import com.jam.render.tilemap.Tilemap;
import com.jam.room.Room;
import com.jam.room.RoomTransition;
import com.jam.ui.UiButton;
import com.jam.ui.UiSprite;

public class TitleRoom extends Room {
	
	private UiSprite logo;
	private RoomTransition toGame;
	
	private float time = 0f;
	
	@Override
	public void populate() {
		this.setBgColor(new Color(0.6f, 0.6f, 0.2f));
		// tilemap
		this.setTilemap(new Tilemap("wood_floor").setWidth(30f));
		// title logo
		this.logo = this.addUiElement(new UiSprite("title_logo", -150, 150));
		this.logo.scale = 6f;
		// play button
		this.addUiElement(new UiButton("btn_play", -40, 0, this).addListener(() -> {
			if(this.toGame != null) return;
			this.toGame = TitleRoom.this.addUiElement(new RoomTransition(TitleRoom.class));
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
		// transition
		if(this.toGame != null) {
			this.toGame.update(delta);
		}
	}

}