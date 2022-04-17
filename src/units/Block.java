package units;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Block {
	
	int x,y;
	public static int size = 40;
	BufferedImage sprite;
	
	public abstract void render(Graphics2D g);
	public abstract void tick();

}
