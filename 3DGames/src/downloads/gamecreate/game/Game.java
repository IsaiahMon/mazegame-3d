package downloads.gamecreate.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import downloads.gamecreate.game.level.Level;

public class Game {
	public int time;
	public Controller controls;
	public Level level;

	public Game() {
		controls = new Controller();

		// Creates the level (from Level class
		level = new Level(80, 80);
	}

	public void tick(boolean[] key) {
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean backward = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean run = key[KeyEvent.VK_R];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crawl = key[KeyEvent.VK_SHIFT];
		boolean click = key[KeyEvent.VK_E];
		boolean map = key[KeyEvent.VK_M];

		controls.tick(forward, backward, left, right, run, jump, crawl, click, map);
	}

}
