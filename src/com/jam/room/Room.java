package com.jam.room;

import java.util.ArrayList;
import java.util.Iterator;

import com.jam.render.SpriteRenderer;
import com.jam.render.UiRenderer;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList.SpriteData;
import com.jam.ui.UiSprite;

public abstract class Room {

	private SpriteRenderer spriteRenderer;
	private UiRenderer uiRenderer;
	
	private final ArrayList<Actor> actors = new ArrayList<Actor>();
	
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
		actor.moveTo(this);
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
		actor.moveTo(null);
		Iterator<Sprite> iter = actor.getSpriteIterator();
		while(iter.hasNext()) {
			this.spriteRenderer.denotify(iter.next());
		}
	}
	
	public <T extends UiSprite> T addUiElement(T element) {
		this.uiRenderer.notify(element);
		return element;
	}
	
	public void removeUiElement(UiSprite element) {
		this.uiRenderer.denotify(element);
	}
	
	protected void notifyActorSprite(Sprite sprite) {
		this.spriteRenderer.notify(sprite);
	}
	
	protected void denotifyActorSprite(Sprite sprite) {
		this.spriteRenderer.denotify(sprite);
	}
	
	public void updateRenderers(SpriteRenderer sprite, UiRenderer ui) {
		this.spriteRenderer = sprite;
		this.uiRenderer = ui;
	}
	
}