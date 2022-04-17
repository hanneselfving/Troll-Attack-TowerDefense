package interfacecomponents;

import java.awt.Graphics2D;

import units.Catapult;
import units.Selectable;
import units.TowerEnum;

public class CatapultInfo extends SelectableInfoPanel {

	public CatapultInfo(Catapult catRef, TowerEnum type) {
		super(catRef, type);
		// TODO Auto-generated constructor stub
		this.infoText = "The wizard is an advanced tower unit. Upgrade Cost: " + catRef.getUpgradeValue();
	}
	
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		super.render(g);
	}

}
