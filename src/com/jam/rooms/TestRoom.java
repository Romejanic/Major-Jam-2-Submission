package com.jam.rooms;

import com.jam.actors.PlayerActor;
import com.jam.math.Color;
import com.jam.render.sprites.SpriteList;
import com.jam.room.Actor;
import com.jam.room.Room;
import com.jam.ui.UiButton;

public class TestRoom extends Room {

	private Actor testSprite;

	@Override
	public void populate() {
		this.testSprite = this.newActorWithSprite(SpriteList.getSprite("wood_floor"));
		this.testSprite.getSpriteAt(0).transform.rotation.rotateZ(0.4f);
		this.testSprite.getSpriteAt(0).tint = new Color(1f,0.2f,0.2f);
		
		PlayerActor player = this.addActor(new PlayerActor());
		player.transform.position.x += 3f;
		
		this.addUiElement(new UiButton("btn_play", 0, 0, this));
	}
	
	@Override
	public void updateRoom(float delta) {
		this.testSprite.transform.position.y += delta * 0.5f;
	}

}
