
package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import logic.InGameMenu;
import logic.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Game extends JFrame implements KeyListener, Runnable {
	/**
	 * Game class holds the game engine and controls the input commands.
	 */
	private static final long serialVersionUID = 1L;

	public static int height;
	public static int width;
	private static final int scale = 2;
	private static final int topSpeed = 4;
	private static final int noScreens = 1; // ändra här över thinlink
	private static final Color marioBlue = new Color(147, 206, 254);

	InputStream in = null;
	AudioStream audioStream = null;
	
	String soundtrack = "smb_jump-small.wav";
	
	private static final int playerInGoal = 3;

	private ArrayList<Integer> highscoreList =  new ArrayList<Integer>();

	private Dimension screenSize;
	private StartScreen start;
	private HighscoreScreen highscoreScreen;
	private BufferedImage mario;
	private Thread timer;

	private boolean left, right, ground, jump; 
	private boolean highscore = false;

	private double dx = 0.0;
	private double dy = 0.0;

	private double friction = 0.1;
	private double gravity = 0.3;
	private double airDrag = 0.01;
	private double startTime;

	private Player player;
	private int jumpCounter = 0;

	private TileMap map;
	private Camera cam;
	private String gameState = "Game Menu";
	private int currentMap = 1;

	private JMenuBar menuBar;
	private double newCenterX;

	private JLabel points;
	private Box healthBox;
	private JLabel healthLabel;
	private JLabel time;

	public Game() {
		super("Medioker Mario");

		highscore();

		System.out.println("Initiating game... ");

		screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		pack();
		height = screenSize.height;
		width = screenSize.width / noScreens;

		setSize(width, height);
		menuBar = new JMenuBar();

		start = new StartScreen(this, menuBar, screenSize);
		highscoreScreen = new HighscoreScreen(this, highscoreList, screenSize, menuBar);
		this.add(start);

		newCenterX = width / 2;

		left = right = false;
		ground = jump = false;



		JMenu menu = new JMenu();

		JMenuItem restart = new JMenuItem("Restart");
		restart.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.toStartPosition();
				cam.setX(0);
				cam.setY(-350);
				newCenterX = width/2;
			}
		});

		menuBar.setOpaque(false);
		
		menuBar.add(menu);
		menuBar.setBackground(marioBlue);
		menuBar.setBorderPainted(false);

		time = new JLabel();
		time.setForeground(Color.DARK_GRAY);
		time.setFont(new Font("Avenir", 2, 64));

		points = new JLabel();
		points.setForeground(Color.DARK_GRAY);
		points.setFont(new Font("Avenir", 2, 64));

		menu.add(restart);

		add(menuBar, BorderLayout.NORTH);

		JButton popUp = new InGameMenu(this);

		menuBar.add(popUp);

		menuBar.add(Box.createHorizontalGlue());
		healthBox = new Box(BoxLayout.X_AXIS);
		healthLabel = new JLabel();
		healthLabel.setForeground(Color.DARK_GRAY);
		healthLabel.setFont(new Font("Avenir", 0, 64));

		menuBar.add(time);
		menuBar.add(Box.createHorizontalStrut(100));
		healthBox.add(healthLabel);
		readImage();
		placeMario();
		menuBar.add(Box.createHorizontalStrut(100));
		menuBar.add(points);
		menuBar.add(Box.createHorizontalStrut(100));

		player = new Player(0, 5);

		getContentPane().setBackground(marioBlue);
		setVisible(true);

		this.setFocusable(true); // måste vara här annars tar knappen över, måste även göras om menyn ska stängas

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timer = new Thread(this);
		timer.start();
		addKeyListener(this);

		System.out.println("Game successfully initiated.");

	}

	public void run() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(gameState);
		this.requestFocus();
		if(gameState.equals("Play")) { // finns det något sätt att säga till spelet att gå hit, istället för att loopa om och kontrollera gamestate
			while(player.isAlive() && gameState.equals("Play") && player.getState() != playerInGoal) {


				if(map.checkMonsterCollision(player.getbX(), player.getbY() + dy)) {
					dy = -10;
				} else if(map.checkMonsterCollision(player.getbX() + dx, player.getbY())) {
					dy = -5;
					dx *= -2;
				}


				if(Math.abs(dx) < topSpeed){
					if(right) {
						player.setDirection('r');
						player.setState(1);
						dx += 0.6;
					} else if(left) {		
						player.setDirection('l');
						player.setState(1);
						dx -= 0.6;
					}
				}

				if (jump && dx < 0 && !left) {
					player.setDirection('k');
					player.setState(2);
				} else if (jump && dx > 0 && !right) {
					player.setDirection('j');
					player.setState(2);
				}

				if(map.checkCollision(player.getbX() + dx, player.getbY())) {
					dx *= -0.1;
				} else if(ground) {
					dx *= (1-friction);
					if(Math.abs(dx) < 0.1) {
						dx = 0.0;
					}
				}

				if(!ground) {
					dx *= (1-airDrag);
					dy += gravity;
					if(Math.abs(dx) < 0.1) {
						dx = 0.0;
					}
				} 

				if(map.checkCollision(player.getbX(), player.getbY() + dy)) {	
					if(player.getDirection() == 'k') {
						player.setDirection('l');
					} else if (player.getDirection() == 'j') {
						player.setDirection('r');
					}

					dy *= -0.01;

					if(Math.abs(dy) < 1) {
						dy = 0.0;
					}
					jump = false;
					ground = true;

				}


				if(map.checkCollision(player.getbX(), player.getbY() + 20) && jumpCounter > 1){
					jumpCounter = 0;
				}

				if(map.checkCollision(player.getbX(), player.getbY()) && jumpCounter > 0){
					dy += 5;
				}

				if(!map.checkCollision(player.getbX(), player.getbY() + 20)){
					ground = false;
				}

				if(player.getbX() > newCenterX) {
					newCenterX = player.getbX();
				}


				time.setText("Time:" + " " + Integer.toString((int)(System.currentTimeMillis()-startTime)/1000));
				player.setY(dy);	
				player.setX(dx);
				healthLabel.setText(Integer.toString(player.getHealth()) + " X ");
				points.setText(Integer.toString(player.getPoints()));

				if (player.getbY() > 2*height) {
					player.setHealth(-1);
					player.toStartPosition();
					player.setDirection('r');
					dx = 0;
					cam.setX(0);
					cam.setY(-350);
					newCenterX = width/2;
				}

				player.iterate(dx);
				map.iterate();

				if(player.getbX() > newCenterX) {
					cam.setCameraX(player);
				} 

				if(player.getbY() > height * 0.5 ){// || player.getbY() < height * 0.6) {
					cam.setCameraY(player);
				}

				map.repaint();
				Toolkit.getDefaultToolkit().sync();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		}


		if(gameState.equals("Game Menu")) {
			start.setVisible(true);
			revalidate();
			while(gameState.equals("Game Menu")) {
				this.requestFocus();// do nothing
			}

			if(gameState.equals("Highscore")){
				this.add(highscoreScreen);
				start.setVisible(false);
				highscoreScreen.setVisible(true);
				revalidate();

				while(gameState.equals("Highscore")) {
					highscoreScreen.requestFocus();
				}
				startLoop();
			}

			if(gameState.equals("Play")){
				newGameReset(0, 5);
			}

			if(gameState.equals("Exit")) {
				System.exit(0);


			} else if(gameState.equals("Paus")) {
				while(gameState.equals("Paus")) {
					this.requestFocus();// do nothing
				}
				startLoop();

			} else if(player.getState() == playerInGoal) {

				double endTime = System.currentTimeMillis();
				this.requestFocus();
				double radius = 1;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int i = 0;
				int p = 100 - (int)((endTime-startTime)/1000);
				while((endTime-startTime)/1000 - i > 0) {
					if(p > 0) {
						player.setPoints(1);
						p--;
					}
					points.setText(Integer.toString(player.getPoints()));
					time.setText("Time:" + " " + Integer.toString((int)(endTime-startTime)/1000 - i));
					i++;

					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				Graphics g = map.getGraphics();
				while(radius < width) {
					g.fillOval(width / 2 - (int) radius, height / 2 - (int) radius, 2 * (int) radius, 2 * (int) radius);
					radius *= 1.02;	
					Toolkit.getDefaultToolkit().sync();
					map.repaint();
				}
				
				if(currentMap < 2) {
					player.setHealth(1);
					currentMap++;// start with next map
					newGameReset(player.getPoints(), player.getHealth());
				} else {
					writeHighscore();
					gameState = "Game Menu";
					highscore = false;
					newGameReset(0, 5);

				}
			} else if(player.getHealth() == 0) {
				gameState = "Game Menu";
				newGameReset(0,5);
			}
		}

	}


	public void startLoop() {
		run();		
	}

	public void newGameReset(int points, int lives){
		System.out.println("Reset" + currentMap);

		if(map != null) {
			getContentPane().remove(map);
		}
		dx = 0;
		dy = 0;
		newCenterX = width / 2;
		player = new Player(points, lives);
		cam = new Camera(0,-350);
		left = right = false;
		ground = jump = false;
		map = new TileMap("MAP" + currentMap + ".txt", player, (int) newCenterX, cam);
		player.toStartPosition();
		getContentPane().add(map);
		setVisible(true);
		map.repaint();
		startTime = System.currentTimeMillis();
		run();
	}

	private void writeHighscore() {
		int savedPos = 3;
		int n = 0;
		for(int i : highscoreList) {
			if (player.getPoints() > i) {
				savedPos = n;
				highscore = true;
				break;
			} 
			n++;
		}
		highscoreList.add(savedPos, player.getPoints());
		if(highscoreList.size() > 3) {
			highscoreList.remove(3);
		}
		highscoreList.trimToSize();

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Highscore.txt"));
			writer.write("");

			for(int i : highscoreList) {
				System.out.println(Integer.toString(i));
				writer.write(Integer.toString(i));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void setStartScreen() {
		start.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			jump = false;

		}

		if (key == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			jump = false;

		}

		if (key == KeyEvent.VK_SPACE) {
			if(jumpCounter < 2) {
				dy = -10;
				ground = false;
				jumpCounter++;
			}
			jump = true;
//			try {
//				in = new FileInputStream(soundtrack);
//				audioStream = new AudioStream(in);
//				AudioPlayer.player.start(audioStream);
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			left = false;
			player.setState(0);
		}

		if (key == KeyEvent.VK_RIGHT) {
			right = false;
			player.setState(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void readImage() {
		try {
			mario = ImageIO.read(new File("mario.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void highscore() {
		File highscore = new File("Highscore.txt");
		String current;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(highscore));
			while((current = reader.readLine()) != null) {
				highscoreList.add(Integer.parseInt(current));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void placeMario() {
		JLabel mLabel = new JLabel((new ImageIcon(((new ImageIcon(mario)).getImage()).getScaledInstance(40, 60, java.awt.Image.SCALE_SMOOTH))));
		healthBox.add(mLabel);
		menuBar.add(healthBox);
	}

	public void setGameState(String state) {
		gameState = state;
	}
}
