package com.jam.room;

import java.util.ArrayList;
import java.util.Iterator;

import com.jam.math.Color;
import com.jam.render.SpriteRenderer;
import com.jam.render.TilemapRenderer;
import com.jam.render.UiRenderer;
import com.jam.render.sprites.Sprite;
import com.jam.render.sprites.SpriteList;
import com.jam.render.sprites.SpriteList.SpriteData;
import com.jam.render.tilemap.Tilemap;
import com.jam.ui.UiSprite;

public abstract class Room {

	private SpriteRenderer spriteRenderer;
	private UiRenderer uiRenderer;
	private TilemapRenderer tilemapRenderer;
	
	private final ArrayList<Actor> actors = new ArrayList<Actor>();
	private Tilemap tilemap = null;
	private Color bgColor = Color.BLACK;
	
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
	
	public Actor newActorWithSprite(String data) {
		return newActorWithSprite(SpriteList.getSprite(data));
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
	
	public void setTilemap(Tilemap tilemap) {
		if(this.tilemap != null) {
			this.tilemapRenderer.denotify(this.tilemap);
		}
		this.tilemap = tilemap;
		if(tilemap != null) {
			this.tilemapRenderer.notify(tilemap);
		}
	}
	
	public void clearTilemap() {
		this.setTilemap(null);
	}
	
	public void setBgColor(Color color) {
		if(color == null) return;
		this.bgColor = color;
	}
	
	public Color getBgColor() {
		return this.bgColor;
	}
	
	public void updateRenderers(SpriteRenderer sprite, UiRenderer ui, TilemapRenderer tilemap) {
		this.spriteRenderer = sprite;
		this.uiRenderer = ui;
		this.tilemapRenderer = tilemap;
	}
	
}