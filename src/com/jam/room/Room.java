package com.jam.room;

import java.util.ArrayList;
import java.util.Iterator;

import com.jam.render.SpriteRenderer;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList.SpriteData;

public abstract class Room {

	private final SpriteRenderer spriteRenderer;
	
	private final ArrayList<Actor> actors = new ArrayList<Actor>();
	
	public Room(SpriteRenderer spriteRenderer) {
		this.spriteRenderer = spriteRenderer;
	}
	
	public abstract void populate();
	public abstract void updateRoom(float delta);
	
	public void update(float delta) {
		this.updateRoom(delta);
		for(Actor actor : this.actors) {
			actor.update(delta);
		}
	}
	
	public Actor newActorWithSprite(SpriteData data) {
		Actor actor = new Actor();
		actor.addSprite(new Sprite(data));
		this.addActor(actor);
		return actor;
	}
	
	public <T extends Actor> T addActor(T actor) {
		this.actors.add(actor);
		Iterator<Sprite> iter = actor.getSpriteIterator();
		while(iter.hasNext()) {
			this.spriteRenderer.notify(iter.next());
		}
		return actor;
	}
	
	public void removeActor(Actor actor) {
		if(!this.actors.contains(actor))
			return;
		this.actors.remove(actor);
		Iterator<Sprite> iter = actor.getSpriteIterator();
		while(iter.hasNext()) {
			this.spriteRenderer.denotify(iter.next());
		}
	}
	
	protected void notifyActorSprite(Sprite sprite) {
		this.spriteRenderer.notify(sprite);
	}
	
	protected void denotifyActorSprite(Sprite sprite) {
		this.spriteRenderer.denotify(sprite);
	}
	
}