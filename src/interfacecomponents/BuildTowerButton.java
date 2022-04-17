package interfacecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import main.Board;
import units.Archer;
import units.Selectable;

public class BuildTowerButton extends Selectable {

	int goldReq = 0;
	BufferedImage sprite;
	BuildBtnEnums type;
	
	boolean available = false;
	
	public BuildTowerButton(int x, int y, int goldReq, BuildBtnEnums type) {
		
		this.x = x;
		this.y = y;
		this.goldReq = goldReq;
		this.type = type;
		if(this.type == BuildBtnEnums.archer)
			this.sprite = Board.spriteSheet.getSprite(0, 1, size);
		else if (this.type == BuildBtnEnums.wizard) {
			this.sprite = Board.spriteSheet.getSprite(0, 2, size);
		}
		else if(this.type == BuildBtnEnums.catapult) {
			this.sprite = Board.spriteSheet.getSprite(0, 3, size);
		}
		
	}
	
	public void render(Graphics2D g) {
		
		super.render(g);
		
		g.drawImage(sprite, x, y, size, size, null);
		
		g.drawString("" + goldReq, x - 30, y);
		
		g.drawImage(Board.spriteSheet.getSprite(1, 6, 40), x-40, y + 10, 20,20, null);

		
		
	}
	public void tick() {
		
		
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		BuildTowerButton.size = size;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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

	public int getGoldReq() {
		return goldReq;
	}

	public void setGoldReq(int goldReq) {
		this.goldReq = goldReq;
	}

	/**
	 * @return the type
	 */
	public BuildBtnEnums getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(BuildBtnEnums type) {
		this.type = type;
	}
	
	
	
}
