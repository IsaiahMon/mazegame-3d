package downloads.gamecreate.game.level;

import java.util.Random;

import downloads.gamecreate.game.graphics.Sprite;

public class Level {

	public Block[] blocks;
	public final int width, height;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[width * height];
		Random random = new Random();

		// Y max value is length, X max value is width
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				Block block = null;

				// Picks random number between 0 and 4. This loop runs a number
				// of 'width * height' times
				// If the number is 0, it generates a solid block

				if (random.nextInt(4) == 0)
				{
					block = new SolidBlock();
					if( random.nextInt(8) == 0)
					{
						block.addSprite(new Sprite(0, 0, 0));
					}
				}
				// If the number is not zero, it generates a non solid block/
				// blank block
				else{
					block = new Block();
				}
				

				// Sets the block
				blocks[x + y * width] = block;
			}
		}
	}

	public Block place(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Block.solidWall;
		}
		return blocks[x + y * width];
	}

}
