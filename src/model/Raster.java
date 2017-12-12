package model;

import rafgfxlib.Util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

public class Raster {
	private Model model;
	private boolean test;
	private BufferedImage background;
	private BufferedImage original;
	private boolean isEffect = false;
	private Random r;

	private long milisecondsStart = System.currentTimeMillis();;

	public Raster(Model model) {
		this.model = model;
		loadBackground();
		r = new Random();
	}

	private void loadBackground() {
		background = Util.loadImage("background.png");
		original = background;
		if (background == null) {
			System.out.println("Nema slike!");
			return;
		}
	}

	public boolean fiveSecPassed() {
		int secStart = (int) milisecondsStart / 1000;

		int secNow = (int) System.currentTimeMillis() / 1000;

		int passedSec = secNow - secStart;

		if (passedSec < 3)
			return false;
		else
			return true;
	}

	public void flip() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {

				source.getPixel(x, y, rgb);

				target.setPixel(source.getWidth() - x - 1, y, rgb);
			}
		}

		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	public void grayscale() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				int i = (rgb[0] + rgb[1] + rgb[2]) / 3;

				rgb[0] = i;
				rgb[1] = i;
				rgb[2] = i;

				target.setPixel(x, y, rgb);
			}

		}
		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void negative() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				rgb[0] = 255 - rgb[0];
				rgb[1] = 255 - rgb[1];
				rgb[2] = 255 - rgb[2];

				target.setPixel(x, y, rgb);
			}
		}

		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void posterize() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				rgb[0] = (rgb[0] / 50) * 50;
				rgb[1] = (rgb[1] / 50) * 50;
				rgb[2] = (rgb[2] / 50) * 50;

				target.setPixel(x, y, rgb);
			}
		}

		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void brightness() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		int brightness = 50;

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				rgb[0] += brightness;
				rgb[1] += brightness;
				rgb[2] += brightness;

				if (rgb[0] < 0)
					rgb[0] = 0;
				if (rgb[1] < 0)
					rgb[1] = 0;
				if (rgb[2] < 0)
					rgb[2] = 0;

				if (rgb[0] > 255)
					rgb[0] = 255;
				if (rgb[1] > 255)
					rgb[1] = 255;
				if (rgb[2] > 255)
					rgb[2] = 255;

				target.setPixel(x, y, rgb);
			}
		}
		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void rotate() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				target.setPixel(source.getWidth() - x - 1, y, rgb);
			}
		}

		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void binary() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				int i = (int) (rgb[0] * 0.30 + rgb[1] * 0.59 + rgb[2] * 0.11);

				if (i > 160)
					i = 255;
				else
					i = 0;

				rgb[0] = i;
				rgb[1] = i;
				rgb[2] = i;

				target.setPixel(x, y, rgb);
			}
		}
		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void vignette() {
		WritableRaster source = background.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);

		int rgb[] = new int[3];

		double vignette = 0.8;

		final double radius = Math.sqrt(2) / 2.0;

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);

				double fX = x / (double) source.getWidth();
				double fY = y / (double) source.getHeight();

				double dist = Math.sqrt((fX - 0.5) * (fX - 0.5) + (fY - 0.5) * (fY - 0.5)) / radius;

				dist = Math.pow(dist, 1.8);

				rgb[0] = (int) (rgb[0] * (1.0 - dist * vignette));
				rgb[1] = (int) (rgb[1] * (1.0 - dist * vignette));
				rgb[2] = (int) (rgb[2] * (1.0 - dist * vignette));

				target.setPixel(x, y, rgb);
			}
		}
		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;
	}

	private void reflection() {
		WritableRaster source = background.getRaster();
		int produzetak = source.getHeight() / 4;
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight() + produzetak, false);

		int rgb[] = new int[3];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);
				target.setPixel(x, y, rgb);
			}
		}
		for (int y = 0; y < produzetak; y++) {

			double fy = y / (double) produzetak;

			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, source.getHeight() - 1 - y, rgb);

				rgb[0] *= (1.0 - fy) * 0.5;
				rgb[1] *= (1.0 - fy) * 0.5;
				rgb[2] *= (1.0 - fy) * 0.5;

				target.setPixel(x, source.getHeight() + y, rgb);
			}
		}
		background = Util.rasterToImage(target);
		milisecondsStart = System.currentTimeMillis();
		isEffect = true;

	}

	public void setOriginal() {
		background = original;

		isEffect = false;
		milisecondsStart = System.currentTimeMillis();
	}

	public void wallHit() {
		if (!isEffect && fiveSecPassed()) {
			if (fiveSecPassed()) {
				int val = r.nextInt(5);
				System.out.println(val);
				if (val == 0)
					flip();
				else if (val == 1)
					grayscale();
				else if (val == 2)
					negative();
				else if (val == 3)
					posterize();
				else if (val == 4)
					brightness();

			}

		} else if (fiveSecPassed() && isEffect)
			setOriginal();
	}

	public void obstacleHit() {
		if (!isEffect && fiveSecPassed()) {
			if (fiveSecPassed()) {
				int val = r.nextInt(4);
				if (val == 0)
					rotate();
				else if (val == 1)
					binary();
				else if (val == 2)
					vignette();
				else if (val == 3)
					reflection();

			}

		} else if (fiveSecPassed() && isEffect)
			setOriginal();
	}

	public BufferedImage getBackground() {
		return background;
	}
}
