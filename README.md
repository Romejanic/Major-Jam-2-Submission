# Major-Jam-2-Submission
My submission to the [Major Jam 2](https://itch.io/jam/major-jam-2) game jam.

[View the game on itch.io!](https://romejanic.itch.io/lovemonger)

This game was made in a 1-week time interval. It was designed under the constraint
that only **one** texture file was allowed, with a size of **128x128** pixels. In
other words, every sprite in the game is stored in a single 128x128px texture file
(located [here](/src/res/sprites.png)).

This project was written in Java, and uses LWJGL, PNGDecoder, JOML and Gson.

## Setup
1) Clone this project into Eclipse (or any other IDE)
2) Add the following libraries to the build path:
- [LWJGL 3](https://www.lwjgl.org/) (lwjgl.jar, lwjgl-glfw.jar, lwjgl-opengl.jar)
- LWJGL 3 Natives (relavant)
- [PNGDecoder](http://twl.l33tlabs.org/dist/PNGDecoder.jar)
- [Gson](https://github.com/google/gson)
- [JOML](https://github.com/JOML-CI/JOML)
3) Run `com.jam.main.Main` to start the game!