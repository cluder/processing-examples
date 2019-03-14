package example.simple;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * Our Game-Class extends {@link PApplet}, which contains the Game-Loop.
 */
public class SimpleExample extends PApplet {
	private PImage pepeImg;
	private float pepeX;
	private float pepeY;

	public static void main(String[] args) {
		// processing way to start the game
		PApplet.main(SimpleExample.class, args);
	}

	/**
	 * Called once at the start.<br>
	 * We set the background color and target frame rate.
	 */
	@Override
	public void setup() {
		super.setup();

		background(0);

		frameRate(30);

		// load an image and set initial position
		pepeImg = loadImage("resources/pepe.png");
		pepeX = 100;
		pepeY = 100;
	}

	@Override
	public void settings() {
		size(640, 480);
	}

	/**
	 * The main game loop<br>
	 * draw() is called continuously depending on the target frame-rate we set in
	 * setup().
	 */
	@Override
	public void draw() {
		final int xMid = sketchWidth() / 2;
		final int yMid = sketchHeight() / 2;

		// clear the whole screen
		clear();

		// set white color, display text
		fill(255, 255, 255);
		textSize(30);
		text("Hello World", xMid - 100, yMid);

		// draw a blue rect - filled - no stroke
		fill(0, 0, 255);
		noStroke();
		rect(xMid - 100, yMid + 10, 165, 10);

		// draw a green rect - not filled - with stroke
		color(0, 0, 255);
		noFill();
		stroke(0, 255, 0);
		rect(xMid - 120, yMid - 50, 210, 100);

		// display pepe
		image(pepeImg, pepeX, pepeY);
	}

	private void movePepe() {
		pepeX = (float) (Math.random() * (640.0 - pepeImg.width));
		pepeY = (float) (Math.random() * (480.0 - pepeImg.height));
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		super.mouseMoved(event);

		if (event.getX() >= pepeX && event.getX() <= pepeX + pepeImg.width && //
				event.getY() >= pepeY && event.getY() <= pepeY + pepeImg.height) {
			movePepe();
		}
	}
}
