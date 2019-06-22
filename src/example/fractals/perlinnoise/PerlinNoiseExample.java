package example.fractals.perlinnoise;

import processing.core.PApplet;

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
	}

	// world coordinates of the top left tile, can be negative
	float coordX = 500;
	float coordY = -1000;
	// scroll speed
	float scrollSpeed = 0.2f;

	private boolean moveUp;
	private boolean moveDown;
	private boolean moveLeft;
	private boolean moveRight;

	float noiseVal;
	float noiseScale = 0.02f;

	@Override
	public void draw() {
		// fixed seed to get a deterministic map
		noiseSeed(2);
		// detail
		noiseDetail(4, 0.5f);

		// set number of tiles / tilesize
		numTiles = 75;
		tileSize = width / numTiles;

		// move map on keypress (asdw)
		scrollSpeed = 2f;
		move();

		// generate and draw each tile
		for (int tileX = 0; tileX < numTiles; tileX++) {
			for (int tileY = 0; tileY < numTiles; tileY++) {
				// prepare noise input values
				float noiseScale = 0.02f;
				final float noiseInputX = (coordX + tileX) * noiseScale;
				final float noiseInputY = (coordY + tileY) * noiseScale;

				System.out.println(
						"coordx/y:" + coordX + "," + coordY + " | noise input:" + noiseInputX + ": " + noiseInputY);

				// generate noise value
				// TODO: cache recent values for the last few x/y values
				float noise = noise(noiseInputX, noiseInputY);

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
	}

	/**
	 * move the playfiled by modifying the world coords.
	 */

	private void move() {
		if (moveDown) {
			coordY += scrollSpeed;
		}
		if (moveUp) {
			coordY -= scrollSpeed;
		}
		if (moveLeft) {
			coordX -= scrollSpeed;
		}
		if (moveRight) {
			coordX += scrollSpeed;
		}

	}

	public void keyPressed() {
		if (key == 'w') {
			moveUp = true;
		}
		if (key == 's') {
			moveDown = true;
		}
		if (key == 'a') {
			moveLeft = true;
		}
		if (key == 'd') {
			moveRight = true;
		}

	}

	public void keyReleased() {
		if (key == 'w') {
			moveUp = false;
		}
		if (key == 's') {
			moveDown = false;
		}
		if (key == 'a') {
			moveLeft = false;
		}
		if (key == 'd') {
			moveRight = false;
		}
	}
}