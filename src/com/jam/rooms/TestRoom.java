package com.jam.rooms;

import com.jam.render.SpriteRenderer;
import com.jam.render.sprites.SpriteList;
import com.jam.room.Actor;
import com.jam.room.Room;

public class TestRoom extends Room {

	private Actor testSprite;
	
	public TestRoom(SpriteRenderer spriteRenderer) {
		super(spriteRenderer);
	}

	@Override
	public void populate() {
		this.testSprite = this.newActorWithSprite(SpriteList.getSprite("test"));
		this.testSprite.getSpriteAt(0).transform.rotation.rotateZ(0.4f);
	}
	
	@Override
	public void update(float delta) {
		this.testSprite.transform.position.y += delta * 0.5f;
	}

}
