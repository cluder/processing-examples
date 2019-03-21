package example.collision.pixel;

import java.awt.Color;

import example.collision.PointInTriangleExample2;
import processing.core.PApplet;
import processing.core.PVector;

public class TriangleObstacle {
	PApplet p;
	float posX;
	float posY;
	PVector p1;
	PVector p2;
	PVector p3;
	PVector center;
	float thisArea;

	public TriangleObstacle(PApplet p, float posX, float posY, float size) {
		this.p = p;
		this.posX = posX;
		this.posY = posY;

		p1 = new PVector(posX, posY); // top
		p2 = new PVector(posX + size, posY + size); // right
		p3 = new PVector(posX - size, posY + size); // left

		// center point of this triangle
		center = calcCenter();

		// area of this triangle
		thisArea = triangleArea(p1, p2, p3);
	}

	/**
	 * Draw this triangle.
	 */
	public void draw(PApplet p) {
		p.fill(255);
		p.triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
	}

	/**
	 * Checks if the point 'p' is inside this triangle.<br>
	 * See {@link PointInTriangleExample2}
	 */
	public boolean isPointInTriangle(PVector p) {
		// create 3 triangles.
		// each triangle is create from 2 edges of this triangle, to the point we want
		// to check.
		// if the sum of those tree triangles is greater than our area, the point must
		// be outside of this triangle
		float t1Area = triangleArea(p, p2, p3);
		float t2Area = triangleArea(p, p3, p1);
		float t3Area = triangleArea(p, p2, p1);

		// sum of the area of the check-triangles
		final float t123Area = t1Area + t2Area + t3Area;
		if (t123Area > thisArea) {
			// sum is greater - point must be outside
			return false;
		} else {
			// point p is inside this triangle
			return true;
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

		debugDraw(p1, p2, p3);
		return (0.5f * Math.abs((a * d) - (b * c)));
	}

	/**
	 * Draws debug info
	 */
	private void debugDraw(PVector p1, PVector p2, PVector p3) {
		boolean debug = true;
		if (debug) {
			p.push();
			// draw center

			// draw check triangles
			p.stroke(Color.yellow.getRGB(), 1);
			p.noFill();

			p.point(center.x, center.y);
			p.triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);

			p.pop();
		}
	}

	public PVector calcCenter() {
		float cX = (p1.x + p2.x + p3.x) / 3;
		float cY = (p1.y + p2.y + p3.y) / 3;
		return new PVector(cX, cY);
	}

	public PVector getCenter() {
		return center;
	}
}
