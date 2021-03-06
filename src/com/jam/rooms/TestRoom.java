package com.jam.rooms;

import com.jam.actors.PlayerActor;
import com.jam.main.Main;
import com.jam.math.Color;
import com.jam.room.Actor;
import com.jam.room.Room;
import com.jam.ui.UiButton;

public class TestRoom extends Room {

	private Actor testSprite;

	@Override
	public void populate() {
		this.testSprite = this.newActorWithSprite("wood_floor");
		this.testSprite.getSpriteAt(0).transform.rotation.rotateZ(0.4f);
		this.testSprite.getSpriteAt(0).tint = new Color(1f,0.2f,0.2f);
		
		PlayerActor player = this.addActor(new PlayerActor());
		player.transform.position.x += 3f;
		
		this.addUiElement(new UiButton("btn_play", -40, 0, this).addListener(() -> {
			System.out.println("I've been pressed!");
		}));
		this.addUiElement(new UiButton("btn_quit", 40, 0, this).addListener(() -> {
			Main.getInstance().requestClose();
		}));
	}
	
	@Override
	public void updateRoom(float delta) {
		this.testSprite.transform.position.y += delta * 0.5f;
	}

}
