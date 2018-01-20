package logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Player extends JComponent {

	/**
	 * 
	 * PLayer class: holds player coordinates and attributes. Is responsible for the image drawing of player. 
	 * 
	 * 
	 * State:
	 * 0 = still
	 * 1 = running
	 * 2 = jumping or flying
	 * 3 = Goal
	 * 
	 * Direction:
	 * r = right
	 * l = left
	 * j = jump
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double it = 0;

	private double x;
	private double y;
	private int startY;
	private int startX;
	private final int runWidth = 18;
	private final int runHeight = 32;
	private final int width = 60;
	private final int height = 60;
	private char direction = 'r';
	private int state = 0;
	private BufferedImage run;
	private BufferedImage[] runSheet;
	private BufferedImage dab;
	private int runningSprites = 8;
	private int points = 0;
	private int health;


	public Player(int points, int health) {
		this.points = points;
		this.health = health;
		System.out.print("Loading sprites...");
		readSprite();
		System.out.println("done.");
	}


	public void iterate(double dx) {

		//		switch (state)  {
		//
		//		case 0 :
		//			it = 0;
		//			break;
		//		case 1 :
		//			if (it < 7) {
		//				it += Math.round(pix/5.9);
		//			} else {
		//				it = 0;
		//			}
		//			break;
		//		}

		if(state == 0) {
			it = 0;
		} else if (state == 1) {
			if (it < 7) {
				it += Math.abs(dx/18); //Ändra här om topSpeed i game ändras
			} else {
				it = 0;
			}
		}


	}

	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

	public void printYourself(Graphics g) {
		int x = (int) this.x;
		int y  = (int) this.y;
		if(state == 3) {
			g.drawImage(dab, x, y, x + width, y + height, 0, 0, 24, 29, null);
		} else {
			switch (direction) {
			case 'l':
				g.drawImage(runSheet[(int)Math.round(it)], x, y, x + width, y + height, 18, 0, 0, 32, null);
				break;
			case 'r':
				g.drawImage(runSheet[(int)Math.round(it)], x, y, x + width, y + height, 0, 0, 18, 32, null);
				break;
			case 'j':
				g.drawImage(runSheet[2], x, y, x + width, y + height, 0, 0, 18, 32, null);
				break;
			case 'k':
				g.drawImage(runSheet[2], x, y, x + width, y + height, 18, 0, 0, 32, null);
				break;
			}
		}
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public void readSprite() {

		runSheet = new BufferedImage[runningSprites];


		try {
			run = ImageIO.read(new File("mariorunright.png"));
			dab = ImageIO.read(new File("mariodab24x29.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}		

		for (int i = 0; i < runningSprites; i++) {
			runSheet[i] = run.getSubimage(runWidth*i, 0, runWidth, runHeight);
		}

	}
	
	public void resetHealthAndPoints() {
		health = 3;
		points = 0;
	}

	public double getbX() {
		return x;
	}

	public void setX(double x) {
		this.x += x;
	}

	public double getbY() {
		return y;
	}

	public void setY(double y) {
		this.y += y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	public char getDirection() {
		return direction;
	}

	public void setStartPos(int x, int y) {
		startX = x;
		startY = y;
	}

	public void toStartPosition() {
		x = startX;
		y = startY;
	}

	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points += points;
	}


	public int getHealth() {
		return health;
	}

	public boolean isAlive() {
		return health > 0;
	}


	public void setHealth(int health) {
		this.health += health;
	}

}
