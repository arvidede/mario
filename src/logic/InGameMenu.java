package logic;

import graphics.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InGameMenu extends JButton {

	/**
	 * InGameMenu: can switch the game state to take the user to main menu, exit game and resume game.  
	 */
	private static final long serialVersionUID = 1L;

	public InGameMenu(final Game game) {
		super("Menu");
		setBorderPainted(false);
		setBackground(new Color(0,0,0,0));
		setOpaque(true);
		setContentAreaFilled(false);
		setFocusPainted(false);

		final JPanel menu = new JPanel();
		menu.setBackground(new Color(0,0,0,0));
		menu.setVisible(false);


		JButton menuLabel = new JButton("MENU");
		menuLabel.setBorderPainted(false);
		menuLabel.setEnabled(false);
		menuLabel.setBackground(new Color(0,0,0,0));
		menuLabel.setForeground(Color.MAGENTA);
		menuLabel.setFont(new Font("Avenir", 0, 64));
		menuLabel.setHorizontalAlignment(JLabel.CENTER);

		final JButton resumeButton = new JButton("Resume");
		resumeButton.setBorderPainted(false);
		resumeButton.setBackground(new Color(0,0,0,0));
		resumeButton.setForeground(Color.DARK_GRAY);
		resumeButton.setFont(new Font("Avenir", 0, 64));

		final JButton closeButton = new JButton("Exit");
		closeButton.setBorderPainted(false);
		closeButton.setBackground(new Color(0,0,0,0));
		closeButton.setForeground(Color.DARK_GRAY);
		closeButton.setFont(new Font("Avenir", 0, 64));
		
		final JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBorderPainted(false);
		mainMenuButton.setBackground(new Color(0,0,0,0));
		mainMenuButton.setForeground(Color.DARK_GRAY);
		mainMenuButton.setFont(new Font("Avenir", 0, 64));

		Box menuItems = new Box(BoxLayout.PAGE_AXIS);
		menuItems.setBackground(new Color(0,0,0,64));
		menuItems.setOpaque(true);
		menuItems.setMinimumSize(new Dimension(400,600));
		menuItems.setMaximumSize(new Dimension(400,600));
		menuItems.setPreferredSize(new Dimension(400,600));

		menuItems.add(Box.createVerticalStrut(50));
		menuItems.add(menuLabel, BorderLayout.NORTH);
		menuItems.add(Box.createVerticalStrut(50));
		menuItems.add(resumeButton);
		menuItems.add(closeButton);
		menuItems.add(mainMenuButton);
		menu.add(menuItems);

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.add(menu, BorderLayout.CENTER);
				InGameMenu.this.requestFocus();
				game.setGameState("Paus");
				menu.setVisible(true);
				
				closeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
						
					}

				});
				
				resumeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {					
						menu.setVisible(false);
						game.setEnabled(true);
						game.requestFocusInWindow();
						game.setGameState("Play"); // flyttar tillbaka fokus till fönstret när menyn stängs
					}

				});
				
					mainMenuButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {		
						menu.setVisible(false);
						game.setStartScreen();
						game.setGameState("Game Menu");
					}

				});
			}
		});

	}

}
