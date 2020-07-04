package com.jam.room;

import java.util.ArrayList;
import java.util.Iterator;

import com.jam.math.Transform2D;
import com.jam.render.sprites.Sprite;

public class Actor {

	private final ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Room currentRoom;
	public final Transform2D transform = new Transform2D();
	
	public void update(float delta) {}
	
	public <T extends Sprite> T addSprite(T sprite) {
		this.sprites.add(sprite);
		sprite.transform.setParent(this.transform);
		if(this.currentRoom != null) {
			this.currentRoom.notifyActorSprite(sprite);
		}
		return sprite;
	}
	
	public void removeSprite(Sprite sprite) {
		this.sprites.add(sprite);
		if(sprite.transform.isChildOf(this.transform)) {
			sprite.transform.clearParent();
		}
		if(this.currentRoom != null) {
			this.currentRoom.denotifyActorSprite(sprite);
		}
	}
	
	public void moveTo(Room room) {
		this.currentRoom = room;
	}
	
	public Iterator<Sprite> getSpriteIterator() {
		return this.sprites.iterator();
	}
	
	public Sprite getSpriteAt(int idx) {
		if(idx < 0 || idx >= this.sprites.size()) return null;
		return this.sprites.get(idx);
	}
	
}