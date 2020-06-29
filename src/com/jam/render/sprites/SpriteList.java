package com.jam.render.sprites;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.jam.util.Util;

public class SpriteList {

	private static final HashMap<String,SpriteData> spriteMap = new HashMap<String,SpriteData>();
	
	public static void load() throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(Util.getResource("sprites.json")));
		JsonObject spriteJson = Util.GSON.fromJson(reader, JsonObject.class);
		for(String key : spriteJson.keySet()) {
			SpriteData data = Util.GSON.fromJson(spriteJson.get(key), SpriteData.class);
			spriteMap.put(key, data);
		}
		reader.close();
	}
	
	public static boolean isSpriteDefined(String name) {
		return spriteMap.containsKey(name);
	}
	
	public static SpriteData getSprite(String name) {
		if(!isSpriteDefined(name)) return null;
		return spriteMap.get(name);
	}
	
	public static class SpriteData {
		public int x, y, w, h;
	}
	
}