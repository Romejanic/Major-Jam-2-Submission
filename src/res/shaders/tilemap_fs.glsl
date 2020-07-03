#version 330

out vec4 fragColor;
in vec2 texCoords;

uniform sampler2D spritesheet;
uniform ivec4 spriteInfo;
uniform vec4 tintColor;

const vec2 spritesheetSize = vec2(128.,128.);

void main(void) {
	vec2 tc = (spriteInfo.xy + fract(texCoords) * spriteInfo.zw) / spritesheetSize;
	fragColor = texture(spritesheet, tc) * tintColor;
}