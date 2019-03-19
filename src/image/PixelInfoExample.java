package image;

import processing.core.PApplet;
import processing.core.PImage;

public class PixelInfoExample extends PApplet {

	public static void main(String[] args) {
		PApplet.main(PixelInfoExample.class);
	}

	@Override
	public void settings() {
	}

	@Override
	public void setup() {
		PImage img = loadImage("resources/pepe.png");
		System.out.println("density:" + img.pixelDensity);
		System.out.println("pixelWidth:" + img.pixelWidth);
		System.out.println("pixelHeight:" + img.pixelHeight);

		for (int i = 0; i < img.pixelWidth; i++) {
			for (int j = 0; j < img.pixelHeight; j++) {
				final int pixel = img.get(i, j);

				int a = (pixel >> 24) & 255;
				int r = (pixel >> 16) & 255;
				int g = (pixel >> 8) & 255;
				int b = pixel & 255;

				System.out.println(String.format("[%s][%s]: a:%s, %s %s %s", i, j, a, r, g, b));
			}
		}

		// dont start draw loop
		exit();
	}
}
