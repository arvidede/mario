package logic;

import java.awt.Graphics;

/**
 * Superclass for Monsters in the game. Extends MapObject
 * 
 */

public abstract class Monster extends MapObject{
	
	public Monster() {
	
	}

	@Override
	public abstract void paintYourself(Graphics g, int centerPos);

	/**
	 * @param x
	 * @param y
	 * @return collision
	 */
	
	public abstract boolean isIn(double x, double y, Player player);
	
	public abstract void iterate();

}
