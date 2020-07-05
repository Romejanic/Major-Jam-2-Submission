package com.jam.rooms;

import java.awt.Desktop;
import java.net.URI;

import com.jam.actors.CharacterActor;
import com.jam.main.Main;
import com.jam.math.Color;
import com.jam.render.tilemap.Tilemap;
import com.jam.room.Room;
import com.jam.room.RoomTransition;
import com.jam.ui.UiButton;
import com.jam.ui.UiSprite;

public class TitleRoom extends Room {
	
	private static final String ITCH_IO_LINK = "https://romejanic.itch.io/";
	
	private UiSprite logo;
	private RoomTransition toGame;
	
	private float time = 0f;
	
	@Override
	public void populate() {
		// characters
		for(int i = 0; i < 20; i++) {
			CharacterActor character = new CharacterActor();
			character.setWander(-30f, -10f, 30f, 10f);
			character.transform.position.x = -30f + (float)Math.random() * 60f;
			character.transform.position.y = -10f + (float)Math.random() * 20f;
			this.addActor(character);
		}
		// background
		this.setBgColor(new Color(0.6f, 0.6f, 0.2f));
		// tilemap
		this.setTilemap(new Tilemap("love_tile").setWidth(30f));
		// title logo
		this.logo = this.addUiElement(new UiSprite("title_logo", -150, 150));
		this.logo.scale = 6f;
		// play button
		this.addUiElement(new UiButton("btn_play", -40, 0, this).addListener(() -> {
			if(this.toGame != null) return;
			this.toGame = TitleRoom.this.addUiElement(new RoomTransition(IngameRoom.class));
		}));
		// quit button
		this.addUiElement(new UiButton("btn_quit", 40, 0, this).addListener(() -> {
			Main.getInstance().requestClose();
		}));
		// credit button
		this.addUiElement(new UiButton("credit", -310, -270, this, true).addListener(() -> {
			try {
				Desktop.getDesktop().browse(URI.create(ITCH_IO_LINK));
			} catch(Exception e) {
				System.err.println("Failed to open link in browser!");
				System.err.print("Caused by ");
				e.printStackTrace(System.err);
			}
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