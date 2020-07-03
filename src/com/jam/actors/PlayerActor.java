package com.jam.actors;

import com.jam.input.InputManager;
import com.jam.input.Key;
import com.jam.main.Main;
import com.jam.render.sprites.Sprite;
import com.jam.room.Actor;

public class PlayerActor extends Actor {

	public float walkSpeed = 4f;
	
	public PlayerActor() {
		this.addSprite(new Sprite("player_temp"));
	}
	
	@Override
	public void update(float delta) {
		float upDown = 0f;
		float leftRight = 0f;
		InputManager input = Main.getInput();
		
		if(input.isKeyDown(Key.MOVE_FORWARD)) {
			upDown += 1f;
		}
		if(input.isKeyDown(Key.MOVE_BACKWARD)) {
			upDown -= 1f;
		}
		if(input.isKeyDown(Key.MOVE_LEFT)) {
			leftRight -= 1f;
		}
		if(input.isKeyDown(Key.MOVE_RIGHT)) {
			leftRight += 1f;
		}
		
		float moveSpeed = this.walkSpeed * delta;
		this.transform.position.add(moveSpeed * leftRight, moveSpeed * upDown, 0f);
	}
	
}