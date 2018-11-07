package downloads.gamecreate.game;

import downloads.gamecreate.game.graphics.Render3D;

public class Controller {

	public double x, y, z, rotation, xa, ya, za, rotationa;
	public static boolean clicked = false;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean turnUp = false;
	public static boolean turnDown = false;
	public static boolean walk = false;
	public static boolean crawl = false;
	public static boolean map = false;
	public void tick(boolean forward, boolean backward, boolean left,
			boolean right, boolean run, boolean jump, boolean crawl, boolean click, boolean Map) {
		double rotationSpeed = 0.75;
		double walkSpeed = 1.0;
		double xMove = 0.0;
		double zMove = 0.0;
		double yMove = 0.0;
		double jumpHeight = 5.0;

		if (forward) {
			zMove++;
			walk = true;
		}

		if (backward) {
			zMove--;
			walk = true;
		}

		if (right) {
			xMove++;
			walk = true;
		}

		if (left) {
			xMove--;
			walk = true;
		}

		if (turnRight) {
			rotationa += rotationSpeed;
			if (forward || backward || right || left)
				walk = true;
			else
				walk = false;
		}
		if (turnLeft) {
			rotationa -= rotationSpeed;
			if (forward || backward || right || left)
				walk = true;
			else
				walk = false;
		}
		if (jump) {
			walk = false;
			y += jumpHeight;
			run = false;
			crawl = false;
		}

		if (!forward && !backward && !right && !left) {
			walk = false;
		}

		if (crawl) {
			crawl = true;
			walkSpeed = .55;
			y -= .8 * jumpHeight;
			run = false;

		}
		if (!crawl) {
			crawl = false;
		}

		if (run) {
			walkSpeed = 2.5;
			walk = true;
		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation))
				* walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation))
				* walkSpeed;
		x += xa;
		y *= 0.8;
		z += za;
		xa *= 0.25;
		za *= 0.25;
		rotation += rotationa / 20;
		clicked = click;
		rotationa *= 0.1;
		map = Map;
	}
}
