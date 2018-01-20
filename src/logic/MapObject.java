package logic;

import java.awt.Graphics;
/**
 * 
 * Superclass for the different MapObjects in the game
 * 
 * ====== TILES ======
 * i = top left grass
 * g = grass center piece
 * k = top right grass
 * j = dirt
 * b = brick
 * a = top left pipe
 * r = top right pipe
 * c = left bottom pipe
 * d = right bottom pipe
 * o = overlap grass right
 * p = overlap grass left
 * v = start dirt
 * w = end dirt
 * H = player
 * z = coin
 * u = castle
 * q = flower
 * y = bug
 * f = heart
 * 
 *
 */



public abstract class MapObject {

	public MapObject(){
		
	}
	
	public abstract void paintYourself(Graphics g, int centerPos);
		
	public abstract boolean isIn(double x, double y);
	public abstract boolean isIn(double x, double y, Player player);
}
