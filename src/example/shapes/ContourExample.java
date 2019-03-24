package example.shapes;

import processing.core.PApplet;
import processing.core.PShape;

/**
 * Example on how to use contours.<br>
 * see also issue #4978 https://github.com/processing/processing/issues/4978.
 */
public class ContourExample extends PApplet {
	public static void main(String[] args) {
		PApplet.main(ContourExample.class);
	}

	@Override
	public void settings() {
		size(640, 480, FX2D);
	}

	public void setup() {
		fill(255, 0, 0);

		translate(50, 50);

		beginShape();
		fill(255, 0, 0);
		vertex(0, 0);
		vertex(100, 0);
		vertex(100, 100);
		vertex(0, 100);
		beginContour();
		vertex(25, 25);
		vertex(25, 75);
		vertex(75, 75);
		vertex(75, 25);
		endContour();
		endShape(CLOSE);

		translate(200, 0);

		PShape s = createShape();
		s.beginShape();
		s.fill(255, 0, 0);
		s.vertex(0, 0);
		s.vertex(100, 0);
		s.vertex(100, 100);
		s.vertex(0, 100);
		s.beginContour();
		s.vertex(25, 25);
		s.vertex(25, 75);
		s.vertex(75, 75);
		s.vertex(75, 25);
		s.endContour();
		s.endShape(CLOSE);
		shape(s, 0, 0);
	}

	@Override
	public void draw() {
		fill(255, 0, 0);

		translate(50, 50);

		beginShape();
		fill(255, 0, 0);
		vertex(0, 0);
		vertex(100, 0);
		vertex(100, 100);
		vertex(0, 100);
		beginContour();
		vertex(25, 25);
		vertex(25, 75);
		vertex(75, 75);
		vertex(75, 25);
		endContour();
		endShape(CLOSE);

		translate(200, 0);

		PShape s = createShape();
		s.beginShape();
		s.fill(255, 0, 0);
		s.vertex(0, 0);
		s.vertex(100, 0);
		s.vertex(100, 100);
		s.vertex(0, 100);
		s.beginContour();
		s.vertex(25, 25);
		s.vertex(25, 75);
		s.vertex(75, 75);
		s.vertex(75, 25);
		s.endContour();
		s.endShape(CLOSE);
		shape(s, 0, 0);

	}
}
