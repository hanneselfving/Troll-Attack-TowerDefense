package visuals;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	BufferedImage img;
	
	public Spritesheet(String path) {
		
		try {
			img = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getSprite(int xIndex, int yIndex, int size) {
		
		return img.getSubimage(size*xIndex, size*yIndex, size, size);
		
	}
	

}
