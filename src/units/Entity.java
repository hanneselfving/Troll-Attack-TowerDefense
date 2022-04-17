package units;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Board;

public abstract class Entity extends Selectable {
	//burntime in seconds, knockbacktime in ticks
	int health, maxHealth, damage, value, burn = 0, burnTime = 0, knockBackTime = 0;
	double velX, velY, knockX = 3, knockY = 0;
	BufferedImage[] sprite = new BufferedImage[3];
	int spriteCounter = 0;
	
	BufferedImage[] burnSprites = new BufferedImage[2];
	int bSpriteCounter = 0;
	
	public Entity(int x, int y) {
		
		super.x = x;
		super.y = y;
		this.x = x;
		this.y = y;
		
		burnSprites[0] = Board.spriteSheet.getSprite(3, 6, 40);
		burnSprites[1] = Board.spriteSheet.getSprite(4, 6, 40);
		
	}
	
	public void render(Graphics2D g) {
		super.render(g);
		
		if(burnTime > 0) {			
			//Burn animation here			
			g.drawImage(burnSprites[bSpriteCounter], x, y, size, size, null);
			bSpriteCounter = (bSpriteCounter + 1)%2;
	
		}
		
	}
	
	public void tick() {
		
		if(knockBackTime > 0) {
			velX = knockX;
			velY = knockY;
			knockX /= 8;
			knockY /= 8;
			knockBackTime--;
		}
		else if(knockX != 3) {
			knockX = 3;
		}
		
		
		x+=velX;
		y+=velY;
		
		
	}
	
	public abstract void attack();
	public abstract void incrementSpriteCounter();
	
	public void burn() {
		this.health -= this.burn;
		this.burnTime--;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,size,size);
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public double getVelX() {
		return velX;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public double getVelY() {
		return velY;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	public BufferedImage getSprite() {
		return sprite[0];
	}
	public void setSprite(BufferedImage sprite) {
		this.sprite[0] = sprite;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	public int getBurn() {
		return burn;
	}

	public void setBurn(int burn) {
		this.burn = burn;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}


	
}
