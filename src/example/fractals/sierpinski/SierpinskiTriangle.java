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
		background(255, 255);
	}

	Triangle t = new Triangle();

	@Override
	public void draw() {
		background(255, 255);
		noStroke();

		frameRate(5f);

		fill(0);
		t.draw();
		subdivide(t, 0);

		textSize(20);
		textAlign(PApplet.LEFT, PApplet.CENTER);
		text("Levels:" + maxLevel, 20, 20);
	}

	boolean isFirst = true;
	int maxLevel = 7;

	public void subdivide(Triangle t, int level) {
		level++;
		// right
		Triangle sub = new Triangle();
		sub.a = new PVector();
		sub.a.x = (t.a.x + t.b.x) / 2f;
		sub.a.y = (t.a.y + t.b.y) / 2f;

		// min triangle length - recursion abort
		if (level >= maxLevel) {
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

		final int color = 255 - level * 25;
//		switch (level) {
//		case 1:
//			fill(color, color, 0);
//			break;
//		case 2:
//			fill(0, color, color);
//			break;
//		case 3:
//			fill(color, 0, color);
//			break;
//		case 4:
//			fill(color, color, 0);
//			break;
//		case 5:
//			fill(0, color, color);
//			break;
//		case 6:
//			fill(color, 0, color);
//			break;
//		default:
//			fill(color, color, color);
//			break;
//		}
		fill(color, color, color);
		sub.draw();

		Triangle top = new Triangle();
		top.a = t.a;
		top.b = sub.a;
		top.c = sub.c;
		subdivide(top, level);

		Triangle left = new Triangle();
		left.a = sub.a;
		left.b = t.b;
		left.c = sub.b;
		subdivide(left, level);

		Triangle right = new Triangle();
		right.a = sub.c;
		right.b = sub.b;
		right.c = t.c;
		subdivide(right, level);

//		subdivide(sub);
	}

	@Override
	public void keyPressed() {
		if (keyPressed && keyCode == PApplet.UP) {
			maxLevel++;
		}
		if (keyPressed && keyCode == PApplet.DOWN) {
			maxLevel--;
		}
	}
}
