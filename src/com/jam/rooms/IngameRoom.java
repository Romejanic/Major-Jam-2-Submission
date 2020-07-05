package com.jam.rooms;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.jam.actors.CharacterActor;
import com.jam.main.Main;
import com.jam.math.Color;
import com.jam.render.Camera;
import com.jam.render.tilemap.Tilemap;
import com.jam.room.Room;
import com.jam.ui.UiLine;
import com.jam.ui.UiNumberLabel;
import com.jam.ui.UiSprite;
import com.jam.util.Util;

public class IngameRoom extends Room {

	private UiNumberLabel timeLabel;
	private UiSprite timeUpSprite;
	private UiSprite scrim;
	
	private float gameTimer = 60f;
	private boolean gameOver = false;
	
	private CharacterActor[] chars;
	private List<CoupleData> couples = new ArrayList<CoupleData>();
	
	private int selectedChar = -1;
	
	@Override
	public void populate() {
		this.setTilemap(new Tilemap("love_tile").setWidth(30f));
		// labels
		this.timeLabel = new UiNumberLabel(60, 360, 280, this);
		this.addUiElement(new UiSprite("timer", 385, 280, 2f));
		// characters
		this.chars = new CharacterActor[20];
		for(int i = 0; i < this.chars.length; i++) {
			boolean isMale = i < this.chars.length / 2;
			this.chars[i] = this.addActor(new CharacterActor(isMale)).setIndex(i);
			this.chars[i].setWander(-30f, -10f, 30f, 10f);
			this.chars[i].transform.position.x = -30f + (float)Math.random() * 60f;
			this.chars[i].transform.position.y = -10f + (float)Math.random() * 20f;
		}
		// scrim
		this.scrim = this.addUiElement(new UiSprite("white", 0, 0));
		this.scrim.width = Main.getInstance().getFrameWidth();
		this.scrim.height = Main.getInstance().getFrameHeight();
		this.scrim.tint = new Color(0f, 0f, 0f, 0f);
		this.scrim.sortingOrder = 10000;
		// make some couples
		int nCouples = 2 + (int)Math.random() * 2;
		for(int i = 0; i < nCouples; i++) {
			CharacterActor a = this.getRandomSingleChar();
			CharacterActor b = this.getRandomSingleChar();
			a.doInitialRelationship(b);
			this.makeCouple(a, b);
		}
	}

	@Override
	public void updateRoom(float delta) {
		// update timer
		this.gameTimer -= delta;
		int intTimer = (int) Math.max(0, Math.ceil((double)this.gameTimer));
		if(this.timeLabel.get() != intTimer) {
			this.timeLabel.set(intTimer);
		}
		// update couples
		for(CoupleData couple : this.couples) {
			couple.updateUi();
		}
		// gameover
		if(intTimer == 0) {
			if(this.timeUpSprite == null) {
				this.timeUpSprite = this.addUiElement(new UiSprite("time_up", 0, 0));
				this.timeUpSprite.scale = 10f;
				this.timeUpSprite.tint.a = 0f;
			}
			if(this.timeUpSprite.scale > 6f) {
				this.timeUpSprite.scale -= delta;
				this.timeUpSprite.scale = Math.max(6f, this.timeUpSprite.scale);
			}
			if(this.timeUpSprite.tint.a < 1f) {
				this.timeUpSprite.tint.a += delta;
				this.timeUpSprite.tint.a = Math.min(1f, this.timeUpSprite.tint.a);
			}
		}
		// selection ui
		else {
			if(this.isSomeoneSelected() && this.scrim.tint.a < 1f) {
				this.scrim.tint.a = Math.min(0.5f, this.scrim.tint.a + delta);
			} else if(!this.isSomeoneSelected() && this.scrim.tint.a > 0f) {
				this.scrim.tint.a = Math.max(0f, this.scrim.tint.a - delta);
			}
		}
	}

	private CharacterActor getRandomSingleChar() {
		CharacterActor c = null;
		do {
			c = this.chars[(int)(Math.random()*this.chars.length)];
		} while(c.isInCouple());
		return c;
	}
	
	private void makeCouple(CharacterActor a, CharacterActor b) {
		a.setFollowing(b);
		this.couples.add(new CoupleData(a, b));
	}
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	public void select(int idx) {
		if(this.isSomeoneSelected() || idx < 0 || idx >= this.chars.length) {
			return;
		}
		this.selectedChar = idx;
	}
	
	public boolean isSomeoneSelected() {
		return this.selectedChar != -1;
	}
	
	class CoupleData {
		CharacterActor a, b;
		float compat, happy;
		UiNumberLabel num;
		UiLine[] lines;
		
		private CoupleData(CharacterActor a, CharacterActor b) {
			this.a = a;
			this.b = b;
			this.compat = a.getCompatabilityScore(b);
			this.happy = b.getCompatabilityScore(b);
			
			IngameRoom room = IngameRoom.this;
			this.num = new UiNumberLabel((int)(this.compat*100f), 0, 0, 2f, true, room).setPrefix("compat");
			
			this.lines = new UiLine[2];
			for(int i = 0; i < lines.length; i++) {
				this.lines[i] = room.addUiElement(new UiLine(0f, 0f, 0f, 0f));
			}
			
			this.updateUi();
		}
		
		private void updateUi() {
			Camera cam = IngameRoom.this.getCamera();
			Vector3f mid = Util.midpoint(a.transform.position, b.transform.position);
			Vector2f pos = cam.worldPointToScreen(mid);
			int px = (int)pos.x, py = (int)pos.y;
			// connect lines
			this.lines[0].a.set(px, py+30);
			this.lines[1].a.set(px, py+30);
			this.lines[0].b.set(cam.worldPointToScreen(a.transform.position));
			this.lines[1].b.set(cam.worldPointToScreen(b.transform.position));
			// position ui elements
			this.num.moveTo(px + 10, py + 30);
		}
		
		private void end() {
			this.num.remove();
			for(UiLine line : this.lines) {
				IngameRoom.this.removeUiElement(line);
			}
		}
	}
	
}
