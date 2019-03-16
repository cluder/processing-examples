package example.renderer;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

/**
 * Example using the jogl renderer.<br>
 */
public class P3DRendererExample extends PApplet {
	float posX = 300;
	float posY = 100;
	float posZ = 0;
	float vel = 1;

	float rotation = 10;

	// shape and textures
	PShape sphere;
	PImage tex1;
	PImage tex2;

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

		posX = 300;
		posY = 100;
		posZ = 0;
		vel = 1;

		// load textures
		tex1 = loadImage("resources/textures/texture2.png");
		tex2 = loadImage("resources/textures/texture3.png");

		// create a textured sphere shape
		sphere = createShape(SPHERE, 50);
		sphere.setTexture(tex2);
		sphere.setStroke(false);
	}

	@Override
	public void draw() {
		background(0, 50, 80, 0);

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

	@Override
	public void mouseDragged() {
		// move camera view with mouse
		camera(mouseX, height / 2, (height / 2) / tan(PI / 6), // eye x,y,z
				mouseX, height / 2, 0, // center x,y,z
				0, 1, 0);
	}

	private void drawObjects() {
		// save and restore translation/rotation/fill modes etc.
		push();

		// a directional blue light from top right
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

		// black outline
		strokeWeight(2);
		stroke(0); //

		translate(sketchWidth() / 2, 250, 0);

		rotateX(radians(90));
		rotateZ(radians(rotation));

		// top triangle (unrotated)
		beginShape();
		texture(tex1);
		vertex(-100, -100, -100, 0, 0); // ok
		vertex(100, -100, -100, 512, 0); // ok
		vertex(0, 0, 100, 256, 512);
		endShape();

		// right triangle
		beginShape();
		texture(tex1);
		vertex(100, -100, -100, 512, 0);
		vertex(100, 100, -100, 0, 0);
		vertex(0, 0, 100, 256, 512);
		endShape();

		// bottom triangle
		beginShape();
		texture(tex1);
		vertex(100, 100, -100, 0, 0);
		vertex(-100, 100, -100, 512, 0);
		vertex(0, 0, 100, 256, 512);
		endShape();

		// left triangle
		beginShape();
		texture(tex1);
		vertex(-100, 100, -100, 512, 0);
		vertex(-100, -100, -100, 0, 0);
		vertex(0, 0, 100, 256, 512);
		endShape();

		pop();
	}

	/**
	 * draw a sphere, which was created during setup().
	 */
	private void drawSphere() {
		push(); // save rotation / translation

		noStroke();
		translate(width / 2, posY, posZ);
		rotateY(radians(rotation)); // rotate Y axis

		shape(sphere);

		pop(); // restore rotation / translation
	}

	/**
	 * Play with the spheres position.
	 */
	private void updateSpherePos() {
		rotation++;
		rotation %= 360;

		if (posY >= 130) {
			vel = -0.5f;
		}
		if (posY <= 80) {
			vel = +0.5f;
		}
		posY += vel;
	}
}
