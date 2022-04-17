package main;

import java.awt.Color;
import java.util.LinkedList;

import interfacecomponents.BuildBtnEnums;
import interfacecomponents.BuildTowerButton;
import interfacecomponents.BuildUIBackground;
import interfacecomponents.SelectableInfoPanel;
import interfacecomponents.WavePanel;
import units.Archer;
import units.Block;
import units.Catapult;
import units.Entity;
import units.Haven;
import units.Path;
import units.Projectile;
import units.Selectable;
import units.Stone;
import units.Tower;
import units.Wizard;
import visuals.Map;
import visuals.Spritesheet;

public class Board {
	
	private static Board board = null;
	
	//MAPS
	private static Map map1 = new Map("/map1.png");
	
	//LISTS OF GAME OBJECTS
	
	private static LinkedList<Entity> entities = new LinkedList<Entity>();
	private static LinkedList<Entity> slowSpawn = new LinkedList<Entity>();
	private static LinkedList<Entity> medSpawn = new LinkedList<Entity>();
	private static LinkedList<Entity> fastSpawn = new LinkedList<Entity>();
	
	private static LinkedList<Block> blocks = new LinkedList<Block>();
	
	private static LinkedList<Tower> towers = new LinkedList<Tower>();
	private static LinkedList<Tower> towersQueue = new LinkedList<Tower>();
	
	private static LinkedList<Projectile> projectiles = new LinkedList<Projectile>();
	
	//List of anything that is a subclass of Selectable(buildBtns, towers, entities)
	private static LinkedList<Selectable> selectables = new LinkedList<Selectable>();
	
	//LISTS OF INTERFACE OBJECTS
	private static LinkedList<BuildTowerButton> buildBtns = new LinkedList<BuildTowerButton>();
	
	private static int spawnPointX = 1280;
	private static int spawnPointY = (720/2);
	
	private static int gold = 0; //add gold through addGold-method
	private static int hp = 100;
	
	private static int waveCD = 10;
	private static int wave = 1;
	private static WavePanel wavePanel;
	
	//BUILD BUTTONS
	private static BuildUIBackground buildUIBackground;
	private static BuildTowerButton archerBuildBtn;
	private static BuildTowerButton wizardBuildBtn;
	private static BuildTowerButton catapultBuildBtn;
	
	//Build UI BACKGROUND
	
	//SELECTABLE INFO UI
	static SelectableInfoPanel infoPanel = null;
	static Selectable selected = null;
	
	public static Spritesheet spriteSheet = new Spritesheet("/sprites.png");
	
	Board() {
		
		//MAPS
		Map map1 = new Map("/map1.png");
		
		//LISTS OF GAME OBJECTS
		
		LinkedList<Entity> entities = new LinkedList<Entity>();
		LinkedList<Block> blocks = new LinkedList<Block>();
		LinkedList<Tower> towers = new LinkedList<Tower>();
		LinkedList<Projectile> projectiles = new LinkedList<Projectile>();
		LinkedList<Tower> towersQueue = new LinkedList<Tower>();
		
		//List of anything that is a subclass of Selectable(buildBtns, towers, entities)
		LinkedList<Selectable> selectables = new LinkedList<Selectable>();
		
		//LISTS OF INTERFACE OBJECTS
		LinkedList<BuildTowerButton> buildBtns = new LinkedList<BuildTowerButton>();
		
		int spawnPointX = 1280;
		int spawnPointY = (720/2)-(Block.size/2);
		
		SelectableInfoPanel infoPanel = null;
		
		
	}
	
	public static void initStats() {
		
		setGold(100);
		setHp(100);
		wave=1;
		waveCD=10;
		
	}

	public static void initUI() {
		
		buildUIBackground = new BuildUIBackground(1230, 10);
		archerBuildBtn = new BuildTowerButton(1235, 20, Archer.ARCHER_VALUE, BuildBtnEnums.archer);
		wizardBuildBtn = new BuildTowerButton(1235, 80, Wizard.WIZARD_VALUE, BuildBtnEnums.wizard);
		catapultBuildBtn = new BuildTowerButton(1235, 140, Catapult.CATAPULT_VALUE, BuildBtnEnums.catapult);
		
		buildBtns.add(archerBuildBtn);
		buildBtns.add(wizardBuildBtn);
		buildBtns.add(catapultBuildBtn);
		
		selectables.addAll(buildBtns);
		
		wavePanel = new WavePanel();
		
	}

	public static BuildUIBackground getBuildUIBackground() {
		return buildUIBackground;
	}

	public static void setBuildUIBackground(BuildUIBackground buildUIBackground) {
		Board.buildUIBackground = buildUIBackground;
	}

	public static void initMap() {
		
		//map1
		for(int i = 0; i < 18; i++) {
			for(int i2 = 0; i2 < 32; i2++) {
				if(map1.getPixelRGB(i2, i) == Color.black.getRGB()) {
					blocks.add(new Stone(i2*Block.size,i*Block.size, 0));
				}
				else if(map1.getPixelRGB(i2, i) == Color.decode("#777777").getRGB()) {
					blocks.add(new Stone(i2*Block.size,i*Block.size, 1));
				}
				else if(map1.getPixelRGB(i2, i) == Color.decode("#b9b9b9").getRGB()) {
					blocks.add(new Stone(i2*Block.size,i*Block.size, 2));
				}
				else if(map1.getPixelRGB(i2, i) == Color.WHITE.getRGB()) {
					blocks.add(new Path(i2*Block.size,i*Block.size));
				}
				else if(map1.getPixelRGB(i2, i) == Color.GREEN.getRGB()) {
					blocks.add(new Haven(i2*Block.size,i*Block.size));
				}
					
			}
			
		}	
		
	}
	
	public static void addTower(Tower tower) { //ALWAYS USE THIS TO ADD A NEW TOWER
		
		tower.setX((int) (Math.floor(tower.getX() / Tower.getSize())) * Tower.getSize());
		tower.setY((int) (Math.floor(tower.getY() / Tower.getSize())) * Tower.getSize());
		towersQueue.add(tower);
			
	}
	
	public static void addEntity(Entity entity) { //ALWAYS USE THIS TO ADD A NEW ENTITY
			
			
			entities.add(entity);
			selectables.add(entity);

			System.out.println(entities.size());
			System.out.println(selectables.size());
			
		}

	public static void addBuildBtn(BuildTowerButton btn) { //ALWAYS USE THIS TO ADD A NEW BUTTON
		
		buildBtns.add(btn);
		selectables.add(btn);
			
	}
	
	public static void addGold(int amount) {
		
		gold+=amount;
		changeTowerAvailability();
			
	}
	
	public static void changeTowerAvailability() {
		if(buildBtns.isEmpty())
			return;
			
		for (BuildTowerButton b : buildBtns) {
			if(gold >= b.getGoldReq()) {
			b.setAvailable(true);
			}
		}
	}

	/**
	 * @return the board
	 */
	public static Board getBoard() {
		if(board == null)
			board = new Board();
		
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public static void setBoard(Board board) {
		Board.board = board;
	}

	/**
	 * @return the map1
	 */
	public static Map getMap1() {
		return map1;
	}

	/**
	 * @param map1 the map1 to set
	 */
	public static void setMap1(Map map1) {
		Board.map1 = map1;
	}

	/**
	 * @return the entities
	 */
	public static LinkedList<Entity> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public static void setEntities(LinkedList<Entity> entities) {
		Board.entities = entities;
	}

	/**
	 * @return the blocks
	 */
	public static LinkedList<Block> getBlocks() {
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public static void setBlocks(LinkedList<Block> blocks) {
		Board.blocks = blocks;
	}

	/**
	 * @return the towers
	 */
	public static LinkedList<Tower> getTowers() {
		return towers;
	}

	/**
	 * @param towers the towers to set
	 */
	public static void setTowers(LinkedList<Tower> towers) {
		Board.towers = towers;
	}

	/**
	 * @return the projectiles
	 */
	public static LinkedList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * @param projectiles the projectiles to set
	 */
	public static void setProjectiles(LinkedList<Projectile> projectiles) {
		Board.projectiles = projectiles;
	}

	/**
	 * @return the selectables
	 */
	public static LinkedList<Selectable> getSelectables() {
		return selectables;
	}

	/**
	 * @param selectables the selectables to set
	 */
	public static void setSelectables(LinkedList<Selectable> selectables) {
		Board.selectables = selectables;
	}

	/**
	 * @return the buildBtns
	 */
	public static LinkedList<BuildTowerButton> getBuildBtns() {
		return buildBtns;
	}

	/**
	 * @param buildBtns the buildBtns to set
	 */
	public static void setBuildBtns(LinkedList<BuildTowerButton> buildBtns) {
		Board.buildBtns = buildBtns;
	}

	/**
	 * @return the spawnPointX
	 */
	public static int getSpawnPointX() {
		return spawnPointX;
	}

	/**
	 * @param spawnPointX the spawnPointX to set
	 */
	public static void setSpawnPointX(int spawnPointX) {
		Board.spawnPointX = spawnPointX;
	}

	/**
	 * @return the spawnPointY
	 */
	public static int getSpawnPointY() {
		return spawnPointY;
	}

	/**
	 * @param spawnPointY the spawnPointY to set
	 */
	public static void setSpawnPointY(int spawnPointY) {
		Board.spawnPointY = spawnPointY;
	}

	/**
	 * @return the gold
	 */
	public static int getGold() {
		return gold;
	}

	/**
	 * @param gold the gold to set
	 */
	public static void setGold(int gold) {
		Board.gold = gold;
	}

	/**
	 * @return the hp
	 */
	public static int getHp() {
		return hp;
	}

	/**
	 * @param hp the hp to set
	 */
	public static void setHp(int hp) {
		Board.hp = hp;
	}

	/**
	 * @return the archerBuildBtn
	 */
	public static BuildTowerButton getArcherBuildBtn() {
		return archerBuildBtn;
	}

	/**
	 * @param archerBuildBtn the archerBuildBtn to set
	 */
	public static void setArcherBuildBtn(BuildTowerButton archerBuildBtn) {
		Board.archerBuildBtn = archerBuildBtn;
	}

	/**
	 * @return the towersQueue
	 */
	public static LinkedList<Tower> getTowersQueue() {
		return towersQueue;
	}

	/**
	 * @param towersQueue the towersQueue to set
	 */
	public static void setTowersQueue(LinkedList<Tower> towersQueue) {
		Board.towersQueue = towersQueue;
	}

	/**
	 * @return the infoPanel
	 */
	public SelectableInfoPanel getInfoPanel() {
		return infoPanel;
	}

	/**
	 * @param infoPanel the infoPanel to set
	 */
	public static void setInfoPanel(SelectableInfoPanel infoPanel) {
		Board.infoPanel = infoPanel;
	}

	public static int getWaveCD() {
		return waveCD;
	}

	public static void setWaveCD(int waveCD) {
		Board.waveCD = waveCD;
	}
	
	public static void decrementWaveCD() {
		Board.waveCD = waveCD - 1;
	}

	public static WavePanel getWavePanel() {
		return wavePanel;
	}

	public static void setWavePanel(WavePanel wavePanel) {
		Board.wavePanel = wavePanel;
	}

	public static int getWave() {
		return wave;
	}

	public static void setWave(int wave) {
		Board.wave = wave;
	}

	public static LinkedList<Entity> getSlowSpawn() {
		return slowSpawn;
	}

	public static void setSlowSpawn(LinkedList<Entity> slowSpawn) {
		Board.slowSpawn = slowSpawn;
	}

	public static LinkedList<Entity> getMedSpawn() {
		return medSpawn;
	}

	public static void setMedSpawn(LinkedList<Entity> medSpawn) {
		Board.medSpawn = medSpawn;
	}

	public static LinkedList<Entity> getFastSpawn() {
		return fastSpawn;
	}

	public static void setFastSpawn(LinkedList<Entity> fastSpawn) {
		Board.fastSpawn = fastSpawn;
	}
	
	public static boolean isEntityQueuesEmpty() {
		if(fastSpawn.isEmpty() && medSpawn.isEmpty() && slowSpawn.isEmpty()) {
			return true;
		}
		else
			return false;
		
	}
	
	//TODO: förbättra
	public static boolean isBuildAbleSpot(int x, int y) {
		
			for(Tower t : getTowers()) {
				if(x > t.x && y > t.y && x < t.x + Tower.getSize() && y < t.y + Tower.getSize()) {
					return false;
				}
			}
		
		return true;
	}
	
	public static void incrementWave() {
		wave++;
	}
	
	

}
