package logic;

import java.awt.Graphics;

import graphics.TileMap;


/**
 * Collectable item heart, gives player extra health + 1.  
 * 
 */
public class Heart extends Collectables{
	
	private int x;
	private int y;
	private int width = 60;
	private int height = 60;
	private boolean hit = false;
	private TileMap map;
	public Heart(int pixX, int pixY, TileMap map) {
		super();
		x = pixX*width;
		y = pixY*height;
		this.map = map;
	}

	@Override
	public void paintYourself(Graphics g, int centerPos) {
		if(!hit) {
			g.drawImage(map.getHeart(), x, y + 2, x + width, y + height - 4, 0, 0, 328, 320, null);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return collision
	 */
	
	public boolean isIn(double x, double y, Player player) {
		if(y < this.y + height && y + height > this.y && x + width * 3 / 4 > this.x && x < this.x + width * 3 / 4 && !hit) {
			player.setHealth(1);
			hit = true;
		}
		return false;
	}

	@Override
	public boolean isIn(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
