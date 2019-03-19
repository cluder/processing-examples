package example.collision;

import java.awt.Color;

import processing.core.PApplet;

/**
 * Checks if a given point (x,y) is inside a triangle, by creating additional
 * triangles and comparing the sum of their area.<br>
 * If the are is greater, the point must be outside the target-triangle.<br>
 * As long as the point is inside the target-triangle, the area will be the
 * same.
 */
public class PointInTriangleExample2 extends PApplet {
	public static void main(String[] args) {
		PApplet.main(PointInTriangleExample2.class);
	}

	class Triangle {
		float point1x;
		float point1y;
		float point2x;
		float point2y;
		float point3x;
		float point3y;

		Triangle(float point1x, float point1y, float point2x, float point2y, float point3x, float point3y) {
			this.point1x = point1x;
			this.point1y = point1y;
			this.point2x = point2x;
			this.point2y = point2y;
			this.point3x = point3x;
			this.point3y = point3y;
		}

		void drawTriangle() {
			fill(155, 155);
			triangle(point1x, point1y, point2x, point2y, point3x, point3y);
		}
	}

	Triangle t;

	@Override
	public void settings() {
		size(300, 300);
	}

	public void setup() {
		// setup the target triangle in the middle of the screen
		t = new Triangle(width / 2, height / 4, width - width / 4, height - height / 4, width / 4, height - height / 4);

		frameRate(30);
	}

	public void draw() {
		// clear background
		background(0);

		t.drawTriangle();

		// check if a given point is inside a triangle
		if (checkCollision(mouseX, mouseY, t)) {
			// if a collision is found, we add a yellow outline
			strokeWeight(4);
			stroke(Color.yellow.getRGB());
		} else {
			noStroke();
		}
	}

	/**
	 * does a collision check, by creating 3 triangles, originating from our target
	 * triangle to the given x/y coordinate (mouse cursor)
	 */
	boolean checkCollision(float x, float y, Triangle t) {
		float tArea, t1Area, t2Area, t3Area;

		// area of target triangle
		tArea = triangleArea(t.point1x, t.point1y, t.point3x, t.point3y, t.point2x, t.point2y);

		// areas of the 3 'check-triangles'
		t1Area = triangleArea(x, y, t.point2x, t.point2y, t.point3x, t.point3y);
		t2Area = triangleArea(x, y, t.point3x, t.point3y, t.point1x, t.point1y);
		t3Area = triangleArea(x, y, t.point2x, t.point2y, t.point1x, t.point1y);

		// inner check-triangles have no outline
		noStroke();

		final int alpha = 100;
		fill(Color.green.getRGB(), alpha);
		triangle(x, y, t.point2x, t.point2y, t.point3x, t.point3y);

		fill(Color.red.getRGB(), alpha);
		triangle(x, y, t.point3x, t.point3y, t.point1x, t.point1y);

		fill(Color.blue.getRGB(), alpha);
		triangle(x, y, t.point2x, t.point2y, t.point1x, t.point1y);

		float totalArea = t1Area + t2Area + t3Area;

		fill(255);
		text("triangle area: " + tArea, 50, height - 50);
		text("total area: " + totalArea, 50, height - 35);
		text("difference: " + (totalArea - tArea), 50, height - 20);

		return (totalArea <= tArea);
	}

	float triangleArea(float p1, float p2, float p3, float p4, float p5, float p6) {
		float a, b, c, d;
		a = p1 - p5;
		b = p2 - p6;
		c = p3 - p5;
		d = p4 - p6;
		return (0.5f * abs((a * d) - (b * c)));
	}
}
