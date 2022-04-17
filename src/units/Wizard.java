package units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.Board;

public class Wizard extends Tower {

	public static final int WIZARD_LVL1_RANGE = 160;
	public static final int WIZARD_LVL1_DMG = 30;
	public static final int WIZARD_LVL1_RATE = 100; //Rate, in game ticks
	public static final int WIZARD_VALUE = 100;
	public static final int COST_UPGRADE0 = 60;
	public static final int COST_UPGRADE1 = 80;
	
	public static final TowerEnum WIZARD_BASIC_TYPE = TowerEnum.wizard_basic;
	public static final TowerEnum WIZARD_EXPERIENCED_TYPE = TowerEnum.wizard_experienced;
	public static final TowerEnum WIZARD_MASTER_TYPE = TowerEnum.wizard_master;
	
	public static final int WIZARD_LVL2_RANGE = 180;
	public static final int WIZARD_LVL2_DMG = 60;
	public static final int WIZARD_LVL2_RATE = 75; //Rate, in game ticks
	
	public static final int WIZARD_LVL3_RANGE = 200;
	public static final int WIZARD_LVL3_DMG = 100;
	public static final int WIZARD_LVL3_RATE = 33; //Rate, in game ticks
	
	
	
	public Wizard(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		
		this.x = x;
		this.y = y;
		
		this.range = WIZARD_LVL1_RANGE;
		this.damage = WIZARD_LVL1_DMG;
		this.fireRate = WIZARD_LVL1_RATE;
		
		this.value = WIZARD_VALUE;
		this.type = WIZARD_BASIC_TYPE;
		this.sprite[0] = Board.spriteSheet.getSprite(0, 2, 40);
		this.sprite[1] = Board.spriteSheet.getSprite(1, 2, 40);
		this.sprite[2] = Board.spriteSheet.getSprite(2, 2, 40);
		this.sprite[3] = Board.spriteSheet.getSprite(3, 2, 40);
		
		//level 2 sprites
		this.sprite[4] = Board.spriteSheet.getSprite(4, 2, 40);
		this.sprite[5] = Board.spriteSheet.getSprite(5, 2, 40);
		this.sprite[6] = Board.spriteSheet.getSprite(6, 2, 40);
		this.sprite[7] = Board.spriteSheet.getSprite(7, 2, 40);
		
		//level 3 sprites
		this.sprite[8] = Board.spriteSheet.getSprite(8, 2, 40);
		this.sprite[9] = Board.spriteSheet.getSprite(9, 2, 40);
		this.sprite[10] = Board.spriteSheet.getSprite(10, 2, 40);
		this.sprite[11] = Board.spriteSheet.getSprite(11, 2, 40);
	}



	
	@Override
	public void render(Graphics2D g) {

		super.render(g);
		AffineTransform old = g.getTransform();
		rotate(g);
		if(type == TowerEnum.wizard_basic)
		if(basicCDTimer < 20 && basicCDTimer > 0) {
			
			g.drawImage(sprite[spriteCounter], x,y,size,size, null);
			if(basicCDTimer % 5 == 0) 
			spriteCounter = (spriteCounter + 1)%4;
			
		}
		else if(basicCDTimer == 0) {
			g.drawImage(sprite[0], x,y,size,size, null);
		}
		else {
			
			g.drawImage(sprite[0], x,y,size,size, null);
			
		}
		
		else if(type == TowerEnum.wizard_experienced) {
			
			if(basicCDTimer < 20 && basicCDTimer > 0) {
				g.drawImage(sprite[spriteCounter+4], x,y,size,size, null);
				if(basicCDTimer % 5 == 0) 
				spriteCounter = (spriteCounter + 1)%4;
			
			}
			else if(basicCDTimer == 0) {
				g.drawImage(sprite[7], x,y,size,size, null);
			}
			else {
				
				g.drawImage(sprite[4], x,y,size,size, null);			
			}			
		}
		else if(type == TowerEnum.wizard_master) {
			if(basicCDTimer < 20 && basicCDTimer > 0) {
				g.drawImage(sprite[spriteCounter+8], x,y,size,size, null);
				if(basicCDTimer % 5 == 0) 
				spriteCounter = (spriteCounter + 1)%4;
			
			}
			else if(basicCDTimer == 0) {
				g.drawImage(sprite[11], x,y,size,size, null);
			}
			else {
				
				g.drawImage(sprite[8], x,y,size,size, null);			
			}	
			
		}
		g.setTransform(old);
			
	}

	@Override
	public void tick() {
		
		if(basicCDTimer > 0) {
			basicCDTimer--;
		}
		
		for(Entity e : Board.getEntities()) {
			if(e.getBounds().intersects(getRangeBounds()) && basicCDTimer <= 0) {
				attack(e);
				break;
			}
		}
		
	}




	@Override
	public void attack(Entity e) { //TODO: FIREBOLT -> FIREBALL -> INFERNO PROJECTILE (AOE DMG)
		
		if(type == TowerEnum.wizard_basic) {
			
			System.out.println("Attacking: " + e.toString());
			rotateX=e.x; rotateY = e.y;
			Board.getProjectiles().add((new Firebolt(x, y, e.getX(), e.getY(), damage)));
			basicCDTimer = fireRate;
			
		}
		else {
			
			System.out.println("Attacking: " + e.toString());
			rotateX=e.x; rotateY = e.y;
			Board.getProjectiles().add((new Fireball(x, y, e.getX(), e.getY(), damage)));
			basicCDTimer = fireRate;
			
		}
		
	}




	@Override
	public boolean upgrade() {
		
		if(type == WIZARD_BASIC_TYPE) {		
			this.range = WIZARD_LVL2_RANGE;
			this.damage = WIZARD_LVL2_DMG;
			this.fireRate = WIZARD_LVL2_RATE; //Rate in game ticks
			type = WIZARD_EXPERIENCED_TYPE;
			return true;
		}
		else if(type == WIZARD_EXPERIENCED_TYPE) {
			this.range = WIZARD_LVL3_RANGE;
			this.damage = WIZARD_LVL3_DMG;
			this.fireRate = WIZARD_LVL3_RATE; //Rate in game ticks
			type = WIZARD_MASTER_TYPE;
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public int getUpgradeValue() {
		
		if(type == WIZARD_BASIC_TYPE) {		
			return COST_UPGRADE0;
		}
		else if(type == WIZARD_EXPERIENCED_TYPE){
			return COST_UPGRADE1;
		}
		else if(type == WIZARD_MASTER_TYPE){
			return -1;
		}
		else {
			return -1;
		}
		
	}
	
	
	
	
	

}
