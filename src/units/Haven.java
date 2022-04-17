package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;

public class Haven extends Block {

	public Haven(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.sprite = Board.spriteSheet.getSprite(0,6, size);
		
	}
	
	
	@Override
	public void render(Graphics2D g) {

		g.drawImage(sprite, x, y, size, size, null);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
