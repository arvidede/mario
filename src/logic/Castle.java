package logic;

import graphics.TileMap;

import java.awt.Graphics;

/**
 * Holds and draws the end castle of the game.
 */

public class Castle extends MapObject {
	
	private int x;
	private int y;
	private char type;
	private int tileWidth = 60;
	private int tileHeight = 60;
	private int width = 300;
	private int height = 300;
	private TileMap map;
	
	public Castle(int pixX, int pixY, char type, TileMap map) {
		super();
		x = pixX*tileWidth;
		y = pixY*tileHeight;
		this.type = type;
		this.map = map;
	}
	

	@Override
	public void paintYourself(Graphics g, int centerPos) {
		if (type == 'u') {
			g.drawImage(map.getCastle(), x, y, x + width, y + height, 0, 0, 80, 80, null);
		}
	}

	
	public boolean isIn(double x, double y, Player player) {
		if( x  > this.x + width * 0.3 && x < this.x + width * 0.6) { //y < this.y + height && y + height > this.y &&
			player.setState(3);
		}
		return false;
	}


	@Override
	public boolean isIn(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
