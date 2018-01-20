package logic;

import java.awt.Graphics;

import graphics.TileMap;

/**
 * 
 * Draws and contains the groundObjecets
 * 
 */

public class GroundObject extends MapObject{

	private int x;
	private int y;
	private char type;
	private int width = 60;
	private int height = 60;
	private TileMap map;

	public GroundObject(int pixX, int pixY, char type, TileMap map) {
		super();
		//super(pixY, pixY, type, map);
		x = pixX*width;
		y = pixY*height;
		this.type = type;
		this.map = map;

	}

	@Override
	public void paintYourself(Graphics g, int centerPos) {
		if (type == 'i') {
			g.drawImage(map.getGrassSheet(0), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'j') {
			g.drawImage(map.getDirtSheet(1), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'v') {
			g.drawImage(map.getDirtSheet(0), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'w') { 
			g.drawImage(map.getDirtSheet(2), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'g') {
			g.drawImage(map.getGrassSheet(1), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'k') {
			g.drawImage(map.getGrassSheet(2), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'o') {
			g.drawImage(map.getOverlapSheet(1), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'p') {
			g.drawImage(map.getOverlapSheet(0), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'r') {
			g.drawImage(map.getTubeSheet(1), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'd') {
			g.drawImage(map.getTubeSheet(3), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'a') {
			g.drawImage(map.getTubeSheet(0), x, y, x + width, y + height, 0, 0, 16, 16, null);
		} else if (type == 'c') {
			g.drawImage(map.getTubeSheet(2), x, y, x + width, y + height, 0, 0, 16, 16, null);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return collision
	 */


	@Override
	public boolean isIn(double x, double y, Player player) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isIn(double x, double y) {

		return y < this.y + height && y + height > this.y && x + width * 3 / 4 > this.x && x < this.x + width * 3 / 4;
	}

}
