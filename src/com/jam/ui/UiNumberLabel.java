package com.jam.ui;

import java.util.ArrayList;
import java.util.List;

import com.jam.room.Room;

public class UiNumberLabel {

	private final Room room;
	
	private int number;
	private int posX, posY;
	private List<UiSprite> sprites;
	
	public UiNumberLabel(int number, int posX, int posY, Room room) {
		this.number = number;
		this.room = room;
		this.posX = posX;
		this.posY = posY;
		this.refresh();
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
	
	private void refresh() {
		if(this.sprites == null) {
			this.sprites = new ArrayList<UiSprite>();
		} else {
			for(UiSprite sprite : this.sprites) {
				this.room.removeUiElement(sprite);
			}
			this.sprites.clear();
		}
		int temp = this.number;
		if(temp == 0) {
			UiSprite zero = new UiSprite("0", this.posX, this.posY);
			zero.scale = 3f;
			this.sprites.add(zero);
			this.room.addUiElement(zero);
			return;
		}
		int xOff = 0;
		while(temp > 0) {
			int digit = temp % 10;
			UiSprite sprite = new UiSprite(String.valueOf(digit), this.posX - xOff, this.posY);
			sprite.scale = 3f;
			this.sprites.add(sprite);
			this.room.addUiElement(sprite);
			temp = temp / 10;
			xOff += 8 * sprite.scale;
		}
	}
	
}