package interfacecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import units.Entity;
import units.Selectable;
import units.Tower;
import units.TowerEnum;

public abstract class SelectableInfoPanel {

	public String infoText = "Default Text";
	public static int width = 1280, height = 200, x = 0,y = 720-height;
	static BufferedImage background, icon;
	public String upgradeText = "Upgrade";
	public TowerEnum type;
	
	private Selectable sRef;
	
	public Selectable getSelectableRef() {
		return sRef;
	}
	
	public Tower getTowerRef() {
		return (Tower) sRef;
	}

	Rectangle upRect = getUpgradeBtnArea();
	
	public SelectableInfoPanel(Selectable s, TowerEnum type) {
		
		this.sRef = s;
		this.type = type;
		
	}

	public void render(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.drawString(infoText, x + 20, y + 20);
		g.drawString("Damage: " + getTowerRef().getDamage() + "\nRate: " + getTowerRef().getFireRate() + " ticks per attack", x + 20, y + 40);
		g.drawString(upgradeText, upRect.x + 20, upRect.y + 20);
		g.setColor(Color.CYAN);
		g.draw(upRect);
		
	}
	
	public Rectangle getUpgradeBtnArea() {	
		return new Rectangle(1000, y, 280, height);	
	}
	
	
	
	
	
}
