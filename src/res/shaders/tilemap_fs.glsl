#version 330

out vec4 fragColor;
in vec2 texCoords;
in vec2 debug;

uniform sampler2D spritesheet;
uniform vec4 tintColor;

void main(void) {
	fragColor = texture(spritesheet, texCoords) * tintColor;
	fragColor = vec4(debug,0.,1.);
}