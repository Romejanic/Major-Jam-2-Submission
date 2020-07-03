#version 330

layout(location = 0) in vec2 vertex;
out vec2 texCoords;
out vec2 debug;

uniform ivec4 spriteInfo;
uniform mat4 cameraTransform;
uniform mat4 transform;
uniform float spriteScale;

const vec2 spritesheetSize = vec2(128.,128.);

void main(void) {
	gl_Position = cameraTransform * transform * vec4(vertex, 0., 1.);
	// calculate texture coordinates
	vec2 tc = ((vertex * vec2(1.,-1.) * spriteScale) * .5 + .5) % vec2(1.,1.);
	texCoords = (spriteInfo.xy + tc * spriteInfo.zw) / spritesheetSize;
	debug = tc;
}