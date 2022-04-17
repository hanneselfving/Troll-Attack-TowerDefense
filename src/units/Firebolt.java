package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;
import main.Panel;

public class Firebolt extends Projectile {
	
	int targetX, targetY;
	int burn = 2, burnLength = 5;
	
	public Firebolt(int x, int y, int targetX, int targetY, int damage) {
		
		super(x,y, targetX, targetY, 6);
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.damage = damage;	
		this.sprite = Board.spriteSheet.getSprite(4, 0, 40);
	
	}
	
	public Firebolt(int x, int y) {
		
		super(x,y, 0, 0, 6);
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		rotateAndDraw(g);
		
		
	}

	@Override
	public void tick() {
		
		x+=velX;
		y+=velY;
		
		for(Entity e : Board.getEntities()) {
			if(e.getBounds().intersects(getBounds())) {
				e.setHealth(e.getHealth() - damage);
				if(e.getBurn() < burn) {
				e.setBurn(burn);
				}
				e.setBurnTime(e.getBurnTime()+burnLength);
				reached = true;
				break;
			}	
	}
	}
	

}
