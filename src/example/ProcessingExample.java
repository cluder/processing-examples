package example;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * Our Game-Class extends {@link PApplet}, which contains the Game-Loop.
 */
public class ProcessingExample extends PApplet {
    PImage pepeImg;
    float pepeX;
    float pepeY;

    public static void main(String[] args) {
        // processing way to start the game
        PApplet.main(ProcessingExample.class, args);
    }

    /**
     * Called once at the start.<br>
     * We set the background color and target frame rate.
     */
    @Override
    public void setup() {
        super.setup();

        background(0);

        frameRate(30);

        loop();

        // load an image and set intial position
        pepeImg = loadImage("resources/pepe.png");
        pepeX = 100;
        pepeY = 100;
    }

    @Override
    public void settings() {
        super.settings();

        size(640, 480);
    }

    /**
     * The main game loop<br>
     * draw() is called continuously depending on the target frame-rate we set in setup().
     */
    @Override
    public void draw() {
        int y = 50;

        // clear the whole screen
        clear();

        // set white color, display text
        fill(255, 255, 255);
        textSize(30);
        text("Hello World", 50, y);

        y += 50;

        // draw a blue rect - not filled - but with an outline
        color(0, 0, 255);
        noFill();
        stroke(0, 255, 0);
        rect(50, y, 100, 20);

        y += 50;

        // draw a green rect - filled blue - no outline
        color(0, 255, 0);
        fill(0, 0, 255);
        noStroke();
        rect(50, y, 100, 10);

        y += 50;

        // display pepe
        image(pepeImg, pepeX, pepeY);

    }

    void movePepe() {
        pepeX = (float) (Math.random() * (640.0 - pepeImg.width));
        pepeY = (float) (Math.random() * (480.0 - pepeImg.height));
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        super.mouseMoved(event);

        if (event.getX() >= pepeX && event.getX() <= pepeX + pepeImg.width &&//
                event.getY() >= pepeY && event.getY() <= pepeY + pepeImg.height) {
            movePepe();
        }
    }
}
