package com.jam.rooms;

import com.jam.render.tilemap.Tilemap;
import com.jam.room.Room;
import com.jam.ui.UiNumberLabel;

public class IngameRoom extends Room {

	private UiNumberLabel scoreLabel;
	private UiNumberLabel timeLabel;
	
	@Override
	public void populate() {
		this.setTilemap(new Tilemap("love_tile").setWidth(30f));
		// labels
		this.scoreLabel = new UiNumberLabel(0, 0, 260, 6f, this);
		this.timeLabel = new UiNumberLabel(60, 360, 280, this);
	}

	@Override
	public void updateRoom(float delta) {
		
	}

}
