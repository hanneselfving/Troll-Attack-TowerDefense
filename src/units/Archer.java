package units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.Board;
import main.Panel;

public class Archer extends Tower {
	
	public static final int ARCHER_LVL1_RANGE = 120;
	public static final int ARCHER_LVL1_DMG = 10;
	public static final int ARCHER_LVL1_RATE = 120; //Rate, in game ticks
	public static final int ARCHER_VALUE = 25;
	public static final int COST_UPGRADE0 = 20;
	public static final int COST_UPGRADE1 = 50;
	
	public static final int ARCHER_LVL2_RANGE = 120;
	public static final int ARCHER_LVL2_DMG = 15;
	public static final int ARCHER_LVL2_RATE = 100; //Rate, in game ticks
	
	public static final int ARCHER_LVL3_RANGE = 160;
	public static final int ARCHER_LVL3_DMG = 20;
	public static final int ARCHER_LVL3_RATE = 75; //Rate, in game ticks
	
	public static final TowerEnum ARCHER_BASIC_TYPE = TowerEnum.archer_basic;
	public static final TowerEnum ARCHER_EXPERIENCED_TYPE = TowerEnum.archer_experienced;
	public static final TowerEnum ARCHER_MASTER_TYPE = TowerEnum.archer_master;
	
	
	
	
	
	//int basicCDTimer = 0; //IF 0 then basic attack enemy unit in range
	
		public Archer(int x, int y) {
		
		super(x, y);
			
		this.x = x;
		this.y = y;
		
		this.range = ARCHER_LVL1_RANGE;
		this.damage = ARCHER_LVL1_DMG;
		this.fireRate = ARCHER_LVL1_RATE; //Rate in game ticks
		
		this.value = ARCHER_VALUE;
		this.type = ARCHER_BASIC_TYPE;
		
		//level 1 sprites
		this.sprite[0] = Board.spriteSheet.getSprite(0, 1, 40);
		this.sprite[1] = Board.spriteSheet.getSprite(1, 1, 40);
		this.sprite[2] = Board.spriteSheet.getSprite(2, 1, 40);
		this.sprite[3] = Board.spriteSheet.getSprite(3, 1, 40);
		
		//level 2 sprites
		this.sprite[4] = Board.spriteSheet.getSprite(4, 1, 40);
		this.sprite[5] = Board.spriteSheet.getSprite(5, 1, 40);
		this.sprite[6] = Board.spriteSheet.getSprite(6, 1, 40);
		this.sprite[7] = Board.spriteSheet.getSprite(7, 1, 40);
		
		//level 3 sprites
		this.sprite[8] = Board.spriteSheet.getSprite(8, 1, 40);
		this.sprite[9] = Board.spriteSheet.getSprite(9, 1, 40);
		this.sprite[10] = Board.spriteSheet.getSprite(10, 1, 40);
		this.sprite[11] = Board.spriteSheet.getSprite(11, 1, 40);
		
	}
	
	@Override
	public void render(Graphics2D g) {

		super.render(g);
		AffineTransform old = g.getTransform();
		rotate(g);
		if(this.type == TowerEnum.archer_basic) {
			if(basicCDTimer < 20 && basicCDTimer > 0) {
				
				g.drawImage(sprite[spriteCounter], x,y,size,size, null);
				if(basicCDTimer % 5 == 0) 
				spriteCounter = (spriteCounter + 1)%4;
				
			}
			else if(basicCDTimer == 0) {
				g.drawImage(sprite[3], x,y,size,size, null);
			}
			else {
				
				g.drawImage(sprite[0], x,y,size,size, null);			
			}
		}
		
			if(this.type == TowerEnum.archer_experienced) {
				
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
			if(this.type == TowerEnum.archer_master) {
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
	public void attack(Entity e) {
		
		System.out.println("Attacking: " + e.toString());
		rotateX=e.x; rotateY = e.y;
		if(type == TowerEnum.archer_basic) {
			Board.getProjectiles().add((new Arrow(x, y, e.getX(), e.getY(), damage, 1)));
		}
		else if (type == TowerEnum.archer_experienced) {
			Board.getProjectiles().add((new Arrow(x, y, e.getX(), e.getY(), damage, 3)));
		}
		else if (type == TowerEnum.archer_master) {
			Board.getProjectiles().add((new Arrow(x, y, e.getX(), e.getY(), damage, 5)));
		}
		basicCDTimer = fireRate;
		
	}

	@Override
	public boolean upgrade() {
		
		if(type == ARCHER_BASIC_TYPE) {		
			this.range = ARCHER_LVL2_RANGE;
			this.damage = ARCHER_LVL2_DMG;
			this.fireRate = ARCHER_LVL2_RATE; //Rate in game ticks
			type = ARCHER_EXPERIENCED_TYPE;
			return true;
		}
		else if(type == ARCHER_EXPERIENCED_TYPE) {
			this.range = ARCHER_LVL3_RANGE;
			this.damage = ARCHER_LVL3_DMG;
			this.fireRate = ARCHER_LVL3_RATE; //Rate in game ticks
			type = ARCHER_MASTER_TYPE;
			return true;
		}
		
		return false;
		
	}

	@Override
	public int getUpgradeValue() {
		
		if(type == ARCHER_BASIC_TYPE) {		
			return COST_UPGRADE0;
		}
		else if(type == ARCHER_EXPERIENCED_TYPE) {
			return COST_UPGRADE1;
		}
		else {
			return -1;
		}
		
	}

}
