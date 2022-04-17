package interfacecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Board;

public class WavePanel {
	
	static final int WIDTH = 200, HEIGHT = 125, X = 1280-WIDTH, Y = 720 - HEIGHT;
	
	public void render(Graphics2D g) {
		
		g.setFont(g.getFont().deriveFont(40.0f));
		
		g.setColor(Color.BLACK);
		g.fillRect(X, Y, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		if(Board.getWaveCD() > 0) {
		
		g.drawString("" + Board.getWaveCD(), X + 90, Y + 60);
		}
			
		g.setFont(g.getFont().deriveFont(16.0f));
		
		g.drawString("Wave: " + Board.getWave(), X + 5, Y + 5);
		
	}
	
	public void tick() {
		
		
		
		
	}
	
	public static Rectangle getBounds() {
		
		return new Rectangle(X,Y,WIDTH,HEIGHT);
		
	}
	
	

}
