package com.jam.rooms;

import com.jam.render.tilemap.Tilemap;
import com.jam.room.Room;
import com.jam.ui.UiNumberLabel;

public class IngameRoom extends Room {
	
	private UiNumberLabel testNumber;
	private int teee;
	private int c;
	
	@Override
	public void populate() {
		this.setTilemap(new Tilemap("love_tile").setWidth(30f));
		this.testNumber = new UiNumberLabel(1000, 0, 150, this);
	}

	@Override
	public void updateRoom(float delta) {
		teee++;
		if(teee % 60 == 0) {
			c++;
			this.testNumber.set(c);
		}
	}

}
