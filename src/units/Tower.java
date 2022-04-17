package units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Tower extends Selectable {
	
	int range, damage, fireRate, value, basicCDTimer = 0;
	TowerEnum type;
	BufferedImage sprite[] = new BufferedImage[15];
	int spriteCounter = 1;
	
	public Tower(int x, int y) {
		
		super.x = x;
		super.y = y;
		this.x = x;
		this.y = y;
		
		this.rotateX = getCenterX();
		this.rotateY = getCenterY();
		
	}
	
	public void render(Graphics2D g) {
		super.render(g);

		if(isSelected()) {	
			g.setColor(new Color(0.75f,0.75f,0.75f,0.5f));
			g.fill(getRangeBounds());
		}
		
	}
	
	public void rotate(Graphics2D g) {

		double angle = angleRotateAtoB(getCenterX(), getCenterY(), rotateX, rotateY);
		g.rotate(angle, getCenterX(), getCenterY());
		
	}
	
	
	public abstract void tick();
	public abstract void attack(Entity e);
	public abstract boolean upgrade(); //returns true if tower was upgraded
	public abstract int getUpgradeValue();
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,size,size);
	}
	
	public Rectangle getRangeBounds() {
		return new Rectangle(x - range,y - range,range*2 + size,range*2 + size);
	}

	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getFireRate() {
		return fireRate;
	}
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	public static int getSize() {
		return size;
	}
	public static void setSize(int size) {
		Tower.size = size;
	}
	public BufferedImage getSprite() {
		return sprite[0];
	}
	public void setSprite(BufferedImage sprite) {
		this.sprite[0] = sprite;
	}


	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}


	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		
		this.selected = selected;
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

	/**
	 * @return the type
	 */
	public TowerEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TowerEnum type) {
		this.type = type;
	}

	
}