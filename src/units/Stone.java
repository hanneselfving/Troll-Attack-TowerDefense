package units;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Board;

public class Stone extends Block {
	
	//0 = regular, 1 = upper, 2 = lower
	int type = 0;
	
	public Stone(int x, int y, int type) {
		
		this.x = x;
		this.y = y;
		this.type = type;
		
		if(type == 0) {
			this.sprite = Board.spriteSheet.getSprite(0, 0, size);
		}
		else if(type == 1) {
			this.sprite = Board.spriteSheet.getSprite(1, 0, size);
		}
		else if(type == 2) {
			this.sprite = Board.spriteSheet.getSprite(2, 0, size);
		}
		
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
