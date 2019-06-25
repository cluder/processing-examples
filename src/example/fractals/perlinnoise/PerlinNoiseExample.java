package example.fractals.perlinnoise;

import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * Perlin noise example, using tiles.
 */
public class PerlinNoiseExample extends PApplet {
	int numTiles = 0;
	float tileSize = 0;

	public static void main(String[] args) {
		args = append(args, PerlinNoiseExample.class.getName());
		PApplet.main(args);
	}

	@Override
	public void settings() {
		size(600, 600);
	}

	@Override
	public void setup() {
		background(255, 255);
		frameRate(30);
		// set number of tiles / tilesize
		numTiles = 75;
		tileSize = width / numTiles;
	}

	// world coordinates of the top left tile, can be negative
	float coordX = 500;
	float coordY = -1000;
	// scroll speed
	float scrollSpeed = 0.01f;

	private boolean moveUp;
	private boolean moveDown;
	private boolean moveLeft;
	private boolean moveRight;

	private boolean shiftPressed;
	private boolean ctrlPressed;

	float noiseVal;
	float noiseScale = 0.02f;

	@Override
	public void draw() {
		// fixed seed to get a deterministic map
		noiseSeed(2);
		// detail
		noiseDetail(4, 0.5f);

		// move map on keypress (asdw)
		scrollSpeed = 1f;
		move();

		// generate and draw each tile
		for (int tileX = 0; tileX < numTiles; tileX++) {
			for (int tileY = 0; tileY < numTiles; tileY++) {
				// prepare noise input values
				float noiseScale = 0.02f;
				final float noiseInputX = (coordX + tileX) * noiseScale;
				final float noiseInputY = (coordY + tileY) * noiseScale;

//				System.out.println(
//						"coordx/y:" + coordX + "," + coordY + " | noise input:" + noiseInputX + ": " + noiseInputY);

				// generate noise value
				// TODO: cache recent values for the last few x/y values
				float noise = noise(noiseInputX, noiseInputY);

				if (numTiles < 120)
					stroke(0);
				else
					noStroke();
				// render map
				if (noise < 0.3) {
					// grass
					fill(0, 255, 0);
				} else if (noise < 0.4) {
					// sand
					fill(255, 255, 0);
				} else {
					// water
					fill(0, 0, 255);
				}

				// draw each tile
				rect(tileX * tileSize, tileY * tileSize, tileSize, tileSize);
			}
		}

		printTextBox();
	}

	private void printTextBox() {
		fill(0, 180);
		rect(10, 10, 480, 110);

		int y = 30;
		stroke(255, 255, 255);
		fill(255, 255, 0);
		textSize(17);
		text("* Mouse wheel to zoom", 20, y);
		y += 20;
		text("* a,s,d,w or arrows to move ", 20, y);
		y += 20;
		text("* shift: faster, ctrl: super-fast", 20, y);
		y += 20;
		y += 20;
		text("FPS:" + round(frameRate), 20, y);
		text(" #tiles per row/col:" + numTiles, 90, y);
	}

	/**
	 * move the playfiled by modifying the world coords.
	 */
	private void move() {
		float realSpeed = scrollSpeed;

		if (shiftPressed)
			realSpeed += 3.5f;
		if (ctrlPressed)
			realSpeed += 10;

		if (moveDown) {
			coordY += realSpeed;
		}
		if (moveUp) {
			coordY -= realSpeed;
		}
		if (moveLeft) {
			coordX -= realSpeed;
		}
		if (moveRight) {
			coordX += realSpeed;
		}
	}

	public void keyPressed() {
		if (keyCode == 'W' || keyCode == UP) {
			moveUp = true;
		}
		if (keyCode == 'S' || keyCode == DOWN) {
			moveDown = true;
		}
		if (keyCode == 'A' || keyCode == LEFT) {
			moveLeft = true;
		}
		if (keyCode == 'D' || keyCode == RIGHT) {
			moveRight = true;
		}
		if (key == CODED && keyCode == SHIFT) {
			shiftPressed = true;
		}
		if (key == CODED && keyCode == CONTROL) {
			ctrlPressed = true;
		}
	}

	@Override
	public void keyReleased() {
		if (keyCode == 'W' || keyCode == UP) {
			moveUp = false;
		}
		if (keyCode == 'S' || keyCode == DOWN) {
			moveDown = false;
		}
		if (keyCode == 'A' || keyCode == LEFT) {
			moveLeft = false;
		}
		if (keyCode == 'D' || keyCode == RIGHT) {
			moveRight = false;
		}
		if (key == CODED && keyCode == SHIFT) {
			shiftPressed = false;
		}
		if (key == CODED && keyCode == CONTROL) {
			ctrlPressed = false;
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		int count = event.getCount();
		if (shiftPressed)
			count *= 3;
		if (ctrlPressed)
			count *= 10;

		numTiles += count;
		if (numTiles < 1) {
			numTiles = 1;
		}
		tileSize = (float) width / (float) numTiles;
	}
}