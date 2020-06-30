package com.jam.rooms;

import com.jam.actors.PlayerActor;
import com.jam.render.SpriteRenderer;
import com.jam.render.sprites.SpriteList;
import com.jam.room.Actor;
import com.jam.room.Room;
import com.jam.ui.ButtonActor;

public class TestRoom extends Room {

	private Actor testSprite;
	
	public TestRoom(SpriteRenderer spriteRenderer) {
		super(spriteRenderer);
	}

	@Override
	public void populate() {
		this.testSprite = this.newActorWithSprite(SpriteList.getSprite("wood_floor"));
		this.testSprite.getSpriteAt(0).transform.rotation.rotateZ(0.4f);
		
		PlayerActor player = this.addActor(new PlayerActor());
		player.transform.position.x += 3f;
		
		this.addActor(new ButtonActor("btn_play").setListener(() -> {
			System.out.println("i've been pressed!");
		}));
	}
	
	@Override
	public void updateRoom(float delta) {
		this.testSprite.transform.position.y += delta * 0.5f;
	}

}
