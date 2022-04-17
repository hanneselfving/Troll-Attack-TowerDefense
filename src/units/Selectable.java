package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;

public abstract class Selectable {
	
	protected boolean hovering = false;
	protected boolean selected = false;
	protected static int size = 40;
	public int x,y;
	public int rotateX, rotateY;
	
	
	public void render(Graphics2D g) {
		
		if(hovering) {
			g.setColor(Color.CYAN);
			g.drawRect(x-1, y-1, size + 2, size + 2);
		}
		
		
		if(selected) {
			g.setColor(Color.RED);
			g.drawRect(x-1, y-1, size + 2, size + 2);
		}
			
	}
	
	//Takes two coordinates and returns 
	public double angleRotateAtoB(int ax, int ay, int bx, int by) {
		double angle = Math.atan2(by - ay, bx - ax);	
		   if(angle < 0){
		        angle += Math.PI*2;
		    }

		angle+=Math.toRadians(90);
		return angle;
	}
	
	public int getCenterX() {
		return x+(size/2);
	}
	
	public int getCenterY() {
		return y+(size/2);
	}
	
	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		Selectable.size = size;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
		this.rotateX = getCenterX();
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
		this.rotateY = getCenterY();
	}
	
	
	

}
