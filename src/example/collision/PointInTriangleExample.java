package example.collision;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Method to check if a given point is inside a triangle by SebLague.<br>
 * 
 * see https://github.com/SebLague/Gamedev-Maths/blob/master/PointInTriangle.cs.
 */
public class PointInTriangleExample extends PApplet {
	PVector lastClick = new PVector(50, 50);

	class Triangle {
		PVector a;
		PVector b;
		PVector c;
	}

	Triangle t;

	public static void main(String[] args) {
		PApplet.main(PointInTriangleExample.class, args);
	}

	@Override
	public void settings() {
		size(300, 300);
	}

	@Override
	public void setup() {
		t = new Triangle();
		textSize(15);
		fill(255);
	}

	private void setTriangleValues() {
		// left
		float x = width * 0.2f;
		float y = height * .5f;
		t.a = new PVector(x, y);

		// top
		x = width / 2;
		y = height * 0.1f;
		t.b = new PVector(x, y);

		// right
		x = width * 0.8f;
		y = height * .5f;
		t.c = new PVector(x, y);
	}

	@Override
	public void draw() {
		setTriangleValues();
		background(20);

		stroke(255);
		triangle(t.a.x, t.a.y, //
				t.b.x, t.b.y, //
				t.c.x, t.c.y);

		text("A", t.a.x - 25, t.a.y);
		text("B", t.b.x - 25, t.b.y);
		text("C", t.c.x + 20, t.c.y);

		noStroke();

		drawClickedSpot();

		// check if last clicked spot is inside triangle
		final boolean pointInTriangle = isPointInTriangle(t.a, t.b, t.c, lastClick);
		if (pointInTriangle) {
			fill(255, 0, 0);
		} else {
			fill(255);
		}
	}

	// draw last clicked point as red point
	private void drawClickedSpot() {
		push();
		fill(50, 100, 255);
		circle(lastClick.x, lastClick.y, 5);
		pop();
	}

	/**
	 * Save last clicked spot
	 */
	@Override
	public void mouseDragged() {
		// save last clicked spot
		lastClick.x = mouseX;
		lastClick.y = mouseY;
	}

	/**
	 * Save last clicked spot
	 */
	@Override
	public void mouseClicked() {
		lastClick.x = mouseX;
		lastClick.y = mouseY;
	}

	// Determines whether point P is inside the triangle a,b,c
	public boolean isPointInTriangle(PVector a, PVector b, PVector c, PVector p) {
		float s1 = c.y - a.y;
		if (s1 == 0) {
			// avoid NPE later
			s1 = EPSILON;
		}
		float s2 = c.x - a.x;
		float s3 = b.y - a.y;
		float s4 = p.y - a.y;

		float w1 = (a.x * s1 + s4 * s2 - p.x * s1) / (s3 * s2 - (b.x - a.x) * s1);
		float w2 = (s4 - w1 * s3) / s1;

		// debug output
		fill(255);
		text("W1:     " + format(w1), 80, 200);
		text("W2:     " + format(w2), 80, 220);
		text("W1 +W2: " + format(w1 + w2), 80, 240);

		final boolean inTriangle = w1 >= 0 && w2 >= 0 && (w1 + w2) <= 1;

		return inTriangle;
	}

	private String format(float value) {
		return String.format("%.2f", value);
	}

}
