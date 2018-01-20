package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class StartScreen extends JPanel {

	/**
	 * Contains the start menu, which can be used to display high score, start the game or exit the game.   
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage img;
	private String name;
	private int numberOfButtons = 3;
	private String[] nameOfButtons = {
			"Play", 
			"Highscore",
			"Exit"
	};

	public StartScreen (final Game game, final JMenuBar menu, Dimension dim) {
		super(new GridLayout(1,3));
		menu.setVisible(false);
		name = "Super Mario";

		setMinimumSize(dim);
		setMaximumSize(dim);
		setPreferredSize(dim);

		add(Box.createHorizontalGlue());
		Box b = new Box(BoxLayout.Y_AXIS);
		b.add(Box.createVerticalStrut(200));
		add(b, 0,0 );
		b.add(Box.createVerticalStrut(135));

		for(int i = 0; i < numberOfButtons; i++) {
			final int j = new Integer(i);
			JButton bt = new JButton(nameOfButtons[i]);
			bt.setPreferredSize(new Dimension(400, 75));
			bt.setMinimumSize(new Dimension(400, 75));
			bt.setMaximumSize(new Dimension(400, 75));

			bt.setFont(new Font("Comic Sans MS", Font.BOLD, 50));


			bt.setFocusPainted(false);
			bt.setOpaque(false);
			bt.setBorderPainted(true);
			bt.setBackground(new Color(0,0,0,0));
			bt.setForeground(Color.DARK_GRAY);
			b.add(bt);
			//			bt.setContentAreaFilled(false);
			bt.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					game.setGameState(nameOfButtons[j]);
					StartScreen.this.setVisible(false);
					menu.setVisible(true);
				}

			});


		}
		try {
			img = ImageIO.read(new File("mariobackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(img, 0, 0, Game.width, Game.height, this);

		//		Graphics2D g2d = (Graphics2D) g;

		Font fnt1 = new Font("Comic Sans MS", Font.BOLD, 50);
		g.setFont(fnt1);
		g.setColor(Color.BLACK);
		g.drawString(name, 30 , Game.height * 3 / 12);

		//		Font fnt2 = new Font("Comic Sans MS", Font.BOLD, 50);
		//		g2d.setFont(fnt2);
		//		g2d.setColor(Color.WHITE);
		//		g2d.draw3DRect(0, Game.height * 5 / 12 + 25, 400, 75, true);
		//		g2d.draw3DRect(0, Game.height * 5 / 12 + 100, 400, 75, true);
		//		g2d.draw3DRect(0, Game.height * 5 / 12 + 175, 400, 75, true);
	}

}
