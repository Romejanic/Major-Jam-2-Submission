#version 330

out vec4 fragColor;
in vec2 texCoords;

uniform sampler2D spritesheet;
uniform vec4 tintColor;

void main(void) {
	fragColor = texture(spritesheet, texCoords) * tintColor;
}