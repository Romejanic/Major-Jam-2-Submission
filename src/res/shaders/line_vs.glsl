#version 330

layout(location = 0) in float t;

uniform mat4 projMat;
uniform vec2 lineEnds[2];

void main(void) {
	vec2 linePos = mix(lineEnds[0], lineEnds[1], t);
	gl_Position  = projMat * vec4(linePos, 0., 1.);
}