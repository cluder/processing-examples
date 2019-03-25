package example.fractals.mandelbrot;

import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * Mandelbrot example<br>
 * see <a href="https://www.youtube.com/watch?v=6z7GQewK-Ks" >The Coding Train -
 * Coding Challenge #21: Mandelbrot Set<\a>
 */
public class MandelbrotMain extends PApplet {

	public static void main(String[] args) {
		args = append(args, "--location=-1900,100");
		args = append(args, MandelbrotMain.class.getName());
		PApplet.main(args);
	}

	int lastFps = 0;

	@Override
	public void settings() {
		size(600, 600);
	}

	@Override
	public void setup() {
		pixelDensity(1);
		loadPixels();
		background(0);
		showFps();
	}

	float zoom = 1.f;
	float xOffset = 0;
	float yOffset = 0;

	@Override
	public void draw() {
		frameRate(60);

		updateOffset();

//		loadPixels();

		int maxIter = 100;
		for (float w = 0; w < width; w++) {
			for (float h = 0; h < height; h++) {

				float a = map(w, 0, width, -zoom + xOffset, zoom + xOffset);
				float b = map(h, 0, height, -zoom + yOffset, zoom + yOffset);

				float ca = a;
				float cb = b;

				int n = 0;

				while (n < maxIter) {
					float aa = a * a - b * b;
					float bb = 2 * a * b;

					a = aa + ca;
					b = bb + cb;

					if (abs(a + b) > 16) {
						break;
					}

					n++;
				}

				float brightness = map(n, 0, maxIter, 0, 255);

				if (n == maxIter) {
					// inside the mandelbrot
					brightness = 0;
				}

				float red = brightness;
				float green = brightness;
				float blue = 0;
				float alpha = 255;

				int pix = (int) (h * width + w);

				pixels[(int) pix] = color(red, green, blue, alpha);
			}
		}

		updatePixels();

		showFps();
	}

	private void updateOffset() {

		float offsetSpeed = 0.001f;
		if (mousePressed) {
			float diffX = dmouseX - mouseX;
			float diffY = dmouseY - mouseY;
			xOffset += offsetSpeed * zoom * diffX;
			yOffset += offsetSpeed * zoom * diffY;
		}

		if (key == 'r' && keyPressed) {
			System.out.println("reset");
			xOffset = 0;
			yOffset = 0;
			zoom = 1f;
		}
	}

	private void showFps() {
		showFps(false);
	}

	private void showFps(boolean override) {
		lastFps = (int) frameRate;
		fill(0);
		rectMode(CORNERS);
		rect(0, 0, 60, 25);
		fill(255, 255, 0);
		textSize(20);
		text(lastFps, 5, 20);
		textSize(15);
		text("zoom:" + zoom, 5, 40);
	}

	@Override
	public void keyPressed() {

	}

	@Override
	public void mouseWheel(MouseEvent event) {
		final float amount = event.getCount();
		if (amount < 0) {
			zoom *= 0.9f;
		} else {
			zoom *= 1.1f;
		}

	}

}
