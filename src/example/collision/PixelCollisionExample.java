package example.collision;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Collision check between img and obstacles.<br>
 * Only non-transparent pixels of the image are used for collision checks.
 */
public class PixelCollisionExample extends PApplet {

	class Triangle {
		float posX;
		float posY;
		PVector p1;
		PVector p2;
		PVector p3;
		float thisArea;

		public Triangle(float posX, float posY) {
			this.posX = posX;
			this.posY = posY;

			int size = 80;
			p1 = new PVector(posX, posY); // top
			p2 = new PVector(posX + size, posY + size); // right
			p3 = new PVector(posX - size, posY + size); // left

			// area of this triangle
			thisArea = triangleArea(p1, p2, p3);
		}

		public void draw() {
			fill(255);
			triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
		}

		/**
		 * Checks if the point 'p' is inside this triangle.
		 */
		public boolean isPointInTriangle(PVector p) {
			// create 3 triangles from the edges of this triangle, to the point we want to
			// check.
			// if the sum of those 3 triangles is greater than our area, the point must be
			// outside of this triangle
			float t1Area = triangleArea(p, p2, p3);
			float t2Area = triangleArea(p, p3, p1);
			float t3Area = triangleArea(p, p2, p1);

			final float t123Area = t1Area + t2Area + t3Area;
			if (thisArea == t123Area) {
				// point p is inside this triangle
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Calculates the area of a triangle.
		 */
		float triangleArea(PVector p1, PVector p2, PVector p3) {
			float a, b, c, d;
			a = p1.x - p2.x;
			b = p1.y - p2.y;
			c = p3.x - p2.x;
			d = p3.y - p2.y;
			return (0.5f * abs((a * d) - (b * c)));
		}
	}

	PImage pepe;
	PImage pepeSad;
	PImage imgToDraw;

	Triangle t1;

	public static void main(String[] args) {
		String[] newArgs = append(args, "--location=-1900,400");
		newArgs = append(newArgs, PixelCollisionExample.class.getName());
		PApplet.main(newArgs);
	}

	@Override
	public void settings() {
		size(500, 500);
	}

	@Override
	public void setup() {
		frameRate(30);

		// load images
		pepe = loadImage("resources/pepe.png");
		pepeSad = loadImage("resources/pepe_bad.png");
		imgToDraw = pepe;

		// create obstacle
		t1 = new Triangle(200, 200);
	}

	@Override
	public void draw() {
		background(50);
		fill(50);

		drawObstacles();

		drawImage();

		checkCollision();
	}

	/**
	 * Check every pixel of the image, if it is inside the triangle.<br>
	 * This check should only be done, if the two objects a re near each other.
	 */
	private void checkCollision() {
		final int pixW = imgToDraw.pixelWidth;
		final int pixH = imgToDraw.pixelHeight;

		// for every pixel, get the alpha value
		for (int w = 0; w < pixW; w++) {
			for (int h = 0; h < pixH; h++) {
				final int pixel = imgToDraw.get(w, h);
				int a = (pixel >> 24) & 255;

				// if the alpha value is above a threshold, we check the pixel
				if (a > 20) {
					final int pixX = mouseX + w;
					final int pixY = mouseY + h;
					final PVector pointToCheck = new PVector(pixX, pixY);

					if (t1.isPointInTriangle(pointToCheck)) {
						// found a pixel colliding with the triangle
						imgToDraw = pepeSad;
						return;
					} else {
						imgToDraw = pepe;
					}
				}
			}
		}
	}

	private void drawObstacles() {
		t1.draw();
	}

	private void drawImage() {
		image(imgToDraw, mouseX, mouseY);
	}

}
