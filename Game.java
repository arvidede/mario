//
//package graphics;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.KeyStroke;
//
//import logic.Player;
//
////import javax.swing.JFrame;
//
//public class Game extends JFrame implements KeyListener, Runnable {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	public static int height;
//	public static int width;
//	public static final int scale = 2;
//	public static final int topSpeed = 3;
//	public static final int noScreens = 2;
//	public int jumpCounter = 0;
//
//	private Thread timer;
//
//	private boolean left, right, ground, jump; 
//
//	private double dx = 0.0;
//	private double dy = 0.0;
//
//	private double friction = 0.1;
//	private double gravity = 0.3;
//	private double airDrag = 0.01;
//
//	private Player player;
//	private final TileMap map;
//	private Camera cam;
//
//	private double newCenterX;
//
//	public Game() {
//		super("Test");
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		pack();
//		setSize(screenSize.width / noScreens, screenSize.height);
//		height = screenSize.height;
//		width = screenSize.width / noScreens;
//		newCenterX = width / 2;
//		cam = new Camera(0,-350);
//		left = right = false;
//		ground = jump = false;
//		
//		player = new Player(400, 600);
//		
//		JMenuBar menuBar = new JMenuBar();
//		JMenu menu = new JMenu("Paus");
//		JMenuItem restart = new JMenuItem("Restart");
//		restart.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//		restart.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				player.startPosition();
//				cam.setX(0);
//				cam.setY(-350);
//				newCenterX = width/2;
//			}
//		});
//		
//		menuBar.add(menu);
//		menu.add(restart);
//		add(menuBar, BorderLayout.NORTH);
//		
//		
//		
//		
//		
//		map = new TileMap("MAP1.txt", player, (int) dx, cam);
//
//		//setPreferredSize(new Dimension(width * scale, height * scale));
//		//setResizable(false);
//		getContentPane().setBackground(new Color(147, 206, 254));
//		setVisible(true);
//		getContentPane().add(map);
//		
//
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		timer = new Thread(this);
//		timer.start();
//		addKeyListener(this);
//
//
//		map.repaint();
//
//
//
//
//
//	}
//
//	public void run() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		while(true) {
//
//			if(Math.abs(dx) < topSpeed){
//				if(right) {
//					player.setDirection('r');
//					player.setState(1);
//					dx += 0.4;
//				} else if(left) {		
//					player.setDirection('l');
//					player.setState(1);
//					dx -= 0.4;
//				}
//			}
//
//			if (jump && dx < 0 && !left) {
//				player.setDirection('k');
//				player.setState(2);
//			} else if (jump && dx > 0 && !right) {
//				player.setDirection('j');
//				player.setState(2);
//			}
//
//
//			if(map.checkCollision(player.getbX() + dx, player.getbY())) {
//				dx *= -0.1;
//			} else if(ground) {
//				dx *= (1-friction);
//				if(Math.abs(dx) < 0.1) {
//					dx = 0.0;
//				}
//			}
//
//			if(!ground) {
//				dx *= (1-airDrag);
//				dy += gravity;
//				if(Math.abs(dx) < 0.1) {
//					dx = 0.0;
//				}
//			} 
//
//			if(map.checkCollision(player.getbX(), player.getbY() + dy)) {	
//				
//				if(player.getDirection() == 'k') {
//					player.setDirection('l');
//				} else if (player.getDirection() == 'j') {
//					player.setDirection('r');
//				}
//				
//				if(Math.abs(dy) < 1) {
//					dy = 0.0;
//				}
//				jump = false;
//				ground = true;
//				dy *= -0.06;
//			}
//			
//			if(map.checkCollision(player.getbX(), player.getbY() + 20) && jumpCounter > 1){
//				jumpCounter = 0;
//			}
//
//			if(!map.checkCollision(player.getbX(), player.getbY() + 20)){
//				ground = false;
//			}
//
//			if(player.getbX() > newCenterX) {
//				newCenterX = player.getbX();
//			}
//
//
//			player.setY((int) dy);	
//			player.setX((int) dx);
//
//			player.iterate(Math.abs(dx));
//			
//			if(player.getbX() > newCenterX) {
//				cam.setCameraX(player);
//			} 
//
//
//			if(player.getbY() > height * 0.8) { // || player.getbY() < height * 0.2) {
//				cam.setCameraY(player);
//			}
//			
//			map.repaint();
//			Toolkit.getDefaultToolkit().sync();
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		int key = e.getKeyCode();
//
//		if (key == KeyEvent.VK_LEFT) {
//			left = true;
//			right = false;
//			jump = false;
//
//		}
//
//		if (key == KeyEvent.VK_RIGHT) {
//			right = true;
//			left = false;
//			jump = false;
//
//		}
//
//		if (key == KeyEvent.VK_SPACE) {
//			if(jumpCounter < 2) {
//				dy = -10;
//				ground = false;
//				jumpCounter++;
//			}
//			jump = true;
//
//		}
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		int key = e.getKeyCode();
//		
//		if (key == KeyEvent.VK_LEFT) {
//			left = false;
//			player.setState(0);
//		}
//
//		if (key == KeyEvent.VK_RIGHT) {
//			right = false;
//			player.setState(0);
//		}
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//}
