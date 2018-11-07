package downloads.gamecreate.game;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import downloads.gamecreate.game.graphics.MapAddition;

public class RunGame {

	public RunGame() {
		// Creates a buffered image of the same dimensions as the cursor on the
		// screen
		BufferedImage cursor = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		
		// Creates invisible cursor for the screen
		Cursor noCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursor, new Point(0, 0), "No Cursor");

		// Creates a display
		Display game = new Display();
		ImageIcon map = new ImageIcon("/textures/worldMap.png");
		JLabel MapShow = new JLabel(map);
		// Adds a frame/window to display the game
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();

		// Sets cursor on the window to the invisible cursor 'noCursor'
		frame.getContentPane().setCursor(noCursor);

		frame.setTitle(Display.Title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);

		if (Controller.clicked) {
			System.exit(3);
			System.out.println("You Win! Congratulations");
		}
		// Starts the program
		game.start();
	}

}
