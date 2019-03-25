package example.image;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class PixelInfoExample extends PApplet {

	public static void main(String[] args) {
		PApplet.main(PixelInfoExample.class);
	}

	class PixelInfo {
		public PixelInfo(float x, float y, float r, float g, float b, float a) {
			super();
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;

			this.x = x;
			this.y = y;
			this.ox = x;
			this.oy = y;
		}

		float x;
		float y;

		float ox;
		float oy;

		float r;
		float g;
		float b;
		float a;
	}

	List<PixelInfo> px = new ArrayList<>();

	@Override
	public void settings() {
		size(560, 560);
	}

	@Override
	public void setup() {
		PImage img = loadImage("resources/pepe_s.png");
		int size = 300;
		int posX = (width / 2) - size / 2;
		int posY = (height / 2) - size / 2;
		for (int i = 0; i < img.pixelWidth; i++) {
			for (int j = 0; j < img.pixelHeight; j++) {
				final int pixel = img.get(i, j);

				int a = (pixel >> 24) & 255;
				int r = (pixel >> 16) & 255;
				int g = (pixel >> 8) & 255;
				int b = pixel & 255;

				final PixelInfo p = new PixelInfo(map(i, 0, img.pixelWidth, posX, posX + size),
						map(j, 0, img.pixelHeight, posY, posY + size), r, g, b, a);
				px.add(p);
			}
		}
	}

	@Override
	public void draw() {
		background(0);
		int maxDist = 80;

		for (PixelInfo p : px) {
			float dist = dist(mouseX, mouseY, p.x, p.y);
			float distX = mouseX - p.x;
			float distY = mouseY - p.y;

			if (mousePressed) {

				if (abs(dist) < maxDist) {
					final float force = 1.2f;
					p.x += map(-distX, -maxDist, maxDist, -force, force);
					p.y += map(-distY, -maxDist, maxDist, -force, force);
				}
			}

			p.x = lerp(p.x, p.ox, 0.05f);
			p.y = lerp(p.y, p.oy, 0.05f);

			stroke(p.r, p.g, p.b, p.a);
			point(p.x, p.y);
		}
	}
}
