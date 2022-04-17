package interfacecomponents;

import java.awt.Graphics2D;

import main.Board;
import units.Archer;
import units.TowerEnum;

public class ArcherInfo extends SelectableInfoPanel{

	public ArcherInfo(Archer archerRef, TowerEnum type) {
		
		super(archerRef, type);	
		
		int upgVal = archerRef.getUpgradeValue();
		
		if(upgVal == -1) {
			this.infoText = "The archer is a basic tower unit. Upgrade Cost: MAX";
		}
		else {		
			this.infoText = "The archer is a basic tower unit. Upgrade Cost: " + upgVal;
		}
		
		
	}
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		super.render(g);
		
	}
	
	
}
