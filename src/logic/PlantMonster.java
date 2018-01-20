package logic;

import graphics.TileMap;

import java.awt.Graphics;

/**
 * 
 * Certain type of monster which extends Monster class. 
 */

public class PlantMonster extends Monster {

	private double it = 0;

	private int x;
	private int y;
	private char type;
	private int tileWidth = 60;
	private int tileHeight = 60;
	private int imgWidth = 100;
	private int imgHeight = 88;
	private boolean hit = false;
	private boolean up = true;
	private TileMap map;

	public PlantMonster(int pixX, int pixY, char type, TileMap map) {
		super();
		x = pixX*tileWidth;
		y = pixY*tileHeight;
		this.type = type;
		this.map = map;
	}

	public void iterate() {

		if (it < 120 && up) {
			it += 0.5;
		} else if (it >= 120 && up){
			up = false;
		} else if (it > 0) {
			it -= 0.5;
		} else {
			up = true;
		}
	}


	@Override
	public void paintYourself(Graphics g, int centerPos) {
		if (type == 'q' && !hit && it < 88) {
			g.drawImage(map.getMonsterPlantSheet((int) Math.round(it/30)), x + 15, y + (int) Math.round(it) + 32, x + imgWidth + 15, y + imgHeight + 32, 0, 0, 32, 44 - (int) Math.round(it) / 2, null);
		}                                            
	}

	/**
	 * @param x
	 * @param y
	 * @return collision
	 */

	@Override
	public boolean isIn(double x, double y, Player player) {
		if(y < this.y + Math.round(it) + tileHeight && y + tileHeight > this.y + Math.round(it) + 32 && x + tileWidth * 3 / 4 > this.x + 15 && x < this.x + 15 + imgWidth * 3 / 4) {
			player.setHealth(-1);
			return true;
		}
		return false;
	}

	@Override
	public boolean isIn(double x, double y) {
		return false;
	}
}
