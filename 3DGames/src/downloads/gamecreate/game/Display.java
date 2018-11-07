package downloads.gamecreate.game;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Canvas;

import javax.swing.JFrame;

import downloads.gamecreate.game.graphics.Render;
import downloads.gamecreate.game.graphics.Screen;
import downloads.gamecreate.game.gui.Launcher;
import downloads.gamecreate.game.input.InputHandler;
public class Display extends Canvas implements Runnable {
	// INSTANCE FIELDS
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final String Title = "Maze";

	private Thread thread;
	private boolean running = false;
	private Render render;
	private Game game;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	private int fps;
	
	private InputHandler input;

	// STORES THE POSTITION OF X AND Y MOUSE POSITION USED IN run()
	private int newX = 0;
	private int oldX = 0;
	private int newY = 0;
	private int oldY = 0;

	// Display method, holds the objects relative to the size of the display
	// screen
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(WIDTH, HEIGHT);
		game = new Game();
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
	}

	// STARTS THE PROGRAM, Used in the main method
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// TERMINATES THE PROGRAM ON EXIT
	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	// Runs the code,
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;

		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();

			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					// System.out.println(frames + "FPS");
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}

			}
			if (ticked) {
				render();
				frames++;
			}

			render();
			frames++;

			// newX EQUAL TO THE MOUSE X COORDINATES IN THE FRAME
			newX = InputHandler.MouseX;

			// mouse motion to the right makes newX (new X position) greater
			// than the previous X position
			if (newX > oldX) {
				// System.out.println("Right");
				Controller.turnRight = true;
			}

			// mouse motion to the left makes newX (new X position) less than
			// the previous X position
			if (newX < oldX) {
				// System.out.println("Left");
				Controller.turnLeft = true;
			}

			// no mouse motion makes newX (new X position) equal to the previous
			// X position
			if (newX == oldX) {
				// System.out.println("Still");
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}

			// Sets old X position to the new one as reference for direction
			oldX = newX;

			// ------------------------------------------------------------------
			
			// newX EQUAL TO THE MOUSE X COORDINATES IN THE FRAME
			newY = InputHandler.MouseY;

			// mouse motion to the right makes newX (new X position) greater
			// than the previous X position
			if (newY > oldY) {
				// System.out.println("Right");
				Controller.turnUp = true;
			}

			// mouse motion to the left makes newX (new X position) less than
			// the previous X position
			if (newY < oldY) {
				// System.out.println("Left");
				Controller.turnDown = true;
			}

			// no mouse motion makes newX (new X position) equal to the previous
			// X position
			if (newY == oldY) {
				// System.out.println("Still");
				Controller.turnDown = false;
				Controller.turnUp = false;
			}

			// Sets old X position to the new one as reference for direction
			oldY = newY;

		}
	}

	//
	private void tick() {
		game.tick(input.key);
	}

	// RENDER METHOD, USES java.awt.* to render in an Image
	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(game);

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		// Renders in the background, 'g'
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Verdana", 0, 20));
		g.setColor(Color.WHITE);
		g.drawString(fps + " fps", 0, HEIGHT - 10);
		g.dispose();

		// shows the contents of the buffer strategy
		bs.show();
	}
	void renderWin() {

		BufferStrategy wn = this.getBufferStrategy();
		

		// Renders in the background, 'g'
		Graphics win = wn.getDrawGraphics();
		win.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		win.setFont(new Font("Verdana", 0, 20));
		win.setColor(Color.WHITE);
		win.drawString("CONGRATULATIONS, YOU WIN!", WIDTH/2, HEIGHT/2);

		// shows the contents of the buffer strategy
		wn.show();
	}

	// Main method
	public static void main(String[] args) {

		// If constructor is only needed, just type new [Constructor Name].
		// Object creation is not needed
		// New Launcher() object, runs the launcher class, which opens a window
		// with the option to Play or Quit. Play runs the game, Quit quits
		// (Check Launcher Class for code)
		new Launcher();

	}
}
