#version 330

layout(location = 0) in vec2 vertex;
out vec2 texCoords;

uniform mat4 cameraTransform;
uniform mat4 transform;
uniform float spriteScale;

void main(void) {
	gl_Position = cameraTransform * transform * vec4(vertex, 0., 1.);
	texCoords = ((vertex * vec2(1.,-1.) * spriteScale) * .5 + .5);
}