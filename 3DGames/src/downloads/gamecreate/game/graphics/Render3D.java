package downloads.gamecreate.game.graphics;

import java.util.Random;
import java.awt.image.BufferedImage;

import downloads.gamecreate.game.Controller;
import downloads.gamecreate.game.Display;
import downloads.gamecreate.game.Game;
import downloads.gamecreate.game.level.Block;
import downloads.gamecreate.game.level.Level;
import downloads.gamecreate.game.level.SolidBlock;

public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zBufferWall;
	public double renderDistance = 15000;
	public static double floorPosition = 8;
	private static double ceilingPosition = 200;
	public static double ceiling;
	public static double z;
	private double forwardGlobal;
	private double jump;
	public static double walking;
	public double xx;
	public double yy;
	public boolean click;
	public static int mapWIDTH = 40;
	public static int mapHEIGHT = 40;
	public int xPix, yPix;
	public int[][] map = new int[mapWIDTH][mapHEIGHT];
	public boolean[][] isBlock = new boolean[mapWIDTH][mapHEIGHT];
	public boolean[][] yesBlock = new boolean[mapWIDTH + (mapWIDTH / 2)][mapHEIGHT
			+ (mapHEIGHT / 2)];
	public boolean[][] collideDetect = new boolean[mapWIDTH][mapHEIGHT];
	// public static int[] map =
	// Texture.mapColor("/textures/worldMap.png");//-----------//

	private double forward, backward, right, up, cosine, sine;
	public boolean interact;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		zBufferWall = new double[width];

	}

	public void floor(Game game) {
		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0.0;
		}
		isBlock = recurMap(game);
		// POWERS VIEW ROTATION
		double rotation = game.controls.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		// DIRECTION OF ROTATION/MOTION
		forward = game.controls.z; // game.time % 100.0 / 10.0;
		forwardGlobal = forward;
		right = game.controls.x;
		interact = game.controls.clicked;
		jump = 20.0;
		up = -jump / 20.0;

		// Bob-Motion effects
		walking = Math.sin(game.time + forward / 6.0) * 2.0;

		// Height of the ceiling and the level of the floor

		for (int y = 0; y < height; y++) {

			ceiling = (y - height / 2.0) / height;

			// Set's the floor position
			z = (floorPosition + 25.0 + jump) / ceiling;

			if (Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 2.0;
				z = (floorPosition + 25.0 + jump + walking) / ceiling;

			}
			if (!Controller.walk) {
				walking = 0;
			}

			if (ceiling < 0) {
				// Set's the height of the ceiling
				z = (ceilingPosition - jump) / -ceiling;
				if (Controller.walk)
					z = (ceilingPosition - jump - walking) / -ceiling;
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				// Actual X position
				xx = depth * cosine + z * sine + 20;

				// Actual Z position, ignore 'y' variable, added in early stages
				// of code
				yy = z * cosine - depth * sine;
				if (x == mapWIDTH && y == mapHEIGHT) {

				}
				xPix = (int) (xx + right);
				yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;

				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7)
						+ (yPix & 7) * 16];

				// RENDER LIMIT TO IMPORVE FRAMES, Stops rendering after a
				// depth
				// of 2000 pixels
				if (z > 2000)
					pixels[x + y * width] = 0;

			}

		}

		loadMap(game, interact, xPix, yPix, cosine, sine);

	}

	public boolean[][] recurMap(Game game) {
		// Returns a 2D array of booleans
		// that tells if a block has been
		// placed in a certain x or y
		// position. Used for collision
		// detection
		int rows = mapWIDTH;
		int cols = mapHEIGHT;
		// Actual PMG image of the map. Scanned later in the method to place
		// blocks. Saves the int value of each pixel color
		int[] mapPixel = Texture.mapPixColors("/textures/worldMap.png");

		int count = 0;

		// Converts the 1D array of pixel colors to a 2D array
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (count == mapPixel.length)
					break;
				map[i][j] = mapPixel[count];
				// System.out.println("count: " + count + " " + map[i][j]);
				count++;
			}
		}

		// Tests the values and places them using the block method
		for (int xBlock = 0; xBlock < mapWIDTH; xBlock++)
			for (int yBlock = 0; yBlock < mapHEIGHT; yBlock++) {

				if (map[xBlock][yBlock] == -16744193) {
					isBlock[xBlock][yBlock] = true;
				}

				if (map[xBlock][yBlock] == -65536) {
					isBlock[xBlock][yBlock] = true;
				} else
					isBlock[xBlock][yBlock] = false;
				System.out.println();
			}
		return isBlock;
	}

	public void loadMap(Game game, boolean clicked, int xPix, int yPix,
			double cosine, double sine) {
		int rows = mapWIDTH;
		int cols = mapHEIGHT;
		// Actual PMG image of the map. Scanned later in the method to place
		// blocks. Saves the int value of each pixel color
		int mapPixel[] = Texture.mapPixColors("/textures/worldMap.png");

		int count = 0;

		// Converts the 1D array of pixel colors to a 2D array
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (count == mapPixel.length)
					break;
				map[i][j] = mapPixel[count];
				System.out.println("count: " + count + " " + map[i][j]);
				count++;
			}
		}

		// Tests the values and places them using the block method
		for (int xBlock = 0; xBlock < mapWIDTH; xBlock++)
			for (int yBlock = 0; yBlock < mapHEIGHT; yBlock++) {

				// If picture pixel at position(x,y) is blue, place a brick
				if (map[xBlock][yBlock] == -16744193) {
					// Shifts the map so player spawns in the center of the
					// map
					block(game, xBlock - (rows / 2), yBlock - (cols / 2),
							false, true, cosine, sine);
				}
				// If picture pixel at position(x,y) is red, place an
				// irregularity
				if (map[xBlock][yBlock] == -65536) {
					// Shifts the map so player spawns in the center of the
					// map
					if (interact) {
						click = true;
					}
						block(game, xBlock - (rows / 2), yBlock - (cols / 2),
								true, click, cosine, sine);
				}
			}
	}

	public void block(Game game, int xBlock, int zBlock, boolean button,
			boolean clicked, double cosine, double sine) {
		/*-----------------------Block Creator 3D-------------------------*/
		Level level = game.level;

		// Size of the area of the block generation
		int size = 1;
		double blockHeight = 1.0; // -0.5 - (size);
		// Yheight of stacked blocks
		// for (int yBlock = -size; yBlock <= size; yBlock++) {
		// Xdepth of stacked blocks
		// for (int xBlock = -size; xBlock <= size; xBlock++) {
		// Zdepth of stacked blocks
		// for (int zBlock = -size; zBlock <= size; zBlock++) {
		Block block = level.place(xBlock, zBlock);
		Block east = level.place(xBlock + 1, zBlock);
		Block west = level.place(xBlock + 1, zBlock + 1);
		Block south = level.place(xBlock, zBlock + 1);
		Block north = level.place(xBlock + 1, zBlock + 1);

		// Block top = level.place(xBlock + 1, zBlock+1);
		// Block bottom = level.place(xBlock + 1, zBlock+1);
		for (double blkHeight = 0.0; blkHeight <= blockHeight; blkHeight += .5) {
			if (!button || clicked) {
				// Right Wall Frontside
				renderWall(xBlock + 1.0, xBlock + 1.0, zBlock, zBlock + 1.0,
						blkHeight);
				// Left Wall Frontside
				renderWall(xBlock, xBlock, zBlock, zBlock + 1.0, blkHeight);

				// Back Wall Frontside
				renderWall(xBlock + 1.0, xBlock, zBlock + 1.0, zBlock + 1.0,
						blkHeight);

				// Front Wall Frontside
				renderWall(xBlock + 1.0, xBlock, zBlock, zBlock, blkHeight);

				/*----------Visible from other side-----------*/

				// Right Backside
				renderWall(xBlock + 1.0, xBlock + 1.0, zBlock + 1.0, zBlock,
						blkHeight);
				// Left Backside
				renderWall(xBlock, xBlock, zBlock + 1.0, zBlock, blkHeight);

				// Back Backside
				renderWall(xBlock, xBlock + 1.0, zBlock + 1.0, zBlock + 1.0,
						blkHeight);
				// Front Backside
				renderWall(xBlock, xBlock + 1.0, zBlock, zBlock, blkHeight);
			} else if (button && !clicked) {
				if (blkHeight == .5
						&& !clicked
						&& ((cosine >= -0.5 || cosine <= 1.5) || (sine >= .5 || sine <= 1.5))
						&& (xBlock < 0.5 || zBlock < 0.5)) {
					// Right Wall Frontside
					renderDrawing(xBlock + 1.0, xBlock + 1.0, zBlock,
							zBlock + 1.0, blkHeight);
					// Left Wall Frontside
					renderDrawing(xBlock, xBlock, zBlock, zBlock + 1.0,
							blkHeight);

					// Back Wall Frontside
					renderDrawing(xBlock + 1.0, xBlock, zBlock + 1.0,
							zBlock + 1.0, blkHeight);

					// Front Wall Frontside
					renderDrawing(xBlock + 1.0, xBlock, zBlock, zBlock,
							blkHeight);

					/*----------Visible from other side-----------*/

					// Right Backside
					renderDrawing(xBlock + 1.0, xBlock + 1.0, zBlock + 1.0,
							zBlock, blkHeight);
					// Left Backside
					renderDrawing(xBlock, xBlock, zBlock + 1.0, zBlock,
							blkHeight);

					// Back Backside
					renderDrawing(xBlock, xBlock + 1.0, zBlock + 1.0,
							zBlock + 1.0, blkHeight);
					// Front Backside
					renderDrawing(xBlock, xBlock + 1.0, zBlock, zBlock,
							blkHeight);
				} else {
					// Right Wall Frontside
					renderWall(xBlock + 1.0, xBlock + 1.0, zBlock,
							zBlock + 1.0, blkHeight);
					// Left Wall Frontside
					renderWall(xBlock, xBlock, zBlock, zBlock + 1.0, blkHeight);

					// Back Wall Frontside
					renderWall(xBlock + 1.0, xBlock, zBlock + 1.0,
							zBlock + 1.0, blkHeight);

					// Front Wall Frontside
					renderWall(xBlock + 1.0, xBlock, zBlock, zBlock, blkHeight);

					/*----------Visible from other side-----------*/

					// Right Backside
					renderWall(xBlock + 1.0, xBlock + 1.0, zBlock + 1.0,
							zBlock, blkHeight);
					// Left Backside
					renderWall(xBlock, xBlock, zBlock + 1.0, zBlock, blkHeight);

					// Back Backside
					renderWall(xBlock, xBlock + 1.0, zBlock + 1.0,
							zBlock + 1.0, blkHeight);
					// Front Backside
					renderWall(xBlock, xBlock + 1.0, zBlock, zBlock, blkHeight);
				}
			}
		}
		if (clicked)
			repairWall(game, xBlock, zBlock);

	}

	public void repairWall(Game game, int xBlock, int zBlock) {
		/*-----------------------Block Creator 3D-------------------------*/
		Level level = game.level;

		// Size of the area of the block generation
		int size = 1;
		double blkHeight = 0.5; // -0.5 - (size);
		// Yheight of stacked blocks
		// for (int yBlock = -size; yBlock <= size; yBlock++) {
		// Xdepth of stacked blocks
		// for (int xBlock = -size; xBlock <= size; xBlock++) {
		// Zdepth of stacked blocks
		// for (int zBlock = -size; zBlock <= size; zBlock++) {
		Block block = level.place(xBlock, zBlock);
		Block east = level.place(xBlock + 1, zBlock);
		Block west = level.place(xBlock + 1, zBlock + 1);
		Block south = level.place(xBlock, zBlock + 1);
		Block north = level.place(xBlock + 1, zBlock + 1);
		// Right Wall Frontside
		renderWall(xBlock + 1.0, xBlock + 1.0, zBlock, zBlock + 1.0, blkHeight);
		// Left Wall Frontside
		renderWall(xBlock, xBlock, zBlock, zBlock + 1.0, blkHeight);

		// Back Wall Frontside
		renderWall(xBlock + 1.0, xBlock, zBlock + 1.0, zBlock + 1.0, blkHeight);

		// Front Wall Frontside
		renderWall(xBlock + 1.0, xBlock, zBlock, zBlock, blkHeight);

		/*----------Visible from other side-----------*/

		// Right Backside
		renderWall(xBlock + 1.0, xBlock + 1.0, zBlock + 1.0, zBlock, blkHeight);
		// Left Backside
		renderWall(xBlock, xBlock, zBlock + 1.0, zBlock, blkHeight);

		// Back Backside
		renderWall(xBlock, xBlock + 1.0, zBlock + 1.0, zBlock + 1.0, blkHeight);
		// Front Backside
		renderWall(xBlock, xBlock + 1.0, zBlock, zBlock, blkHeight);
	}

	public void renderWall(double xLeft, double xRight, double zDistanceLeft,
			double zDistanceRight, double yHeight) {
		// Allows the block to remain grounded while crouching and jumping
		double antiJumpDistort = -0.0605 * 4.9893;
		// Repairs a bug that made the object move nearly 70x the speed of the
		// character
		double blockSpeedRepair = 65.93;
		// Allows the block/object to bob with the character's POV while in
		// motion
		double blockBob = walking / 61.29;
		// PLACEMENT/MOTION OF A WALL'S LEFT SIDE

		double xcLeft = ((xLeft / 2.0) - right / blockSpeedRepair) * 2.0;
		double zcLeft = ((zDistanceLeft / 2.0) - forward / blockSpeedRepair) * 2.0;

		double rotLeftSideX = (xcLeft * cosine) - (zcLeft * sine);
		double yCornerTL = ((-yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double yCornerBL = ((0.5 - yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double rotLeftSideZ = (zcLeft * cosine) + (xcLeft * sine);

		// PLACEMENT/MOTION OF A WALL'S RIGHT SIDE
		double xcRight = ((xRight / 2.0) - right / blockSpeedRepair) * 2.0;
		double zcRight = ((zDistanceRight / 2.0) - forward / blockSpeedRepair) * 2.0;

		double rotRightSideX = (xcRight * cosine) - (zcRight * sine);
		double yCornerTR = ((-yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double yCornerBR = ((0.5 - yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double rotRightSideZ = (zcRight * cosine) + (xcRight * sine);

		// Fixes an issue where a block, occasionally makes and infinite
		// extension of blocks in all horizontal directions
		double tex30 = 0.0;
		double tex40 = 8.0;
		double clip = 0.5;

		if (rotLeftSideZ < clip && rotRightSideZ < clip)
			return;

		if (rotLeftSideZ < clip) {
			// Cohen-Sutherland clipping Algorithm
			double clipper = (clip - rotLeftSideZ)
					/ (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ)
					* clipper;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX)
					* clipper;
			tex30 = tex30 + (tex40 - tex30) * clipper;
		}
		if (rotRightSideZ < clip) {
			// Cohen-Sutherland clipping Algorithm
			double clipper = (clip - rotLeftSideZ)
					/ (rotLeftSideZ - rotRightSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ)
					* clipper;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX)
					* clipper;
			tex40 = tex30 + (tex40 - tex30) * clipper;
		}

		// Position of the right and left side of the wall
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2.0);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2.0);

		// If one side goes over the other, it produces negative pixels, so
		// don't render them
		if (xPixelLeft >= xPixelRight)
			return;

		// Casts the double positions of the sides as ints
		int xPixelLeftInt = (int) xPixelLeft;
		int xPixelRightInt = (int) xPixelRight;

		// DONT RENDER THE PIXELS IF THEY AREN'T ON SCREEN
		if (xPixelLeftInt < 0)
			xPixelLeftInt = 0;
		if (xPixelRightInt > width)
			xPixelRightInt = width;

		double yPixelLeftTop = (yCornerTL / rotLeftSideZ * height + height / 2.0);
		double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * height + height / 2.0);
		double yPixelRightTop = (yCornerTR / rotRightSideZ * height + height / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightSideZ * height + height / 2.0);

		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex30 / rotLeftSideZ;
		double tex4 = tex40 / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft)
					/ (xPixelRight - xPixelLeft);

			// Allows the blocks to be solid, not see-through, and issue
			// presented after the inclusion of random terrain generation
			// (Level, Block, and SolidBlock classes)
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

			// If the location of the block rendered is behind any other block,
			// continue
			//
			if (xx < zWall || xx > zWall) {
				if (zBufferWall[x] > zWall) {
					continue;
				}
			}
			// Hides the wall
			zBufferWall[x] = zWall;

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop)
					* pixelRotation;
			double yPixelBottom = yPixelLeftBottom
					+ (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if (yPixelTopInt < 0)
				yPixelTopInt = 0;
			if (yPixelBottomInt > height)
				yPixelBottomInt = height;

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop)
						/ (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);
				pixels[x + y * width] = // xTexture * 100 + yTexture * 100 *
										// 256;
				Texture.floor.pixels[((xTexture & 7) + 8) + (yTexture & 7) * 16];
				zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1)
						* pixelRotation) * 8;

			}
		}
	}

	public void renderDrawing(double xLeft, double xRight,
			double zDistanceLeft, double zDistanceRight, double yHeight) {

		// Allows the block to remain grounded while crouching and jumping
		double antiJumpDistort = -0.0605 * 4.9893;
		// Repairs a bug that made the object move nearly 70x the speed of the
		// character
		double blockSpeedRepair = 65.93;
		// Allows the block/object to bob with the character's POV while in
		// motion
		double blockBob = walking / 61.29;
		// PLACEMENT/MOTION OF A WALL'S LEFT SIDE

		double xcLeft = ((xLeft / 2.0) - right / blockSpeedRepair) * 2.0;
		double zcLeft = ((zDistanceLeft / 2.0) - forward / blockSpeedRepair) * 2.0;

		double rotLeftSideX = (xcLeft * cosine) - (zcLeft * sine);
		double yCornerTL = ((-yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double yCornerBL = ((0.5 - yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double rotLeftSideZ = (zcLeft * cosine) + (xcLeft * sine);

		// PLACEMENT/MOTION OF A WALL'S RIGHT SIDE
		double xcRight = ((xRight / 2.0) - right / blockSpeedRepair) * 2.0;
		double zcRight = ((zDistanceRight / 2.0) - forward / blockSpeedRepair) * 2.0;

		double rotRightSideX = (xcRight * cosine) - (zcRight * sine);
		double yCornerTR = ((-yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double yCornerBR = ((0.5 - yHeight) - (-up * antiJumpDistort) + (blockBob)) * 2.0;
		double rotRightSideZ = (zcRight * cosine) + (xcRight * sine);

		// Fixes an issue where a block, occasionally makes and infinite
		// extension of blocks in all horizontal directions
		double tex30 = 0.0;
		double tex40 = 8.0;
		double clip = 0.5;

		if (rotLeftSideZ < clip && rotRightSideZ < clip)
			return;

		if (rotLeftSideZ < clip) {
			// Cohen-Sutherland clipping Algorithm
			double clipper = (clip - rotLeftSideZ)
					/ (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ)
					* clipper;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX)
					* clipper;
			tex30 = tex30 + (tex40 - tex30) * clipper;
		}
		if (rotRightSideZ < clip) {
			// Cohen-Sutherland clipping Algorithm
			double clipper = (clip - rotLeftSideZ)
					/ (rotLeftSideZ - rotRightSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ)
					* clipper;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX)
					* clipper;
			tex40 = tex30 + (tex40 - tex30) * clipper;
		}

		// Position of the right and left side of the wall
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2.0);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2.0);

		// If one side goes over the other, it produces negative pixels, so
		// don't render them
		if (xPixelLeft >= xPixelRight)
			return;

		// Casts the double positions of the sides as ints
		int xPixelLeftInt = (int) xPixelLeft;
		int xPixelRightInt = (int) xPixelRight;

		// DONT RENDER THE PIXELS IF THEY AREN'T ON SCREEN
		if (xPixelLeftInt < 0)
			xPixelLeftInt = 0;
		if (xPixelRightInt > width)
			xPixelRightInt = width;

		double yPixelLeftTop = (yCornerTL / rotLeftSideZ * height + height / 2.0);
		double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * height + height / 2.0);
		double yPixelRightTop = (yCornerTR / rotRightSideZ * height + height / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightSideZ * height + height / 2.0);

		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex30 / rotLeftSideZ;
		double tex4 = tex40 / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft)
					/ (xPixelRight - xPixelLeft);

			// Allows the blocks to be solid, not see-through, and issue
			// presented after the inclusion of random terrain generation
			// (Level, Block, and SolidBlock classes)
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

			// If the location of the block rendered is behind any other block,
			// continue
			//
			if (xx < zWall || xx > zWall) {
				if (zBufferWall[x] > zWall) {
					continue;
				}
			}
			// Hides the wall
			zBufferWall[x] = zWall;

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop)
					* pixelRotation;
			double yPixelBottom = yPixelLeftBottom
					+ (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if (yPixelTopInt < 0)
				yPixelTopInt = 0;
			if (yPixelBottomInt > height)
				yPixelBottomInt = height;

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop)
						/ (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);
				pixels[x + y * width] = // xTexture * 100 + yTexture * 100 *
										// 256;
				Texture.wallButton.pixels[((xTexture & 7)) + (yTexture & 7) * 8];
				zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1)
						* pixelRotation) * 8;

			}
		}
	}

	public void renderDistanceLimter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);

			if (brightness < 0)
				brightness = 0;
			if (brightness > 255)
				brightness = 255;

			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;

		}
	}

}
