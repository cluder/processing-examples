package example.fractals.sierpinski;

import processing.core.PApplet;
import processing.core.PVector;

public class SierpinskiTriangle extends PApplet {
	class Triangle {
		// top
		PVector a;
		// right
		PVector b;
		// left
		PVector c;

		public void draw() {
			triangle(a.x, a.y, //
					b.x, b.y, //
					c.x, c.y);
		}
	}

	public static void main(String[] args) {
//			args = append(args, "--location=-1900,100");
		args = append(args, SierpinskiTriangle.class.getName());
		PApplet.main(args);
	}

	@Override
	public void settings() {
		size(900, 800);
	}

	@Override
	public void setup() {
		int pad = 5;

		t.a = new PVector(width / 2, pad);
		t.b = new PVector(width - pad, height - pad);
		t.c = new PVector(pad, height - pad);
	}

	Triangle t = new Triangle();

	@Override
	public void draw() {
		background(255, 255);
		noStroke();

		frameRate(5f);

		fill(0);
		t.draw();
		subdivide(t);
	}

	boolean isFirst = true;

	public void subdivide(Triangle t) {
		// right
		Triangle sub = new Triangle();
		sub.a = new PVector();
		sub.a.x = (t.a.x + t.b.x) / 2f;
		sub.a.y = (t.a.y + t.b.y) / 2f;

		// min triangle length - recursion abort
		if (abs(t.a.x - t.b.x) < 8) {
			return;
		}

		// bot
		sub.b = new PVector();
		sub.b.x = (t.b.x + t.c.x) / 2f;
		sub.b.y = (t.b.y + t.c.y) / 2f;

		// left
		sub.c = new PVector();
		sub.c.x = (t.c.x + t.a.x) / 2f;
		sub.c.y = (t.c.y + t.a.y) / 2f;

		fill(255, 255);
		sub.draw();

		Triangle top = new Triangle();
		top.a = t.a;
		top.b = sub.a;
		top.c = sub.c;
		subdivide(top);

		Triangle left = new Triangle();
		left.a = sub.a;
		left.b = t.b;
		left.c = sub.b;
		subdivide(left);

		Triangle right = new Triangle();
		right.a = sub.c;
		right.b = sub.b;
		right.c = t.c;
		subdivide(right);

//		subdivide(sub);
	}
}
