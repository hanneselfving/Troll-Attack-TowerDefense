package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;
import main.Panel;

public class Arrow extends Projectile {
	
	int targetX, targetY;
	int piercePower = 1;
	
	public Arrow(int x, int y, int targetX, int targetY, int damage, int piercePower) {
		
		
		
		super(x,y, targetX, targetY, 6);
		
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.damage = damage;
		this.piercePower = piercePower;
		this.sprite = Board.spriteSheet.getSprite(3, 0, 40);

		
		
	}
	
	public Arrow(int x, int y) {
		
		super(x,y, 0, 0, 6);
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		rotateAndDraw(g, 90);

	}

	@Override
	public void tick() {
		
		x+=velX;
		y+=velY;
		
		this.piercePower = singleHitCheck(piercePower);

	}
	

}
