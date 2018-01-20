package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import logic.BoxObject;
import logic.Castle;
import logic.Coins;
import logic.Collectables;
import logic.GroundMonster;
import logic.GroundObject;
import logic.Heart;
import logic.MapObject;
import logic.Monster;
import logic.PlantMonster;
import logic.Player;

public class TileMap extends JComponent {

	/**
	 * Contains and draws the tilemap
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<MapObject> objectList = new ArrayList<MapObject>();
	ArrayList<Monster> monsterList = new ArrayList<Monster>();
	ArrayList<Collectables> collectableList = new ArrayList<Collectables>();
	private Player player;

	private final int width = 16; //till alla ground object
	private final int height = 16;//till alla ground object
	private final int dirtSize = 3;
	private final int dirtRows = 1;
	private final int dirtColumns = 3;
	private final int grassSize = 3;
	private final int grassRows= 1;
	private final int grassColumns = 3;
	private final int tubeSize = 4;
	private final int tubeRows = 2;
	private final int tubeColumns = 2;
	private final int overlapSize = 2;
	private final int overlapRows = 1;
	private final int overlapColumns = 2;
	private final int plantSize = 4;
	private final int plantRows = 1;
	private final int plantColumns = 4;
	private final int bugSize = 5;
	private final int bugRows = 1;
	private final int bugColumns = 5;
	private final int tileSize = 60;
	private final int coinColumns = 3;
	private int mapWidth; //for future use
	private int mapHeight;  //for future use

	private BufferedImage tube;
	private BufferedImage[] tubeSheet;

	private BufferedImage grass;
	private BufferedImage[] grassSheet;;

	private BufferedImage overlap;
	private BufferedImage[] overlapSheet;

	private BufferedImage dirt;
	private BufferedImage[] dirtSheet;

	private BufferedImage box;

	private BufferedImage coins;
	private BufferedImage[] coinSheet;
	
	private BufferedImage monsterPlant;
	private BufferedImage[] monsterPlantSheet;

	private BufferedImage monsterBug;
	private BufferedImage[] monsterBugSheet;
	
	private BufferedImage castle;
	private Castle castleObj;
	
	private BufferedImage heart; 

	private int centerPos;
	private Camera cam;

	public TileMap (String mapName, Player player, int centerPos, Camera cam) {
		this.player = player;
		this.centerPos = centerPos;
		this.cam = cam;
		System.out.print("Loading map...");

		coinSheet = new BufferedImage[coinColumns];
		tubeSheet = new BufferedImage[tubeSize];
		overlapSheet = new BufferedImage[overlapSize];
		grassSheet = new BufferedImage[grassSize];
		dirtSheet = new BufferedImage[dirtSize];
		monsterPlantSheet = new BufferedImage[plantSize];
		monsterBugSheet = new BufferedImage[bugSize];
		
		readFile(mapName);
		readImages();
		System.out.println(" done.");
	}

	public void centerPosition(int centerPos) {
		this.centerPos = centerPos;
	}


	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(cam.getX(), cam.getY());

		for(MapObject obj : objectList ) {
			obj.paintYourself(g, centerPos);
		}

		for(MapObject obj : collectableList) {
			obj.paintYourself(g, centerPos);
		}
		
		for(Monster obj : monsterList) {
			obj.paintYourself(g, centerPos);
		}

		
		castleObj.paintYourself(g, centerPos);
		player.printYourself(g);


		g2d.translate(-cam.getX(), -cam.getY());


	}

	public boolean checkCollision (double x, double y) {

		if(castleObj.isIn(x,y, player)) {
			return true; // görs aldrig, isIn ska bara returnera false
		}

		for(MapObject obj : objectList ) {	

			if(obj.isIn(x,y)) {
				return true;
			}
		}

		for(Collectables obj : collectableList) {	

			if(obj.isIn(x,y, player)) {
				return true; // görs aldrig, isIn ska bara returnera false
			}
		}
		return false;
	}
	
	public boolean checkMonsterCollision(double x, double y) {
		for(Monster obj : monsterList) {	

			if(obj.isIn(x,y, player)) {
				return true; // görs aldrig, isIn ska bara returnera false
			}
		}

		return false;
	}


	private void readImages() {
		try {
			coins = ImageIO.read(new File("coins.png"));
			tube = ImageIO.read(new File("pipe.png"));			
			overlap = ImageIO.read(new File("overlap.png"));
			grass = ImageIO.read(new File("grass.png"));
			dirt = ImageIO.read(new File("dirt.png"));
			box = ImageIO.read(new File("b.png"));
			castle = ImageIO.read(new File("castle80x80.png"));
			heart = ImageIO.read(new File("heart328x320.png"));
			monsterPlant = ImageIO.read(new File("blomma128x44.png"));
			monsterBug = ImageIO.read(new File("bug39x39.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		box.getSubimage(0, 0, width, height);

		for (int i = 0; i < dirtRows; i ++) {
			for(int j = 0; j < dirtColumns; j++) {
				dirtSheet[(i*dirtRows) + j] = dirt.getSubimage(j*width, i*height, width, height);
			}
		}

		for (int i = 0; i < tubeRows; i ++) {
			for(int j = 0; j < tubeColumns; j++) {
				tubeSheet[(i*tubeRows) + j] = tube.getSubimage(j*width, i*height, width, height);
			}
		}

		for (int i = 0; i < overlapRows; i ++) {
			for(int j = 0; j < overlapColumns; j++) {
				overlapSheet[(i*1) + j] = overlap.getSubimage(j*width, i*height, width, height);
			}
		}


		for (int i = 0; i < grassRows; i ++) {
			for(int j = 0; j < grassColumns; j++) {
				grassSheet[(i*1) + j] = grass.getSubimage(j*width, i*height, width, height);
			}
		}

		for(int j = 0; j < coinColumns; j++) {
			coinSheet[j] = coins.getSubimage(j*16, 0, 16, 18);
		}
		
		for (int i = 0; i < plantRows; i ++) {
			for(int j = 0; j < plantColumns; j++) {
				monsterPlantSheet[(i*1) + j] = monsterPlant.getSubimage(j*32, i*44, 32, 44);
			}
		}
		
		for (int i = 0; i < bugRows; i ++) {
			for(int j = 0; j < bugColumns; j++) {
				monsterBugSheet[(i*1) + j] = monsterBug.getSubimage(j * 39, i*39, 38, 39);
			}
		}

	}

	public BufferedImage getCoin(int i) {
		return coinSheet[i];
	}

	public BufferedImage getBox() {
		return box;
	}

	public BufferedImage getCastle() {
		return castle;
	}
	
	public BufferedImage getHeart() {
		return heart;
	}

	public BufferedImage getTubeSheet(int i) {
		return tubeSheet[i];
	}

	public BufferedImage getGrassSheet(int i) {
		return grassSheet[i];
	}

	public Image getOverlapSheet(int i) {
		return overlapSheet[i];
	}

	public Image getDirtSheet(int i) {
		return dirtSheet[i];
	}
	
	public BufferedImage getMonsterPlantSheet(int i) {
		return monsterPlantSheet[i];
	}

	public BufferedImage getMonsterBugSheet(int i) {
		return monsterBugSheet[i];
	}

	public void eraseMapObject(MapObject obj) {
		objectList.remove(obj);
	}


	public void createMapObjects(int x, int y, char type, TileMap map) {
		if (type == 'i' || type == 'j' || type == 'v' || type == 'w' || type == 'g' || type == 'k' || type == 'o' || type == 'p' || type == 'r' 
				|| type == 'd' || type == 'a' || type == 'c') {
			objectList.add(new GroundObject(x, y, type, map));
		} else if (type == 'b') {
			objectList.add(new BoxObject(x, y, type, map));
		} else if (type == 'z') {
			collectableList.add(new Coins(x, y, map));
		} else if(type == 'f') {
			collectableList.add(new Heart(x, y, map));
		} else if(type == 'u') {
			castleObj = new Castle(x, y, type, map);
		} else if(type == 'q') {
			monsterList.add(new PlantMonster(x, y, type, map));
		} else if(type == 'y') {
			monsterList.add(new GroundMonster(x, y, type, map));
		}
	}

	public void readFile(String mapName) {
		File file = new File(mapName);
		String current = "";
		String split = ",";
		String[] ch;
		int x;
		int y;
		char type;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			current = reader.readLine();
			ch = current.split(split);
			mapWidth = Integer.parseInt(ch[0]);
			mapHeight = Integer.parseInt(ch[0]);
			player.setStartPos(Integer.parseInt(ch[2]) * tileSize, Integer.parseInt(ch[3]) * tileSize);

			while((current = reader.readLine()) != null) {
				ch = current.split(split);
				x = Integer.parseInt(ch[0]);
				y = Integer.parseInt(ch[1]);
				type =  ch[2].charAt(0);
				createMapObjects(x, y, type, this);	
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void iterate() {
		for(Monster m : monsterList) {
			m.iterate();
		}
		
	}
}
