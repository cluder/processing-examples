package example.simple;

import processing.core.PApplet;

public class MinimalExample extends PApplet {
	public static void main(String[] args) {
		PApplet.main(MinimalExample.class);
	}

	@Override
	public void settings() {
		setSize(640, 480);
	}

	@Override
	public void setup() {
		frameRate(30);

	}

	@Override
	public void draw() {
		clear();
		background(0);

		text("Hello World", 100, 100);
	}
}
