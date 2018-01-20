package graphics;

import logic.Player;

/**
 * Contains the camera and is responsible for displaying the correct part of the map.
 */
public class Camera{

	private double x;
	private double y;

	public Camera(double x, double y){
		this.x = x;
		this.y = y;
	}

	public void setCameraX(Player player){
		x = -player.getbX() + Game.width / 2;
	}
	
	public void setCameraY(Player player){
		y = -player.getbY() + Game.height / 3; 
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
