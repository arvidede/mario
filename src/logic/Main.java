package logic;

import graphics.Game;
/**
 * 
 * @author harek048
 * @author arved490
 * 
 * Super Mario game. Initiates the game
 * 
 * TODO:
 * Menu **done** ish
 * Monster Collision --> player loose health
 * Powerup extra health --> take in a sprite (hearth) create code in Collision class
 * Clock which will transform to score when finish map
 * One more map
 * 
 * 
 * Boss -- not priority
 * Sound effects -- not priority
 * Clouds -- not priority
 *
 *
 * 
 * 
 * 
 * Remaining issues:
 * must check separate up collision 
 * 
 *
 */

public class Main {
	
	public Main() {
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
