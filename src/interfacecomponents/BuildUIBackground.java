package interfacecomponents;

import java.awt.Color;
import java.awt.Graphics2D;

import units.Archer;

public class BuildUIBackground {

	int x, y, width = 48, height = 180;
	
	public BuildUIBackground(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	
	public void render(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		g.setColor(Color.cyan);
		g.drawRect(x, y, width, height);

		
		
		
	}
	
	
}
