package units;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import main.Board;

public abstract class Projectile {

	int x,y, velX, velY, damage, enemiesHit = 0;
	public int size = 20;
	boolean reached = false;
	BufferedImage sprite;
	public int rotateX, rotateY;
	
	LinkedList<Entity> exclusionList = new LinkedList<Entity>();

	public Projectile(int x, int y, int targetX, int targetY, int slowness) {
		
		this.x = x;
		this.y = y;
		
		int diffX = targetX - x;
		int diffY = targetY - y;
		
		velX = diffX / (slowness + 1);
		velY = diffY / (slowness + 1);
		
		rotateX = x+velX*100;
		rotateY = y+velY*100;
			
	}
	
	public abstract void render(Graphics2D g);
	public abstract void tick();
	
	public void rotateAndDraw(Graphics2D g) {
		
		AffineTransform old = g.getTransform();

		double angle = angleRotateAtoB(getCenterX(), getCenterY(), rotateX, rotateY);
		g.rotate(angle, getCenterX(), getCenterY());
		
		g.drawImage(sprite, x,y,size,size, null);
		
		g.setTransform(old);
		
	}
	
	public void rotateAndDraw(Graphics2D g, double offsetDegrees) {
		
		AffineTransform old = g.getTransform();

		double angle = angleRotateAtoB(getCenterX(), getCenterY(), rotateX, rotateY, offsetDegrees);
		g.rotate(angle, getCenterX(), getCenterY());
		
		g.drawImage(sprite, x,y,size,size, null);
		
		g.setTransform(old);
		
	}
	
	//Takes two coordinates and returns 
	private double angleRotateAtoB(int ax, int ay, int bx, int by) {
		double angle = Math.atan2(by - ay, bx - ax);	
		   if(angle < 0){
		        angle += Math.PI*2;
		    }
		return angle;
	}
	
	//Takes two coordinates and returns 
	private double angleRotateAtoB(int ax, int ay, int bx, int by, double offsetDegrees) {
		double angle = Math.atan2(by - ay, bx - ax);	
		   if(angle < 0){
		        angle += Math.PI*2;
		    }

		angle+=Math.toRadians(offsetDegrees);
		return angle;
	}
	
	void singleHitCheck() {
		
		for(Entity e : Board.getEntities()) {
			
			if(e.getBounds().intersects(getBounds())) {
				
				e.setHealth(e.getHealth() - damage);
				reached = true;
				break;
				
			}	
			
		}
		
	}
	
	int singleHitCheck(int maxPierce) {
		
		for(Entity e : Board.getEntities()) {
			
			if(e.getBounds().intersects(getBounds()) && !exclusionList.contains(e)) {
				e.setHealth(e.getHealth() - damage);
				exclusionList.add(e);
				maxPierce--;
				if(maxPierce <= 0) {
					reached = true;
					break;
				}
			}	
			
		}
		
		return maxPierce;
			
	}
	
	void singleHitCheck(int burn, int burnLength) {
		
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
	
	void aoeHitCheck() {
		
		for(Entity e : Board.getEntities()) {
			
			if(e.getBounds().intersects(getBounds())) {
				
				e.setHealth(e.getHealth() - damage);
				aoeDMG(e.getCenterX(), e.getCenterY(), 120, e.damage);
				reached = true;
				break;
				
			}	
			
		}
		
	}
	
	void aoeHitCheck(int burn, int burnLength) {
		
		for(Entity e : Board.getEntities()) {
			if(e.getBounds().intersects(getBounds())) {
				e.setHealth(e.getHealth() - damage);
				aoeDMG(e.getCenterX(), e.getCenterY(), 120, e.damage, burn, burnLength);
				reached = true;
				break;
			}	
		}
		
	}
	
	
	private void aoeDMG(int x, int y, int range, int dmg) {
		
		Rectangle hitBox = new Rectangle(x-range,y-range, range*2, range*2);
		
		for (Entity e : Board.getEntities()) {
			
			if(e.getBounds().intersects(hitBox)) {
				
				e.setHealth(e.getHealth()-dmg);
				
			}
			
		}

	}
	
	private void aoeDMG(int x, int y, int range, int dmg, int burn, int burnLength) {
		
		Rectangle hitBox = new Rectangle(x-range,y-range, range*2, range*2);
		
		for (Entity e : Board.getEntities()) {
			
			if(e.getBounds().intersects(hitBox)) {
				
				e.setHealth(e.getHealth()-dmg);
				
				if(e.getBurn() < burn) {
					e.setBurn(burn);
				}
				e.setBurnTime(e.getBurnTime()+burnLength);
				
				
			}
			
		}

	}
	
	
	public int getCenterX() {
		return x+(size/2);
	}
	
	public int getCenterY() {
		return y+(size/2);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getVelX() {
		return velX;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVelY() {
		return velY;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public BufferedImage getSprite() {
		return sprite;
	}
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,size,size);
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public boolean isReached() {
		return reached;
	}
	public void setReached(boolean reached) {
		this.reached = reached;
	}
	
	
}
