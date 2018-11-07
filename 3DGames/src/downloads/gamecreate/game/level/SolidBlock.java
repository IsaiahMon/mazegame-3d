package downloads.gamecreate.game.level;

public class SolidBlock extends Block {

	public SolidBlock() {
		solid = true;
	}

}

/*
EXTRA CODE FOR LEVEL GENERATION IN RENDER3D CLASS!!!

for (int xBlock = -size; xBlock <= size; xBlock++) {
for (int zBlock = -size; zBlock <= size; zBlock++) {
	Block block = level.place(xBlock, zBlock);
	Block east = level.place(xBlock + 1, zBlock);
	Block south = level.place(xBlock, zBlock + 1);

	if (block.solid) {
		if (!east.solid)
			renderWall(xBlock + 1.0, xBlock + 1.0, zBlock, zBlock + 1.0,
					0.5);
		if (!south.solid)
			renderWall(xBlock + 1.0, xBlock, zBlock + 1.0, zBlock + 1.0,
					0.5);

		else {
			if (east.solid)
				renderWall(xBlock + 1.0, xBlock + 1.0, zBlock + 1.0,
						zBlock, 0.5);
			if (south.solid)
				renderWall(xBlock, xBlock + 1.0, zBlock + 1.0,
						zBlock + 1.0, 0.5);
		}
	}

}
}
*/