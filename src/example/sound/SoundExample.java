package example.sound;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.sound.SoundFile;

/**
 * Plays a sound file, when the mouse is clicked.
 */
public class SoundExample extends PApplet {
	public static void main(String[] args) {
		PApplet.main(SoundExample.class);
	}

	SoundFile leftClick;
	SoundFile rightClick;
	SoundFile middleClick;

	@Override
	public void settings() {
		setSize(640, 480);
	}

	@Override
	public void setup() {
		frameRate(30);

		// loads mp3/wav files
		leftClick = new SoundFile(this, "resources/sounds/beep.mp3");
		rightClick = new SoundFile(this, "resources/sounds/beep.wav");
		middleClick = new SoundFile(this, "resources/sounds/beep1.mp3");
	}

	@Override
	public void draw() {
		background(0);

		textSize(25);
		text("Click to play sound", 100, 100);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		super.mousePressed(event);
		switch (event.getButton()) {
		case LEFT:
			leftClick.play();
			break;
		case RIGHT:
			rightClick.play();
			break;
		case CENTER:
			middleClick.play();
			break;
		}
	}
}
