#version 330

layout(location = 0) in vec2 vertex;
out vec2 texCoords;

uniform ivec4 spriteInfo;
uniform mat4 projMat;
uniform mat4 transform;

const vec2 spritesheetSize = vec2(128.,128.);

void main(void) {
	gl_Position = projMat * transform * vec4(vertex, 0., 1.);
	// calculate texture coordinates
	vec2 tc = (vertex * vec2(1.,-1.)) * .5 + .5;
	texCoords = (spriteInfo.xy + tc * spriteInfo.zw) / spritesheetSize;
}