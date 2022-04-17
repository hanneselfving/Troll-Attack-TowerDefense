package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;
import main.Panel;

public class Troll extends Entity {

	final static double VEL = 0.8;
	
	final static double DIR_WEST_VELX = -VEL;
	final static double DIR_EAST_VELX = VEL;
	final static double DIR_NORTH_VELY = -VEL;
	final static double DIR_SOUTH_VELY = VEL;

	public Troll(int x, int y, double speedMult, int hp) {

		super(x, y);
		
		this.x = x;
		this.y = y;
		
		this.maxHealth = hp;
		this.health = maxHealth;
		this.damage = 10;
		this.value = 20;
		
		this.sprite[0] = Board.spriteSheet.getSprite(0, 4, size);
		this.sprite[1] = Board.spriteSheet.getSprite(1, 4, size);
		this.sprite[2] = Board.spriteSheet.getSprite(2, 4, size);
		
		velX = DIR_WEST_VELX*speedMult;
		

		
	}
	
	@Override
	public void render(Graphics2D g) {
		
	
		
		if(getHealth() > 0) {
			
			
			g.drawImage(sprite[spriteCounter%3], x, y, size,size, null);
			
			//HP Bar
			g.setColor(Color.RED);
			g.fillRect(x, y - 10, size, 5);
			g.setColor(Color.GREEN);
			double hpRatio = ((double)health/maxHealth);
			g.fillRect(x, y - 10, (int) (hpRatio * size), 5);
			
			super.render(g);
		}
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
		super.tick();
			
	}

	@Override
	public void attack() {
		Board.setHp(Board.getHp() - damage);
	}

	@Override
	public void incrementSpriteCounter() {		
		spriteCounter++;	
		
	}
	
	

}
