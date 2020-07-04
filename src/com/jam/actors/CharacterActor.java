package com.jam.actors;

import com.jam.render.sprites.Sprite;
import com.jam.room.Actor;

public class CharacterActor extends Actor {

	private Sprite head;
	
	private float animTime = 0f;
	private boolean isMale = false;
	
	public CharacterActor() {
		this.isMale = Math.random() > 0.5d;
		this.animTime += (float)Math.random();
		String spriteSuffix = this.isMale ? "male" : "female";
		// torso sprite
		this.addSprite(new Sprite("torso").matchAspect());
		// head sprite
		this.head = this.addSprite(new Sprite("head_" + spriteSuffix).matchAspect());
		this.head.transform.position.y = 1.85f;
	}
	
	@Override
	public void update(float delta) {
		this.animTime += delta;
		// animations
		if(this.isMale) {
			this.head.transform.position.y = 1.85f + 0.05f * (float)Math.sin(this.animTime*4d*Math.PI);
		} else {
			this.head.transform.position.y = 1.75f + 0.05f * (float)Math.sin(this.animTime*4d*Math.PI);
		}
	}
	
}