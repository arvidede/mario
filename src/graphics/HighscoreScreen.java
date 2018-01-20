package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class HighscoreScreen extends JPanel {

	
	/**
	 *displays the high score, contains all time top three. 
	 */
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Integer> list;
	private JButton bt;
	private String name;
	
	public HighscoreScreen(final Game game, ArrayList<Integer> list, final Dimension dim, final JMenuBar menu) {
		super(new GridLayout(3,3));
		menu.setVisible(false);
		this.setVisible(false);
		name = "HIGHSCORE";
		this.list = list;
		
		setMinimumSize(dim);
		setMaximumSize(dim);
		setPreferredSize(dim);
		
		add(Box.createHorizontalGlue());
		Box b = new Box(BoxLayout.Y_AXIS);
		add(b, 2,1 );
		b.add(Box.createVerticalStrut(135));
		
		bt = new JButton("Main Menu");
		bt.setPreferredSize(new Dimension(400, 75));
		bt.setMinimumSize(new Dimension(400, 75));
		bt.setMaximumSize(new Dimension(400, 75));
		bt.setForeground(Color.DARK_GRAY);
		bt.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		
		bt.setContentAreaFilled(false);
		bt.setFocusPainted(false);
		bt.setOpaque(true);
		bt.setVisible(true);
		bt.setBorderPainted(false);
		bt.setBackground(new Color(0,0,0,0));
		b.add(bt);
		bt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				game.setGameState("Game Menu");
				HighscoreScreen.this.setVisible(false);			
				game.setStartScreen();
			}	
		});
		
		add(bt, 2,1);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
		g.drawString(name, Game.width / 2 - 500 , Game.height / 12);
		
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
		int row = 2;
		for(int i = 0; i < 3; i++) {
			g.drawString(Integer.toString(list.get(i)), Game.width / 2, Game.height * row / 12);	
		row++;
		}
	}
}
