package example.fractals.tree;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Fractal tree with branches kept in a list for easier modification.<br>
 * see https://www.youtube.com/watch?v=0jjeOYMjmDU
 */
public class FractalTreeExample2 extends PApplet {

	public static void main(String[] args) {
//		args = append(args, "--location=-1900,100");
		args = append(args, FractalTreeExample2.class.getName());
		PApplet.main(args);
	}

	class Branch {
		PVector start;
		PVector end;

		boolean endpoint = true;
		int level = 1;

		public Branch(PVector start, PVector end) {
			this.start = start;
			this.end = end;
		}

		// create new branches
		public Branch branch(float angle) {
			this.endpoint = false;

			PVector direction = PVector.sub(end, start);
			direction.rotate(radians(angle));
			direction.mult(0.8f);

			PVector newEnd = PVector.add(end, direction);
			Branch newBranch = new Branch(end, newEnd);
			newBranch.level = this.level + 1;
			return newBranch;
		}

		public void draw() {
			stroke(100, 150, 150);

			strokeWeight(max(8 - level, 1));

			line(start.x, start.y, end.x, end.y);

			if (endpoint && level > 3) {
				fill(180, 240, 120, 160);
				noStroke();
				circle(end.x, end.y + 5, 10);
			}
		}
	}

	List<Branch> tree = new ArrayList<>();

	@Override
	public void settings() {
		size(600, 500);
	}

	@Override
	public void setup() {
		initTree();
	}

	private void initTree() {
		Branch root = new Branch(new PVector(width / 2, height), new PVector(width / 2, height - 100));
		tree.add(root);
	}

	@Override
	public void draw() {
		background(0);
		frameRate(30f);

		for (Branch b : tree) {
			b.draw();
		}

		jitter();

		if (keyPressed && key == ' ') {
			tree.clear();
			initTree();
		}
	}

	private void jitter() {
		for (Branch b : tree) {
			if (b.endpoint == false) {
				continue;
			}
			b.end.x += random(-1, 1);
			b.end.y += random(-1, 1);
		}
	}

	@Override
	public void mousePressed() {
		if (mouseButton == LEFT) {
			// add mew endpoints
			List<Branch> newBranches = new ArrayList<>();
			for (Branch b : tree) {
				if (b.endpoint == false) {
					continue;
				}
				Branch a = b.branch(random(45));
				newBranches.add(a);

				a = b.branch(random(-45));
				newBranches.add(a);
			}
			tree.addAll(newBranches);
		}
	}

	@Override
	public void mouseClicked() {

	}
}
