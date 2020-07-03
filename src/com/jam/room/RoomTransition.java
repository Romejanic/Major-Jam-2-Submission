package com.jam.room;

import com.jam.main.Main;
import com.jam.math.Color;
import com.jam.ui.UiSprite;

public class RoomTransition extends UiSprite {

	private Class<? extends Room> room;
	private float totalTime = 1f;
	
	private float time = 0f;
	
	public RoomTransition(Class<? extends Room> room, float totalTime) {
		super("white", 0, 0);
		this.room = room;
		this.totalTime = totalTime;
		this.width = 500;
		this.height = 500;
		this.tint = new Color(0f,0f,0f,0f);
		this.sortingOrder = 1000;
	}
	
	public RoomTransition(Class<? extends Room> room) {
		this(room, 1f);
	}
	
	public void update(float delta) {
		this.time += delta;
		if(this.totalTime < this.time) {
			Main.getInstance().gotoRoom(this.room);
		}
		this.tint.a = Math.min(1f, this.time / this.totalTime);
	}
	
}