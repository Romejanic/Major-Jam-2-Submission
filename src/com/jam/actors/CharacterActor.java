package com.jam.actors;

import com.jam.actors.ai.WanderAI;
import com.jam.render.sprites.Sprite;
import com.jam.room.Actor;

public class CharacterActor extends Actor {

	private Sprite head;
	private Sprite leftLeg;
	private Sprite rightLeg;

	private float animTime = 0f;
	private float legMoveTime = 0f;
	private int legState = 0;
	private boolean isMale = false;

	private WanderAI wander;

	public CharacterActor() {
		this.isMale = Math.random() > 0.5d;
		this.animTime += (float)Math.random();
		String spriteSuffix = this.isMale ? "male" : "female";
		// left leg
		this.leftLeg = this.addSprite(new Sprite("leg_l")).matchAspect();
		this.leftLeg.transform.position.x = 0.2f;
		this.leftLeg.transform.position.y = -2f;
		// right leg
		this.rightLeg = this.addSprite(new Sprite("leg_r")).matchAspect();
		this.rightLeg.transform.position.x = 0.5f;
		this.rightLeg.transform.position.y = -2f;
		// torso sprite
		this.addSprite(new Sprite("torso").matchAspect());
		// head sprite
		this.head = this.addSprite(new Sprite("head_" + spriteSuffix).matchAspect());
		this.head.transform.position.y = 1.85f;
	}

	public void setWander(float minX, float minY, float maxX, float maxY) {
		this.wander = new WanderAI(this, minX, minY, maxX, maxY);
	}

	@Override
	public void update(float delta) {
		this.animTime += delta;
		this.legMoveTime += delta;
		this.setActorSortingOrder((int)(100f*this.transform.position.y));
		if(this.wander != null) {
			this.wander.update(delta);
		}
		// animations
		if(this.isMale) {
			this.head.transform.position.y = 1.85f + 0.05f * (float)Math.sin(this.animTime*4d*Math.PI);
		} else {
			this.head.transform.position.y = 1.75f + 0.05f * (float)Math.sin(this.animTime*4d*Math.PI);
		}
		if(this.legMoveTime > 0.2f) {
			this.legMoveTime = 0;
			this.legState = (legState + 1) % 4;
			switch(this.legState) {
			case 1:
				this.leftLeg.transform.rotation.rotateZ(-0.4f);
				this.rightLeg.transform.rotation.rotateZ(0.4f);
				this.leftLeg.transform.position.x -= 0.05f;
				this.leftLeg.transform.position.y += 0.1f;
				this.rightLeg.transform.position.x += 0.05f;
				this.rightLeg.transform.position.y += 0.3f;
				break;
			case 3:
				this.leftLeg.transform.rotation.rotateZ(0.4f);
				this.rightLeg.transform.rotation.rotateZ(-0.4f);
				this.leftLeg.transform.position.x += 0.5f;
				this.leftLeg.transform.position.y += 0.3f;
				this.rightLeg.transform.position.x -= 0.6f;
				this.rightLeg.transform.position.y += 0.1f;
				break;
			case 0:
			case 2:
			default:
				this.leftLeg.transform.position.x = 0.2f;
				this.leftLeg.transform.position.y = -2f;
				this.leftLeg.transform.rotation.identity();
				this.rightLeg.transform.position.x = 0.5f;
				this.rightLeg.transform.position.y = -2f;
				this.rightLeg.transform.rotation.identity();
				break;
			}
		}
	}

}