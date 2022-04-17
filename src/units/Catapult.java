package units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.Board;

public class Catapult extends Tower {

	public static final int CATAPULT_LVL1_RANGE = 240;
	public static final int CATAPULT_LVL1_DMG = 50;
	public static final int CATAPULT_LVL1_RATE = 140; //Rate, in game ticks
	public static final int CATAPULT_VALUE = 120;
	public static final int COST_UPGRADE0 = 140;
	public static final int COST_UPGRADE1 = 280;
	
	public static final TowerEnum CATAPULT_BASIC_TYPE = TowerEnum.catapult_basic;
	public static final TowerEnum CATAPULT_EXPERIENCED_TYPE = TowerEnum.catapult_experienced;
	public static final TowerEnum CATAPULT_MASTER_TYPE = TowerEnum.catapult_master;
	
	public static final int CATAPULT_LVL2_RANGE = 280;
	public static final int CATAPULT_LVL2_DMG = 70;
	public static final int CATAPULT_LVL2_RATE = 130; //Rate, in game ticks
	
	public static final int CATAPULT_LVL3_RANGE = 320;
	public static final int CATAPULT_LVL3_DMG = 90;
	public static final int CATAPULT_LVL3_RATE = 110; //Rate, in game ticks
	
	public Catapult(int x, int y) {
		super(x, y);
		
		this.x = x;
		this.y = y;
		
		this.range = CATAPULT_LVL1_RANGE;
		this.damage = CATAPULT_LVL1_DMG;
		this.fireRate = CATAPULT_LVL1_RATE;
		
		this.value = CATAPULT_VALUE;
		this.type = TowerEnum.catapult_basic;
		
		this.sprite[0] = Board.spriteSheet.getSprite(0, 3, 40);
		this.sprite[1] = Board.spriteSheet.getSprite(1, 3, 40);
		this.sprite[2] = Board.spriteSheet.getSprite(2, 3, 40);
		this.sprite[3] = Board.spriteSheet.getSprite(3, 3, 40);
	}

	
	@Override
	public void render(Graphics2D g) {

		super.render(g);
		AffineTransform old = g.getTransform();
		rotate(g);
		if(basicCDTimer < 20 && basicCDTimer > 0) {
			
			g.drawImage(sprite[spriteCounter], x,y,size,size, null);
			if(basicCDTimer % 5 == 0) 
			spriteCounter = (spriteCounter + 1)%4;
			
		}
		else if(basicCDTimer == 0) {
			g.drawImage(sprite[0], x,y,size,size, null);
		}
		else {
			
			g.drawImage(sprite[1], x,y,size,size, null);
			
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
		Board.getProjectiles().add((new Rock(x, y, e.getX(), e.getY(), damage)));
		basicCDTimer = fireRate;
		
	}


	@Override
	public boolean upgrade() {

//		if(type == CATAPULT_BASIC_TYPE) {		
//			this.range = CATAPULT_LVL2_RANGE;
//			this.damage = CATAPULT_LVL2_DMG;
//			this.fireRate = CATAPULT_LVL2_RATE; //Rate in game ticks
//			type = CATAPULT_EXPERIENCED_TYPE;
//			return true;
//		}
//		else if(type == CATAPULT_EXPERIENCED_TYPE) {
//			this.range = CATAPULT_LVL3_RANGE;
//			this.damage = CATAPULT_LVL3_DMG;
//			this.fireRate = CATAPULT_LVL3_RATE; //Rate in game ticks
//			type = CATAPULT_MASTER_TYPE;
//			return true;
//		}
		
		return false;
		
	}
	
	@Override
	public int getUpgradeValue() {
		
		return -1;
		
	}

}
