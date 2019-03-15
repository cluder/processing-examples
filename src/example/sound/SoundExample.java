package example.sound;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.sound.SoundFile;

/**
 * Plays sound files, when the mouse is clicked.
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
		size(640, 480);
	}

	@Override
	public void setup() {
		frameRate(30);

		// loads mp3/wav files
		leftClick = new SoundFile(this, "resources/beep1.mp3");
		rightClick = new SoundFile(this, "resources/beep.mp3");
		middleClick = new SoundFile(this, "resources/beep.wav");
	}

	@Override
	public void draw() {
		background(0);

		textSize(25);
		text("Click to play sound", 100, 100);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		super.mouseClicked(event);
		switch (event.getButton()) {
		case LEFT:
			System.out.println("left clicked");
			leftClick.play();
			break;
		case RIGHT:
			System.out.println("right clicked");
			rightClick.play();
			break;
		case CENTER:
			System.out.println("middle clicked");
			middleClick.play();
			break;
		}
	}
}
