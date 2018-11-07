package downloads.gamecreate.game.graphics;

import java.applet.Applet;

import downloads.gamecreate.game.Display;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;

	public Display display;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[this.width * this.height];
	}

	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= Display.HEIGHT)
				continue;
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= Display.WIDTH)
					continue;

				int alphaRend = render.pixels[x + y * render.width];

				if (alphaRend > 0)
					pixels[xPix + yPix * width] = alphaRend;
			}
		}

	}
}
