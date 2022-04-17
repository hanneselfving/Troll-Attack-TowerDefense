package interfacecomponents;

import java.awt.Graphics2D;

import units.Archer;
import units.TowerEnum;
import units.Wizard;

public class WizardInfo extends SelectableInfoPanel{

	public WizardInfo(Wizard wizRef, TowerEnum type) {
		
		super(wizRef, type);	
		this.infoText = "The wizard is an advanced tower unit. Upgrade Cost: " + wizRef.getUpgradeValue();
		
	}
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		super.render(g);
	}
	
	
}
