package example.renderer;

import processing.core.PApplet;

/**
 * Example using the jogl renderer.<br>
 */
public class P3DRendererExample extends PApplet {
	float posX = 300;
	float posY = 300;
	float posZ = 0;
	float vel = 1;

	float rotation = 10;

	public static void main(String[] args) {
		PApplet.main(P3DRendererExample.class);
	}

	@Override
	public void settings() {
		// use P3d (jogl)
		size(640, 480, P3D);
	}

	@Override
	public void setup() {
		frameRate(30);
	}

	@Override
	public void draw() {
		background(0);

		// for a 3d scene, lights must be set
		// default ambient/directional light
		lights();

		// display text
		push();
		textSize(50);
		text("P3D render example", 100, 450);
		pop();

		updateSpherePos();

		drawObjects();
	}

	private void drawObjects() {
		// save and restore translation/rotation/fill modes etc.
		push();

		// a directional light from top right
		directionalLight(0, 0, 255, -1, 1, 0);

		// draw a sphere
		drawSphere();

		// draw shape using vertices
		drawShape();

		pop();
	}

	/**
	 * Draw shape using vertices.
	 */
	private void drawShape() {
		push();
		fill(150, 150, 150, 70); // gray - semi transparent
		stroke(150); // light gray wireframe

		translate(sketchWidth() / 2, 250, 0);

		rotateX(radians(90));
		rotateZ(radians(rotation));

		beginShape();
		vertex(-100, -100, -100);
		vertex(100, -100, -100);
		vertex(0, 0, 100);
		endShape();

		beginShape();
		vertex(100, -100, -100);
		vertex(100, 100, -100);
		vertex(0, 0, 100);
		endShape();

		beginShape();
		vertex(100, 100, -100);
		vertex(-100, 100, -100);
		vertex(0, 0, 100);
		endShape();

//		beginShape();
//		vertex(-100, 100, -100);
//		vertex(-100, -100, -100);
//		vertex(0, 0, 100);
//		endShape();

		pop();
	}

	private void drawSphere() {

		push();
		fill(255);
		stroke(120);
		translate(width / 2, posY, posZ);
		rotateY(radians(rotation)); // rotate Y axis
		sphereDetail(30, 30);
		sphere(50);
		pop();
	}

	/**
	 * Play with the spheres z position.
	 */
	private void updateSpherePos() {
		rotation++;
		rotation %= 360;

		if (posY >= 100) {
			vel = -0.5f;
		}
		if (posY <= 50) {
			vel = +0.5f;
		}
		posY += vel;
		System.out.println(posY);
	}
}
