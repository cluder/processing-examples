package example.swing;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Opens an additional swing window, where some settings can be changed.
 */
public class SwingExample extends PApplet {
	SwingGui gui;

	String currentIconFilename;
	PImage icon;

	Color backgroundColor = Color.BLACK;

	public static void main(String[] args) {
		// start processing
		PApplet.main(SwingExample.class, args);
	}

	public SwingExample() {
		// start swing gui
		gui = new SwingGui();
		gui.setVisible(true);
	}

	@Override
	public void setup() {

		frameRate(30);
	}

	@Override
	public void settings() {
		size(640, 480);
	}

	@Override
	public void draw() {
		// grab new values from GUI
		// Player name
		final String player = gui.getPlayerName();
		backgroundColor = gui.getBgColor();

		// clear the whole screen
		background(backgroundColor.getRGB());

		textSize(25);
		text("Player name: " + player, 100, 100);

		// Player Icon
		final String newIconFilename = gui.getPlayerIcon();
		if (newIconFilename.equals(currentIconFilename) == false) {
			System.out.println("reloading img");
			icon = loadImage("resources/" + newIconFilename);
			currentIconFilename = newIconFilename;
		}

		if (icon != null) {
			image(icon, 100, 150);
		}
	}
}
