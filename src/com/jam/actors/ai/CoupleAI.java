package com.jam.actors.ai;

import org.joml.Vector2fc;

import com.jam.actors.CharacterActor;
import com.jam.math.Transform2D;
import com.jam.room.Actor;

public class CoupleAI extends WanderAI {

	private boolean leader = false;
	private Transform2D following;
	
	public CoupleAI(Actor actor, float minX, float minY, float maxX, float maxY) {
		super(actor, minX, minY, maxX, maxY);
	}
	
	public CoupleAI(Actor actor, Vector2fc min, Vector2fc max) {
		this(actor, min.x(), min.y(), max.x(), max.y());
	}

	public void setFollowing(CharacterActor actor) {
		this.following = actor.transform;
		this.leader = false;
		actor.setLeaderFor(this);
	}
	
	public void setLeader() {
		this.leader = true;
	}
	
	public void breakup() {
		this.leader = false;
		this.following = null;
	}
	
	@Override
	public void update(float delta) {
		if(this.leader || this.following != null) {
			super.update(delta);
		} else {
			super.moveTowards(this.following.position, delta);
		}
	}
	
}