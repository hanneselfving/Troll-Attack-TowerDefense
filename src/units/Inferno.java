package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;
import main.Panel;

public class Inferno extends Projectile {
	
	int targetX, targetY;
	int burn = 5, burnLength = 5;
	int reachedNum = 0;
	
	public Inferno(int x, int y, int targetX, int targetY, int damage) {
		
		super(x,y, targetX, targetY, 6);
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.damage = damage;
		
		this.setSize(size*2);
	
	}
	
	public Inferno(int x, int y) {
		
		super(x,y, 0, 0, 6);
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		g.setColor(Color.RED);
		g.fillRect(x, y, size, size);
		
		
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
					e.knockBackTime = 150;
					e.setBurnTime(e.getBurnTime()+burnLength);
					reachedNum++;
					if(reachedNum > 4) {
						reached = true;
					}
					break;
			}	
	}
	}
	

}
