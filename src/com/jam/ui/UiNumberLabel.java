package com.jam.ui;

import java.util.ArrayList;
import java.util.List;

import com.jam.room.Room;

public class UiNumberLabel {

	private final Room room;
	
	private int number;
	private int posX, posY;
	private float scale;
	private boolean percent = false;
	private List<UiSprite> sprites;
	private UiSprite prefix;
	
	private boolean enabled = true;
	
	public UiNumberLabel(int number, int posX, int posY, float scale, boolean percent, Room room) {
		this.number = number;
		this.room = room;
		this.posX = posX;
		this.posY = posY;
		this.scale = scale;
		this.percent = percent;
		this.refresh();
	}
	
	public UiNumberLabel(int number, int posX, int posY, float scale, Room room) {
		this(number, posX, posY, scale, false, room);
	}
	
	public UiNumberLabel(int number, int posX, int posY, Room room) {
		this(number, posX, posY, 3f, room);
	}
	
	public UiNumberLabel setPrefix(String prefix) {
		this.prefix = new UiSprite(prefix, 0, 0);
		this.refresh();
		return this;
	}
	
	public int get() {
		return this.number;
	}
	
	public void set(int number) {
		if(this.number == number) return;
		this.number = number;
		this.refresh();
	}
	
	public void moveTo(int x, int y) {
		if(this.posX == x && this.posY == y) return;
		int oldX = this.posX;
		this.posX = x;
		this.posY = y;
		this.sprites.forEach((s) -> {
			int xOff = s.posX - oldX;
			s.posX = x + xOff;
			s.posY = y;
		});
	}
	
	public void remove() {
		for(UiSprite sprite : this.sprites) {
			this.room.removeUiElement(sprite);
		}
		this.sprites.clear();
	}
	
	public void setEnabled(boolean enabled) {
		if(this.enabled == enabled) return;
		this.enabled = enabled;
		for(UiSprite sprite : this.sprites) {
			sprite.enabled = enabled;
		}
		this.prefix.enabled = enabled;
	}
	
	private void refresh() {
		if(this.sprites == null) {
			this.sprites = new ArrayList<UiSprite>();
		} else {
			this.remove();
		}
		int temp = this.number;
		int xOff = 0;
		if(this.percent) {
			UiSprite percent = new UiSprite("percent", this.posX, this.posY);
			percent.enabled = this.enabled;
			percent.scale = this.scale;
			this.sprites.add(percent);
			this.room.addUiElement(percent);
			xOff += 8 * percent.scale;
		}
		if(temp == 0) {
			UiSprite zero = new UiSprite("0", this.posX - xOff, this.posY);
			zero.enabled = this.enabled;
			zero.scale = this.scale;
			this.sprites.add(zero);
			this.room.addUiElement(zero);
			return;
		}
		while(temp > 0) {
			int digit = temp % 10;
			UiSprite sprite = new UiSprite(String.valueOf(digit), this.posX - xOff, this.posY);
			sprite.scale = this.scale;
			sprite.enabled = this.enabled;
			this.sprites.add(sprite);
			this.room.addUiElement(sprite);
			temp = temp / 10;
			xOff += 8 * sprite.scale;
		}
		if(this.prefix != null) {
			this.prefix.posX = this.posX - xOff - 10;
			this.prefix.posY = this.posY;
			this.prefix.scale = this.scale;
			this.sprites.add(this.prefix);
			this.room.addUiElement(this.prefix);
		}
	}
	
}