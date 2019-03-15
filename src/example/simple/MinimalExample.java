package example.simple;

import processing.core.PApplet;

/**
 * Minimal example which just shows a text.
 */
public class MinimalExample extends PApplet {
	public static void main(String[] args) {
		PApplet.main(MinimalExample.class);
	}

	@Override
	public void settings() {
		size(640, 480);
	}

	@Override
	public void setup() {
		frameRate(30);
	}

	@Override
	public void draw() {
		// clear screen with a dark grey background
		background(50);

		textSize(25);
		text("Hello World", 200, 200);
	}
}
