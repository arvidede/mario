package logic;

import java.awt.Graphics;

/**
 * @author arved490
 * @author harek048
 *
 *superclass for collectable items. Extends MapObject
 *
 */

public abstract class Collectables extends MapObject{
	public Collectables() {
		super();
	}

	@Override
	public abstract void paintYourself(Graphics g, int centerPos);

	
	/**
	 * @param x
	 * @param y
	 * @return collision
	 */
	
	public abstract boolean isIn(double x, double y, Player player);

	@Override
	public boolean isIn(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
