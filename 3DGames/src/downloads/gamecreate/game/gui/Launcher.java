package downloads.gamecreate.game.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import downloads.gamecreate.game.Controller;
import downloads.gamecreate.game.RunGame;

public class Launcher extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel window = new JPanel();
	// -REMINDER!!!!! Implement Options if sound is added; In Help tab put your
	// information
	private JButton play, levelSelect, help, quit;
	private Rectangle rplay, rlevelSelect, rhelp, rquit;

	private int width = 240;
	private int height = 320;
	private int buttonWidth = 80;
	private int buttonHeight = 40;

	public Launcher() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Game");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);

		buttonCreate();
	}

	private void buttonCreate() {

		// Initializes Play as a JButton
		play = new JButton("Play");
		// Sets location of rectangle, which serves as a button
		rplay = new Rectangle((width / 2) - (buttonWidth / 2),
				(height - (5 * buttonHeight)), buttonWidth, buttonHeight);
		// Sets play's bounds to inside of the rectangle rplay
		play.setBounds(rplay);
		window.add(play);

		// REPEATED FOR OTHER BUTTONS

		quit = new JButton("Quit");
		rquit = new Rectangle((width / 2) - (buttonWidth / 2),
				(height - (2 * buttonHeight)), buttonWidth, buttonHeight);
		quit.setBounds(rquit);
		window.add(quit);
		
		levelSelect = new JButton("Level Selection");
		rlevelSelect = new Rectangle((width / 2) - (buttonWidth / 2) - 20,
				(height - (4 * buttonHeight)), buttonWidth + 40, buttonHeight);
		levelSelect.setBounds(rlevelSelect);
		window.add(levelSelect);

		/*----------Button Interaction -----------*/
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// Removes the launcher window
				dispose();

				// Creates new object based on RunGame class constructor. This
				// object runs the game.
				new RunGame();

			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);

			}
		});
		/*----------Button Interaction -----------*/
		levelSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// Removes the launcher window
				dispose();

				// Creates new object based on LevelSelection class constructor. This
				// object runs the game.
				new LevelSelection();

			}
		});
	}
}
