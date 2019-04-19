package example.fractals.tree;

import processing.core.PApplet;

/**
 * Fractal tree created by recursion.<br>
 * see https://www.youtube.com/watch?v=0jjeOYMjmDU
 */
public class FractalTreeExample extends PApplet {

	public static void main(String[] args) {
//		args = append(args, "--location=-1900,100");
		args = append(args, FractalTreeExample.class.getName());
		PApplet.main(args);
	}

	@Override
	public void settings() {
		size(600, 500);
	}

	@Override
	public void setup() {

	}

	@Override
	public void draw() {
		background(0);
		frameRate(0.5f);

		translate(width / 2, height * 0.95f);
		drawBranch(23, 1, 100);
	}

	int maxIterations = 5;

	private void drawBranch(float angle, int depth, float height) {
		if (height < 2) {
			return;
		}

		stroke(50, 250, 100, 255 - depth * 15);
		strokeWeight(max(10 - depth * 2, 1));
		height *= 0.86f;
		line(0, 0, 0, 0 - height);
		depth++;

		push();
		translate(0, -height);
		rotate(radians(random(angle, angle + 4)));
		drawBranch(angle, depth, height * random(0.85f, 0.99f));
		pop();

		if (depth > 2 && random(1) < 0.1f) {
			return;
		}

		push();
		translate(0, -height);
		rotate(radians(random(-angle, -angle + 3)));
		drawBranch(angle, depth, height * random(0.8f, 0.99f));
		pop();
	}

}
