package example.swing;

import processing.core.PApplet;
import processing.core.PImage;

public class SwingExample extends PApplet {
	SwingGui gui;

	String iconPlayer1String;
	PImage iconPlayer1;
	String iconPlayer2String;
	PImage iconPlayer2;

	public static void main(String[] args) {
		// start processing
		PApplet.main(SwingExample.class, args);
	}

	public SwingExample() {
		gui = new SwingGui();
		// start separate swing gui
		gui.setVisible(true);
	}

	@Override
	public void setup() {
		super.setup();

		background(0);

		frameRate(30);
	}

	@Override
	public void settings() {
		size(640, 480);
	}

	@Override
	public void draw() {
		// clear the whole screen
		clear();

		// grab new values from GUI
		// Player names
		final String player1 = gui.getPlayer1Name();
		final String player2 = gui.getPlayer2Name();

		textSize(20);
		text("player 1: " + player1, 100, 100);
		text("player 2: " + player2, 400, 100);

		// Player Icon
		final String newIconPlayer1String = gui.getPlayer1Icon();
		if (newIconPlayer1String.equals(iconPlayer1String) == false) {
			System.out.println("reloading img");
			iconPlayer1 = loadImage("resources/" + newIconPlayer1String);
			iconPlayer1String = newIconPlayer1String;
		}
		final String newIconPlayer2String = gui.getPlayer2Icon();
		if (newIconPlayer2String.equals(iconPlayer2String) == false) {
			System.out.println("reloading img");
			iconPlayer2 = loadImage("resources/" + newIconPlayer2String);
			iconPlayer2String = newIconPlayer2String;
		}

		if (iconPlayer1 != null) {
			image(iconPlayer1, 100, 150);
		}

		if (iconPlayer2 != null) {
			image(iconPlayer2, 400, 150);
		}
	}
}
