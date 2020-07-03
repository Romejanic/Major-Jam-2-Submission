#version 330

layout(location = 0) in vec2 vertex;
out vec2 texCoords;

uniform mat4 cameraTransform;
uniform mat4 transform;
uniform vec2 texScale;

void main(void) {
	gl_Position = cameraTransform * transform * vec4(vertex, 0., 1.);
	texCoords = ((vertex * texScale) * .5 + .5);
}