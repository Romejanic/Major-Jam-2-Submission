package com.jam.actors.ai;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.jam.math.Transform2D;
import com.jam.room.Actor;

public class WanderAI {

	public Transform2D actorTransform;
	public float moveSpeed = 2f;
	public Vector2f minBound;
	public Vector2f maxBound;
	
	private Vector2f movePoint;
	private Vector2f deltaPos = new Vector2f();
	
	public WanderAI(Actor actor, float minX, float minY, float maxX, float maxY) {
		this.actorTransform = actor.transform;
		this.minBound = new Vector2f(minX, minY);
		this.maxBound = new Vector2f(maxX, maxY);
	}
	
	public void update(float delta) {
		Vector3f actorPos = this.actorTransform.position;
		if(this.movePoint == null) {
			this.movePoint = new Vector2f(actorPos.x, actorPos.y);
		}
		if(this.movePoint.distanceSquared(actorPos.x, actorPos.y) < 1f) {
			this.movePoint.set(
				lerp(minBound.x, maxBound.x, (float)Math.random()),
				lerp(minBound.y, maxBound.y, (float)Math.random())
			);
			float dx = this.movePoint.x - actorPos.x;
			this.actorTransform.scale.x = Math.signum(dx);
		}
		this.movePoint.sub(actorPos.x, actorPos.y, this.deltaPos);
		this.deltaPos.normalize();
		float speed = this.moveSpeed * delta;
		this.deltaPos.mul(speed);
		actorPos.add(this.deltaPos.x, this.deltaPos.y, 0f);
	}
	
	private float lerp(float a, float b, float x) {
		return a + (b - a) * x;
	}
	
}