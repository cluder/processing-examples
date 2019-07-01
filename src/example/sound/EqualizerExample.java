package example.sound;

import java.util.ArrayDeque;
import java.util.ArrayList;

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

	ArrayList<String> songNames = new ArrayList<>();
	int currentSongIdx = 0;

	SoundFile song;
	int volume = 30;
	int bands = 64;
	FFT fft = new FFT(this, bands);
	Waveform waveForm = new Waveform(this, bands);
	boolean drawSepctrum = true;

	@Override
	public void settings() {
		size(1280, 720, P3D);
	}

	@Override
	public void setup() {
		songNames.add("Circle Of Alchemists - The Prime-Age.mp3");
		songNames.add("Circle Of Alchemists - Cryosphere.mp3");
		songNames.add("Circle Of Alchemists - Jackpot Ride.mp3");
		songNames.add("Circle Of Alchemists - The Summoning.mp3");

		frameRate(30);
		background(0);
		textSize(20);
		textAlign(CENTER);

		// loads mp3/wav files
		fill(255);
	}

	private void init() {
		text("Loading mp3 ...", width / 2, height / 2);

		loadSong();

		// set intial volume
		setVolume();

		textAlign(LEFT);
		init = true;
	}

	private void loadSong() {
		song = new SoundFile(this, "resources/sounds/" + songNames.get(currentSongIdx));
		song.amp(volume / 100f);
		fft.input(song);
		waveForm.input(song);
		song.play();
	}

	boolean init = false;

	@Override
	public void draw() {
		if (!init) {
			init();
		}

		background(0);
		final float xText = width * 0.5f;
		int yText = 30;
		final int yStep = 20;

		fill(255);
		textSize(15);
		textAlign(LEFT);
		text("Mouse buttons: play / pause / stop", xText, yText);
		text("Mouse wheel:   volume (current: " + volume + "%)", xText, yText += yStep);
		text("0-9: # frequency bands (current: " + bands + ")", xText, yText += yStep);
		text("Left / right arrow: next song ", xText, yText += yStep);

		yText = height - 7;
		textAlign(CENTER);
		text(songNames.get(currentSongIdx), width / 2, yText);

		drawSpectrumAnalyzer();
		drawWaveForm();
	}

	private void drawWaveForm() {
		int widthPercent = 45;

		final float[] data = waveForm.analyze();
		final float waveWidth = width / 100.0f * widthPercent;
		float waveEleWidth = waveWidth / data.length;
		float xStart = width / 2f;
		final float yStart = height * 0.35f;

		for (int i = 0; i < data.length; i++) {
			float value = data[i];

			final float green = map(i, 0, data.length, 50, 255);
			final float blue = map(i, 0, data.length, 255, 0);
			stroke(255, green, blue);
			noFill();
			rect(xStart + i * waveEleWidth, yStart, waveEleWidth, value * 100.0f);
		}
	}

	ArrayDeque<float[]> dataQueue = new ArrayDeque<>();

	private void drawSpectrumAnalyzer() {
		int widthPercent = 90;

		final float spectrumWidth = width / 100.0f * widthPercent;
		float spectrumEleWidth = spectrumWidth / bands;
		float xStart = (width - spectrumWidth) / 2;
		final float yStart = height * 0.95f;

		if (song.isPlaying()) {
			final float[] data = fft.analyze();
			final float[] clone = data.clone();
			dataQueue.addFirst(clone);
		}
		final int maxEle = 100;

		if (dataQueue.size() >= maxEle) {
			dataQueue.removeLast();
		}
		if (dataQueue.size() > maxEle) {
			dataQueue.clear();
		}

		int z = 0;
		float alpha = 255;
		int eleNum = 0;
		for (float[] ele : dataQueue) {
			eleNum++;
			alpha = map(eleNum, 0, dataQueue.size(), 255, 0);

			for (int i = 0; i < ele.length; i++) {
				final float greem = map(i, 0, ele.length, 50, 255);
				final float blue = map(i, 0, ele.length, 255, 0);
				if (bands > 128) {
					noStroke();
				} else {
					stroke(0);
				}

				push();
				fill(30, greem, blue, alpha);
				translate(0, 0, z);
				rect(xStart + i * spectrumEleWidth, yStart, spectrumEleWidth, -ele[i] * 3000.0f);
				pop();
			}
			z -= 20;
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (!init)
			return;
		super.mousePressed(event);
		switch (event.getButton()) {
		case LEFT:
			if (!song.isPlaying())
				song.play();
			break;
		case RIGHT:
			song.stop();
			dataQueue.clear();
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

		// right
		if (event.getKeyCode() == 39) {
			song.stop();
			currentSongIdx++;
			currentSongIdx %= songNames.size();
			loadSong();
		}
		// left
		if (event.getKeyCode() == 37) {
			song.stop();
			currentSongIdx--;
			if (currentSongIdx < 0) {
				currentSongIdx = songNames.size();
			}
			loadSong();
		}
	}

	private void updateFFT(int bands) {
		this.bands = bands;
		fft = new FFT(this, bands);
		fft.input(song);
		dataQueue.clear();
	}

	private void setVolume() {
		// set volume
		song.amp(volume / 100.0f);
	}
}
