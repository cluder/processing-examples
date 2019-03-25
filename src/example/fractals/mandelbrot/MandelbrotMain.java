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
		size(500, 500);
	}

	@Override
	public void setup() {
		pixelDensity(1);
		loadPixels();
		background(0);
		showFps();
	}

	double whRatio;
	double zoom = 1.f;
	int zoomSpeed = 0;
	double xOffset = 0;
	double yOffset = 0;
	int maxIter = 50;

	static public final double mapDouble(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	@Override
	public void draw() {
		whRatio = (double) width / height;
//		maxIter = 1000;
//		yOffset = 0;
//		zoom = EPSILON;
		frameRate(20);
//		background(0);

		updateOffset();
		zoom += zoomSpeed * 0.01f * zoom;
//		loadPixels();

		for (double w = 0; w < width; w++) {
			for (double h = 0; h < height; h++) {

				double a = mapDouble(w, 0, width, (-zoom * whRatio) + xOffset, (zoom * whRatio) + xOffset);
				double b = mapDouble(h, 0, height, -zoom + yOffset, zoom + yOffset);
//				double a = map(w, 0, width, -zoom + xOffset, zoom + xOffset);
//				double b = map(h, 0, height, -zoom + yOffset, zoom + yOffset);

				double ca = a;
				double cb = b;

				int n = 0;

				while (n < maxIter) {
					double aa = a * a - b * b;
					double bb = 2 * a * b;

					a = aa + ca;
					b = bb + cb;

					if (abs((float) (a + b)) > 16) {
						break;
					}

					n++;
				}

//				float brightness = map(n, 0, maxIter, 0, 255);
				float brightness = map(sqrt(n), 0, sqrt(maxIter), 0, 255);

				if (n == maxIter) {
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
		float maxItrIncrease = 0;
		double offsetSpeed = 0.001f;
		if (mousePressed) {
			double diffX = dmouseX - mouseX;
			double diffY = dmouseY - mouseY;
			xOffset += offsetSpeed * zoom * diffX;
			yOffset += offsetSpeed * zoom * diffY;
		}

		if (key == 'r' && keyPressed) {
			System.out.println("reset");
			xOffset = 0;
			yOffset = 0;
			zoom = 1f;
			maxIter = 100;
		}
		final float increase = .03f;
		if (key == '+' && keyPressed) {
			maxItrIncrease = max(maxIter * increase, 1);
			maxIter += maxItrIncrease;
		}
		if (key == '-' && keyPressed) {
			maxItrIncrease = max(maxIter * increase, 1);
			maxIter -= maxItrIncrease;
			if (maxIter <= 0) {
				maxIter = 1;
			}
		}
	}

	private void showFps() {
		showFps(false);
	}

	private void showFps(boolean override) {
		lastFps = (int) frameRate;
//		fill(0);
//		rectMode(CORNERS);
//		rect(0, 0, 60, 25);
//		fill(255, 255, 0);
		textSize(20);
		text("FPS:" + lastFps, 5, 20);
		textSize(15);
		text("zoom:" + zoom, 5, 40);
		text("zoom speed:" + zoomSpeed, 5, 60);
		text("iterations:" + maxIter, 5, 80);
	}

	@Override
	public void keyPressed() {

	}

	@Override
	public void mouseWheel(MouseEvent event) {
		final double amount = event.getCount();
		double speedStep = 0.01f;
		zoomSpeed += amount;
//		if (amount < 0) {
//			zoom *= 0.9f;
//		} else {
//			zoom *= 1.1f;
//		}
	}

}
