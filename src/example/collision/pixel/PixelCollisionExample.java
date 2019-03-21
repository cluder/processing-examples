package example.collision.pixel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Collision check between img and obstacles.<br>
 * Only non-transparent pixels of the image are used for collision checks.
 */
public class PixelCollisionExample extends PApplet {
	PImage pepe;
	PImage pepeSad;
	PImage img;

	List<TriangleObstacle> triangles;

	public static void main(String[] args) {
		// open on left monitor
		args = append(args, "--location=-1900,400");
		args = append(args, PixelCollisionExample.class.getName());
		PApplet.main(args);
	}

	@Override
	public void settings() {
		size(500, 400);
	}

	@Override
	public void setup() {
		frameRate(30);

		// load images
		pepe = loadImage("resources/pepe.png");
		pepeSad = loadImage("resources/pepe_bad.png");
		img = pepe;

		triangles = new ArrayList<>();
		// create obstacles
		for (int i = 1; i <= 6; i += 2) {
			TriangleObstacle t = new TriangleObstacle(this, i * 100, 200, 30);
			triangles.add(t);
		}
	}

	@Override
	public void draw() {
		background(0);

		triangles = new ArrayList<>();
		// create obstacles
		for (int i = 1; i <= 6; i += 2) {
			TriangleObstacle t = new TriangleObstacle(this, i * 70, 200, 30);
			triangles.add(t);
		}

		drawObstacles();

		drawImage();

		boolean collisionn = checkCollision();
		if (collisionn) {
			textSize(25);
			text("collision detected !", 50, height * 0.8f);
			img = pepeSad;
		} else {
			img = pepe;
		}
	}

	/**
	 * This check should only be done, if the objects are near each other.
	 */
	private boolean checkCollision() {

		// get coordinates of non-transparent pixels from this image
		final List<PVector> pixelsToCheck = getNonTransparentPixels(mouseX, mouseY, pepe, 20);

		// check if we are near an obstacle
		for (TriangleObstacle t : triangles) {
			final int imgCenterX = mouseX + img.width / 2;
			final int imgCenterY = mouseY + img.height / 2;

			point(imgCenterX, imgCenterY);

			float dist = dist(imgCenterX, imgCenterY, t.getCenter().x, t.getCenter().y);
			if (dist > 100) {
				// this triangle is too far, skip pixel perfect test
				continue;
			}
			// for every non-transparent pixel, check if its inside an obstacle
			for (PVector pix : pixelsToCheck) {
				if (t.isPointInTriangle(pix)) {
					// found a pixel colliding with the triangle
					return true;
				} else {
				}
			}
		}
		return false;
	}

	/**
	 * Returns all non-transparent pixels of this image.
	 */
	List<PVector> getNonTransparentPixels(int posX, int posY, PImage img, int threshold) {
		List<PVector> pixels = new ArrayList<>();

		// for every pixel, get the alpha value
		for (int w = 0; w < img.pixelWidth; w += 2) {
			for (int h = 0; h < img.pixelHeight; h += 2) {
				final int pixel = img.get(w, h);
				int a = (pixel >> 24) & 255;

				// if the alpha value is above a threshold, we check the pixel
				if (a > threshold) {
					pixels.add(new PVector(posX + w, posY + h));
				}
			}
		}

		return pixels;
	}

	private void drawObstacles() {
		for (TriangleObstacle t : triangles) {
			t.draw(this);
		}
	}

	private void drawImage() {
		push();

		image(img, mouseX, mouseY);

		// draw center point
		final int imgCenterX = mouseX + img.width / 2;
		final int imgCenterY = mouseY + img.height / 2;

		stroke(Color.yellow.getRGB());
		strokeWeight(3);
		point(imgCenterX, imgCenterY);

		pop();
	}

}
