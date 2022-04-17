package visuals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	public BufferedImage img;
	
	public Map(String path) {
		try {
			this.img = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPixelRGB(int x, int y) {
		
		return img.getRGB(x, y);
		
	}
	
	
	

}
