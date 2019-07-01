package example.sound;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.sound.FFT;
import processing.sound.SoundFile;
import processing.sound.Waveform;

/**
 * Sound Visualization: Frequency Analysis with FFT.<br>
 * see https://www.youtube.com/watch?v=2O3nm0Nvbi4
 */
public class EqualizerExample extends PApplet {
	public static void main(String[] args) {
		PApplet.main(EqualizerExample.class);
	}

	SoundFile song;
	int volume = 10;
	int bands = 512;
	FFT fft = new FFT(this, bands);
	Waveform waveForm = new Waveform(this, bands);
	boolean drawSepctrum = true;

	@Override
	public void settings() {
		setSize(640, 480);
	}

	@Override
	public void setup() {
		frameRate(30);
		background(0);
		textSize(20);
		textAlign(CENTER);

		// loads mp3/wav files
		fill(255);
		text("Loading mp3 ...", width / 2, height / 2);
		song = new SoundFile(this, "resources/sounds/The Summoning.mp3");

		textAlign(LEFT);

		// set mp3 song as input for FFT analyzer
		fft.input(song);
		waveForm.input(song);

		// set intial volume
		setVolume();
	}

	@Override
	public void draw() {
		background(0);
		final int xText = width / 10;
		int yText = 30;
		final int yStep = 20;

		fill(255);
		textSize(18);
		text("Left mouse button: play", xText, yText);
		text("Right mouse button: stop", xText, yText += yStep);
		text("Middle mouse button: pause", xText, yText += yStep);
		text("Mouse wheel: adjust volume (current: " + volume + "%)", xText, yText += yStep);
		text("0-9: number of frequency bands (current: " + bands + ")", xText, yText += yStep);

		drawSpectrumAnalyzer();
		drawWaveForm();
	}

	private void drawWaveForm() {
		int widthPercent = 60;

		final float[] data = waveForm.analyze();
		final float waveWidth = width / 100.0f * widthPercent;
		float waveEleWidth = waveWidth / data.length;
		float xStart = (width - waveWidth) / 1.2f;
		final float yStart = height * 0.5f;

		for (int i = 0; i < data.length; i++) {
			float value = data[i];

			final float green = map(i, 0, data.length, 50, 255);
			final float blue = map(i, 0, data.length, 255, 0);
			stroke(255, green, blue);

			rect(xStart + i * waveEleWidth, yStart, waveEleWidth, value * 50.0f);

		}
	}

	private void drawSpectrumAnalyzer() {
		int widthPercent = 90;

		final float[] data = fft.analyze();
		final float spectrumWidth = width / 100.0f * widthPercent;
		float spectrumEleWidth = spectrumWidth / data.length;
		float xStart = (width - spectrumWidth) / 2;
		final float yStart = height * 0.95f;

		for (int i = 0; i < data.length; i++) {
			final float greem = map(i, 0, data.length, 50, 255);
			final float blue = map(i, 0, data.length, 255, 0);
			fill(50, greem, blue);
			if (bands > 128) {
				noStroke();
			} else {
				stroke(0);
			}
			rect(xStart + i * spectrumEleWidth, yStart, spectrumEleWidth, -data[i] * 3000.0f);
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		super.mousePressed(event);
		switch (event.getButton()) {
		case LEFT:
			if (!song.isPlaying())
				song.play();
			break;
		case RIGHT:
			song.stop();
			break;
		case CENTER:
			song.pause();
			break;
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		final int count = event.getCount();
		volume -= count;
		if (volume > 100) {
			volume = 100;
		}
		if (volume < 0) {
			volume = 0;
		}

		setVolume();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == '1') {
			updateFFT(1);
		}
		if (event.getKeyCode() == '2') {
			updateFFT(2);
		}
		if (event.getKeyCode() == '3') {
			updateFFT(4);
		}
		if (event.getKeyCode() == '4') {
			updateFFT(8);
		}
		if (event.getKeyCode() == '5') {
			updateFFT(16);
		}
		if (event.getKeyCode() == '6') {
			updateFFT(32);
		}
		if (event.getKeyCode() == '7') {
			updateFFT(64);
		}
		if (event.getKeyCode() == '8') {
			updateFFT(128);
		}
		if (event.getKeyCode() == '9') {
			updateFFT(256);
		}
		if (event.getKeyCode() == '0') {
			updateFFT(512);
		}

		if (event.getKeyCode() == ' ') {
			drawSepctrum = !drawSepctrum;
			if (drawSepctrum) {
//				waveForm.input(input);
			}
		}
	}

	private void updateFFT(int bands) {
		this.bands = bands;
		fft = new FFT(this, bands);
		fft.input(song);
	}

	private void setVolume() {
		// set volume
		song.amp(volume / 100.0f);
	}
}
