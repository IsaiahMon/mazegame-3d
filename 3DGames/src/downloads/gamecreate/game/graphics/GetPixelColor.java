package downloads.gamecreate.game.graphics;

import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GetPixelColor {
	private static int x;
	private static int y;

	public GetPixelColor(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*public String getPixelColor() throws IOException {
		new File("/textures/worldMap.png");
		BufferedImage image = ImageIO.read(file);
		// Getting pixel color by position x and y
		int clr = image.getRGB(x, y);
		int red = (clr & 0x00ff0000) >> 16;
		int green = (clr & 0x0000ff00) >> 8;
		int blue = clr & 0x000000ff;
		System.out.println("Red Color value = " + red);
		System.out.println("Green Color value = " + green);
		System.out.println("Blue Color value = " + blue);
		return red+ " " + green + " " + blue;
	}*/
}
