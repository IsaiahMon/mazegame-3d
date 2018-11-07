package downloads.gamecreate.game.graphics;

import java.util.Random;

import downloads.gamecreate.game.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render;

	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(width, height);

		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}

	}

	public void render(Game game) {
		for (int j = 0; j < width * height; j++) {
			pixels[j] = 0;
		}
		render.floor(game);

		// Render test Block
		/*
		 * render.renderWall(0, 0.5, 1.5, 1.5, 0); render.renderWall(0, 0, 1,
		 * 1.5, 0); render.renderWall(0, 0.5, 1, 1, 0); render.renderWall(0.5,
		 * 0.5, 1, 1.5, 0);
		 */
		render.renderDistanceLimter();

		draw(render, 0, 0);
	}
}
