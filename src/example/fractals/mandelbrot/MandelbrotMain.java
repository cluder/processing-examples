package example.fractals.mandelbrot;

import java.text.DecimalFormat;

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
		size(800, 600);

	}

	@Override
	public void setup() {
		pixelDensity(1);
		loadPixels();
		background(0);
		frameRate(30);
		reset();
	}

	double whRatio;
	double zoom = 1.f;
	int zoomSpeed = 0; // 1,2,3 ...
	final float zoomSpeedFactor = 0.01f;
	double xOffset = 0;
	double yOffset = 0;
	int maxIterations = 1;

	static public final double mapDouble(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	@Override
	public void draw() {
		whRatio = (double) width / height;
//		maxIter = 1000;
//		xOffset = -0.7664847254471618;
//		yOffset = 0.1009221160007073;

//		zoom = EPSILON;
//		frameRate(15);
//		background(0);

		updateOffset();
		zoom += zoomSpeed * zoomSpeedFactor * zoom;
//		loadPixels();

		// for each pixel, map the x/y coordiante to the current zoom level
		// and check if the calculated value is in the mandelbrot set
		// x = real
		// y = imaginary
		for (double pixX = 0; pixX < width; pixX++) {
			for (double pixY = 0; pixY < height; pixY++) {

				// real part mapped from 0 - with to -zoom to +zoom
				double r = mapDouble(pixX, 0, width, (-zoom * whRatio) + xOffset, (zoom * whRatio) + xOffset);

				// imaginary part mapped from 0 - height to -zoom to +zoom
				double i = mapDouble(pixY, 0, height, -zoom + yOffset, zoom + yOffset);
//				double a = map(w, 0, width, -zoom + xOffset, zoom + xOffset);
//				double b = map(h, 0, height, -zoom + yOffset, zoom + yOffset);

				double cR = r;
				double cI = i;

				int iter = 0;

				// we use while, because we need the iteration counter later
				while (iter < maxIterations) {
					double rZ = r * r - i * i;
					double iZ = 2 * r * i;

					r = rZ + cR;
					i = iZ + cI;

					final float z = (float) (r - i);
					if (abs(z) >= 4) {
						// point does not belong to the mandelbrot set
						break;
					}

					iter++;
				}

				float brightness = 0;
				if (iter != maxIterations) {
					// point does not belong to the mandelbrot set
					// color is adjusted according to the number of iterations it took
					brightness = map(iter, 0, maxIterations, 50, 255);
//					brightness = 255;
				} else {
					// point does belong to the mandelbrot set
				}

				float red = brightness;
				float green = brightness;
				float blue = brightness;
				float alpha = 255;

				int pix = (int) (pixY * width + pixX);

				int color = ((int) alpha << 24 | ((int) red << 16) | ((int) green << 8) | (int) blue);

				pixels[pix] = color;
//				pixels[pix] = color(red, green, blue, alpha);
			}
		}

		updatePixels();

		showStats();
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
			reset();
		}

		final float increase = .03f;
		if (key == '+' && keyPressed) {
			maxItrIncrease = max(maxIterations * increase, 1);
			maxIterations += maxItrIncrease;
		}

		if (key == '-' && keyPressed) {
			maxItrIncrease = max(maxIterations * increase, 1);
			maxIterations -= maxItrIncrease;
			if (maxIterations <= 0) {
				maxIterations = 1;
			}
		}
		if (key == 'i' && keyPressed) {
			System.out.println("x: " + formatDouble(xOffset));
			System.out.println("Y: " + formatDouble(yOffset));
			System.out.println("zoom:" + formatDouble(zoom));
			System.out.println("iterations:" + maxIterations);

		}
	}

	private void reset() {
		xOffset = -1.4264062155965123;
		yOffset = -0.00000005075879463939667;
		zoom = 1;
		zoomSpeed = 0;
		maxIterations = 30;
	}

	private String formatDouble(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMaximumFractionDigits(500);
		return df.format(value);
	}

	private void showStats() {
		lastFps = (int) frameRate;

		fill(0, 100);
		rectMode(CORNERS);
		rect(0, 0, 400, 100);
		fill(255, 255, 0);

		textSize(20);
		text("FPS:" + lastFps, 5, 20);
		textSize(15);
		text("zoom:" + formatDouble(zoom), 5, 40);
		text("zoom speed:" + zoomSpeed, 5, 60);
		text("iterations:" + maxIterations, 5, 80);
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		final double amount = event.getCount();
		zoomSpeed += amount;
	}

}
