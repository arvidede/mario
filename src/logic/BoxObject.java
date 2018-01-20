package logic;

import java.awt.Graphics;

import graphics.TileMap;

/**
 * 
 * A certain type of groundObject that can be destroyed on impact.  
 * 
 */
public class BoxObject extends MapObject{

	private int x;
	private int y;
	private char type;
	private int width = 60;
	private int height = 60;
	private double hit = 1.75; 
	private TileMap map;
	
	public BoxObject(int pixX, int pixY, char type, TileMap map) {
		super();
		x = pixX*width;
		y = pixY*height;
		this.type = type;
		this.map = map;
	}

	@Override
	public void paintYourself(Graphics g, int centerPos) {
		if (type == 'b' && hit > 0) {
			g.drawImage(map.getBox(), x, y, x + width, y + height, 0, 0, 16, 16, null);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return collision
	 */
	
	@Override
	public boolean isIn(double x, double y) {
		if(y < this.y + height && y + height > this.y && x + width * 3 / 4 > this.x && x < this.x + width * 3 / 4 && hit > 0) {
			hit -= 0.1;
			return true;
		}
		return false;
	}

	@Override
	public boolean isIn(double x, double y, Player player) {
		// TODO Auto-generated method stub
		return false;
	}


}
