package com.jam.rooms;

import com.jam.math.Color;
import com.jam.room.Room;
import com.jam.room.RoomTransition;
import com.jam.ui.UiButton;
import com.jam.ui.UiSprite;

public class TutorialRoom extends Room {
	
	private RoomTransition toGame;
	private float animTimer = 0f;
	
	private UiSprite tutorialSprite;
	
	@Override
	public void populate() {
		this.setBgColor(new Color(0.76f, 0.01f, 1f));
		// text
		this.tutorialSprite = this.addUiElement(new UiSprite("tutorial", 0, 50, 5f));
		// button
		this.addUiElement(new UiButton("btn_play", 0, -250, this)).addListener(() -> {
			if(this.toGame != null) return;
			this.toGame = this.addUiElement(new RoomTransition(IngameRoom.class));
		});
	}

	@Override
	public void updateRoom(float delta) {
		this.animTimer += delta;
		// animation
		this.tutorialSprite.posY = (int)(50 + 10 * (float)Math.sin((double)this.animTimer));
		// transition
		if(this.toGame != null) {
			this.toGame.update(delta);
		}
	}

}