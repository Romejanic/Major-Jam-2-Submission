package com.jam.rooms;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.jam.actors.CharacterActor;
import com.jam.input.InputManager;
import com.jam.input.MouseButton;
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
	private UiNumberLabel startTimeLabel;
	
	private float startTimer = 3f;
	private float gameTimer = 60f;
	private boolean gameOver = false;
	private boolean showResults = false;
	
	private CharacterActor[] chars;
	private List<CoupleData> couples = new ArrayList<CoupleData>();
	
	private int hoveredChar = -1;
	private int selectedChar = -1;
	private UiLine selectedLine = new UiLine(0f,0f,0f,0f);
	private UiNumberLabel compatHighlight;
	
	@Override
	public void populate() {
		this.setTilemap(new Tilemap("love_tile").setWidth(30f));
		// selection line
		this.selectedLine.enabled = false;
		this.selectedLine.color = new Color(1f, 0.38f, 0.98f);
		this.addUiElement(this.selectedLine);
		// compat highlight
		this.compatHighlight = new UiNumberLabel(0, 0, 0, 2f, true, this).setPrefix("compat");
		this.compatHighlight.setEnabled(false);
		// labels
		this.timeLabel = new UiNumberLabel(60, 360, 280, this);
		this.addUiElement(new UiSprite("timer", 385, 280, 2f));
		this.startTimeLabel = new UiNumberLabel(3, 0, 0, 5f, this);
		this.startTimeLabel.setSortingLayer(20000);
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
		this.scrim.tint = new Color(0f, 0f, 0f, 1f);
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
		// show results
		if(this.showResults) {
			if(this.scrim.tint.a < 1f) {
				this.scrim.tint.a = Math.min(1f, this.scrim.tint.a + delta);
			}
			return;
		}
		// start timer
		if(this.startTimer > 0f) {
			this.startTimer -= delta;
			int time = (int)Math.max(0, Math.ceil((double)this.startTimer));
			if(this.startTimeLabel.get() != time) {
				this.startTimeLabel.set(time);
			}
			return;
		}
		if(this.startTimeLabel != null) {
			this.startTimeLabel.remove();
			this.startTimeLabel = null;
		}
		// update timer
		this.gameTimer -= delta;
		int intTimer = (int) Math.max(0, Math.ceil((double)this.gameTimer));
		if(this.timeLabel.get() != intTimer) {
			this.timeLabel.set(intTimer);
		}
		if(this.gameTimer < -5f) {
			this.showResults = true;
			this.scrim.tint = new Color(0.76f, 0.01f, 1f, 0f);
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
			InputManager input = Main.getInput();
			this.selectedLine.enabled = this.isSomeoneSelected();
			this.compatHighlight.setEnabled(this.isSomeoneSelected() && this.hoveredChar > -1);
			if(this.isSomeoneSelected() && this.scrim.tint.a < 1f) {
				this.scrim.tint.a = Math.min(0.5f, this.scrim.tint.a + delta);
				CharacterActor character = this.chars[this.selectedChar];
				this.selectedLine.b.set(this.getCamera().worldPointToScreen(character.transform.position));
				if(this.hoveredChar == -1) {
					float mx = (float)input.getMouseX() - Main.getInstance().getFrameWidth() / 2;
					float my = (float)input.getMouseY() - Main.getInstance().getFrameHeight() / 2;
					this.selectedLine.a.set(mx, -my);
				} else {
					Vector3f pos = this.chars[this.hoveredChar].transform.position;
					Vector2f screenPos = this.getCamera().worldPointToScreen(pos);
					this.selectedLine.a.set(screenPos.x, screenPos.y);
					int score = (int)(100f * character.getCompatabilityScore(this.chars[this.hoveredChar]));
					this.compatHighlight.set(score);
					screenPos.set(this.getCamera().worldPointToScreen(pos));
					this.compatHighlight.moveTo((int)screenPos.x, (int)screenPos.y);
				}
				if(input.isMouseButtonPressed(MouseButton.LEFT) && this.hoveredChar == -1) {
					this.selectedChar = -1;
				}
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
	
	public void makeCoupleWithSelected(CharacterActor other) {
		CharacterActor selected = this.chars[this.selectedChar];
		if(other == selected) {
			this.selectedChar = -1;
			this.hoveredChar = -1;
			return;
		}
		CoupleData aCouple = this.getCoupleFor(selected);
		CoupleData bCouple = this.getCoupleFor(other);
		if(aCouple != null) {
			aCouple.end();
			selected.breakup(aCouple.happy);
		}
		if(bCouple != null) {
			bCouple.end();
			other.breakup(bCouple.happy);
		}
		this.makeCouple(selected, other);
		this.selectedChar = -1;
		this.hoveredChar = -1;
	}
	
	private void makeCouple(CharacterActor a, CharacterActor b) {
		a.setFollowing(b);
		CoupleData data = new CoupleData(a, b);
		this.couples.add(data);
		a.updateFace(data.happy);
		b.updateFace(data.happy);
	}
	
	private CoupleData getCoupleFor(CharacterActor actor) {
		for(CoupleData data : this.couples) {
			if(data.a == actor || data.b == actor) {
				return data;
			}
		}
		return null;
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
	
	public void hover(int idx) {
		this.hoveredChar = idx;
	}
	
	public boolean isSomeoneSelected() {
		return this.selectedChar != -1;
	}
	
	class CoupleData {
		CharacterActor a, b;
		float compat, happy;
		UiNumberLabel compatNum, happyNum;
		UiLine[] lines;
		
		private CoupleData(CharacterActor a, CharacterActor b) {
			this.a = a;
			this.b = b;
			this.compat = a.getCompatabilityScore(b);
			this.happy = b.getCompatabilityScore(b);
			
			IngameRoom room = IngameRoom.this;
			this.compatNum = new UiNumberLabel((int)(this.compat*100f), 0, 0, 2f, true, room).setPrefix("compat");
			this.happyNum = new UiNumberLabel((int)(this.happy*100f), 0, 0, 2f, true, room).setPrefix("happy");
			
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
			this.compatNum.moveTo(px + 10, py + 30);
			this.happyNum.moveTo(px + 10, py + 60);
		}
		
		private void end() {
			this.compatNum.remove();
			for(UiLine line : this.lines) {
				IngameRoom.this.removeUiElement(line);
			}
		}
	}
	
}
