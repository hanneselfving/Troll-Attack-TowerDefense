package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;
import main.Panel;

public class Rock extends Projectile {
	
	int targetX, targetY;
	
	public Rock(int x, int y, int targetX, int targetY, int damage) {
		
		super(x,y,targetX,targetY,6);
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.damage = damage;
		
		this.sprite = Board.spriteSheet.getSprite(5,0, 40);
		
		this.setSize(size*2);
	
	}
	
	public Rock(int x, int y) {
		
		super(x,y,0,0,6);
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		g.drawImage(sprite, x, y, size, size, null);
		
		
	}

	@Override
	public void tick() {
		
		x+=velX;
		y+=velY;
		
		aoeHitCheck();
		
	}
	

}
