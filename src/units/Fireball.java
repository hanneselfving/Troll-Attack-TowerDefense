package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;
import main.Panel;

public class Fireball extends Projectile {
	
	int targetX, targetY;
	int burn = 5, burnLength = 5;
	
	public Fireball(int x, int y, int targetX, int targetY, int damage) {
		
		super(x,y, targetX, targetY, 6);
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.damage = damage;
		
		this.setSize(size*3);
		
		this.sprite = Board.spriteSheet.getSprite(4, 0, 40);
	
	}
	
	public Fireball(int x, int y) {
		
		super(x,y, 0, 0, 6);
		this.x = x;
		this.y = y;
		this.setSize(size*2);
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		rotateAndDraw(g);
		
	}

	@Override
	public void tick() {
		
		x+=velX;
		y+=velY;
		
		aoeHitCheck(burn, burnLength);
		
    }
	

}
