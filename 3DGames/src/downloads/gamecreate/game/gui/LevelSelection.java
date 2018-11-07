package downloads.gamecreate.game.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import downloads.gamecreate.game.RunGame;

public class LevelSelection extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel window = new JPanel();
	// -REMINDER!!!!! Implement Options if sound is added; In Help tab put your
	// information
	private JButton lev1, lev2, cont;
	private Rectangle rlev1, rlev2, rcont;

	private int width = 240;
	private int height = 320;
	private int buttonWidth = 80;
	private int buttonHeight = 40;

	public LevelSelection() {
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
		// Initializes lev1 as a JButton
		lev1 = new JButton("Level 1");
		// Sets location of rectangle, which serves as a button
		rlev1 = new Rectangle((width / 2) - (buttonWidth / 2),
				(height - (5 * buttonHeight)), buttonWidth, buttonHeight);
		// Sets lev1's bounds to inside of the rectangle rlev1
		lev1.setBounds(rlev1);
		window.add(lev1);

		// REPEATED FOR OTHER BUTTONS

		lev2 = new JButton("Level 2");
		rlev2 = new Rectangle((width / 2) - (buttonWidth / 2),
				(height - (4 * buttonHeight)), buttonWidth, buttonHeight);
		lev2.setBounds(rlev2);
		window.add(lev2);
		
		cont = new JButton("More coming soon!");
		rcont = new Rectangle((width / 2) - (buttonWidth / 2) - 20,
				(height - (3 * buttonHeight)), buttonWidth + 40, buttonHeight);
		cont.setBounds(rcont);
		window.add(cont);
		/*----------Button Interaction -----------*/
		lev1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// Removes the launcher window
				dispose();

				// Creates new object based on RunGame class constructor. This
				// object runs the game.
				new RunGame();

			}
		});
	
	}
}