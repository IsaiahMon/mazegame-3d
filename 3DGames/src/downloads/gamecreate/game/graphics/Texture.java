package downloads.gamecreate.game.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {

	public static Render floor = loadBitmap("/textures/grassTop.png");
	public static Render wallButton = loadBitmap("/textures/wallButton.png");
	public static Render map = loadBitmap("/textures/worldMap.png");
	public static int mapPix[] = new int[Render3D.mapHEIGHT * Render3D.mapWIDTH ];
	public static int width, height;

	public static Render loadBitmap(String fileName) {
		try {
			BufferedImage image = ImageIO.read(Texture.class
					.getResource(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch (Exception e) {
			System.out.println("CRASH.");
			throw new RuntimeException(e);

		}
	}

	public static int[] mapPixColors(String fileName) {
		try {
			BufferedImage image = ImageIO.read(Texture.class
					.getResource(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			mapPix = image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return mapPix;
		} catch (Exception e) {
			System.out.println("CRASH.");
			throw new RuntimeException(e);

		}
	}
}
