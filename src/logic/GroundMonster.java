package logic;

import graphics.TileMap;

import java.awt.Graphics;
import java.util.Random;

/**
 * 
 * Certain type of monster, extends Monster class. Can walk
 * 
 */

public class GroundMonster extends Monster {

	private double it = 0;

	private int x;
	private int y;
	private double dx;
	private char type;
	private int tileWidth = 60;
	private int tileHeight = 60;
	private int imgWidth = 38;
	private int imgHeight = 39;
	private int count = 0;
	private boolean hit = false;
	private boolean killed = false;
	private boolean right;
	private TileMap map;
	private Random random;

	public GroundMonster(int pixX, int pixY, char type, TileMap map) {
		super();
		random = new Random();
		right = random.nextBoolean();
		x = pixX*tileWidth;
		y = pixY*tileHeight + 25;
		this.type = type;
		this.map = map;
	}


	public void iterate() {
		if (it < 4) {
			it += 0.1;
		} else {
			it = 0;
		}

		if (dx < 60 && right) {
			dx += 1;
		} else if (dx == 60 && right){
			right = false;
		} else if (dx > - 60) {
			dx -= 1;
		} else {
			right = true;
		}	

		if(hit) {
			count += 1;
			if(count == 60) {
				hit = false;
				count = 0;
			}
		}
	}

	@Override
	public void paintYourself(Graphics g, int centerPos) {
		if (type == 'y' && right && !killed) {
			g.drawImage(map.getMonsterBugSheet((int) Math.round(it)), x + (int) dx , y, x + (int) dx + tileWidth, y + imgHeight, 0, 0, imgWidth, imgHeight, null);
		} else  if(type == 'y' && !right && !killed) {
			g.drawImage(map.getMonsterBugSheet((int) Math.round(it)), x + (int) dx, y, x + (int) dx + tileWidth, y + imgHeight, imgWidth, 0, 0, imgHeight, null);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return collision
	 */

	@Override
	public boolean isIn(double x, double y, Player player) {
		if(x + tileWidth > this.x + (int) dx && x < this.x + (int) dx + tileWidth  && !hit && y < this.y + imgHeight && y + imgHeight > this.y
				&& !hit && !killed) {
			if(y + 30 < this.y) {
				killed = true;
				player.setPoints(10);
			} else {
				hit = true;
				player.setHealth(-1);

			}
			return true;
		}
		return false;
	}


	@Override
	public boolean isIn(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
}

