package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

import interfacecomponents.ArcherInfo;
import interfacecomponents.BuildBtnEnums;
import interfacecomponents.BuildTowerButton;
import interfacecomponents.CatapultInfo;
import interfacecomponents.SelectableInfoPanel;
import interfacecomponents.WavePanel;
import interfacecomponents.WizardInfo;
import units.*;
import visuals.Logo;
import visuals.Map;

public class Panel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {


	Board board;
	public static Font f = null;
	private GameState gameState = GameState.intro;
	private Logo logo = new Logo("/logo.png");
	
	
	//TIMER CALLING ACTIONLISTENER WITH 20 MS DELAY
	
	final int DELAY = 20; //delay in ms
	private Timer timer = new Timer(DELAY, this);
	boolean siege = false;
	boolean applyBurn = false; //Apply boolean every second
	boolean doTrollAnimation = false;
	
	int frames = 0;
	WaveHandler wHandler = new WaveHandler(board);
	
	public Panel() {
		this.setPreferredSize(new Dimension(1280,720));
		
		  GraphicsEnvironment ge = null;
		    try{
		      ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/dpcomic.ttf")));
		    } catch(FontFormatException e){} catch (IOException e){
		    	
		    		e.printStackTrace();
		    }
		
		    
		for (Font fo : ge.getAllFonts()) {
			System.out.println(fo);
		}
		    
		f = new Font("DPComic", Font.TRUETYPE_FONT, 16);
		
		board =  Board.getBoard();
		
		Board.initMap();
		Board.initUI();
		Board.initStats();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setFont(f);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if(gameState == GameState.playing) {
		
			for(Block b : Board.getBlocks()) {
				b.render(g2);
			}
			
			for(Entity e : Board.getEntities()) {
				e.render(g2);
			}
			
			Tower tSelected = null; //Draw the selected tower last for a proper drawing order
			for(Tower t : Board.getTowers()) {
				//TODO: INDICATOR HERE
				if(t.isSelected()) {
					tSelected = t;
				}			
				else {
					t.render(g2);	
				}
			}
			if(tSelected != null)
			tSelected.render(g2);
			
			for(Projectile p : Board.getProjectiles()) {
				p.render(g2);
			}
			
			Board.getBuildUIBackground().render(g2);
			
			for(BuildTowerButton bu : Board.getBuildBtns()) {
				bu.render(g2);
			}
			
			
			g2.setColor(Color.WHITE);
			g2.drawImage(Board.spriteSheet.getSprite(1, 6, 40), 20, 15, 20,20, null);
			g2.drawImage(Board.spriteSheet.getSprite(2, 6, 40), 20, 45, 20,20, null);
			g2.drawString(": " + Board.getGold(), 50, 30);
			g2.drawString(": " + Board.getHp(), 50, 60);
			
			Board.getWavePanel().render(g2);
			
			if(board.getInfoPanel() != null)
			board.getInfoPanel().render(g2);
		
		
		}
		
		else if(gameState == GameState.intro) {
			g.drawImage(logo.img, 0, 0, 1280, 720, null);
			g.setColor(Color.WHITE);
			g.drawString("Click anywhere to start", 520, 400);
		}
		else if(gameState == GameState.end) {
			g.setColor(Color.WHITE);
			g.drawString("\tGame Over.\nClick anywhere to play again", 600, 40);
		}
		
		
	}
	
	void tick() {
		
		if(gameState == GameState.playing) {
			
			if(Board.getHp() <= 0) {
				gameState= GameState.end;
			}
			
			frames = (frames + 1)%10000;
			
			doTrollAnimation = false;
			
			if(Board.getWaveCD() <= 0 && Board.isEntityQueuesEmpty() && !siege) {
				spawnWave();
				siege = true;
			}
			
			if(Board.getWaveCD() <= 0 && Board.isEntityQueuesEmpty() && siege) {
				Board.setWaveCD(10);
				Board.incrementWave();
				siege = false;
			}
			
			registerTimers();
			
			for(Block b : Board.getBlocks()) {
				b.tick();
			}
			
			for(Tower t : Board.getTowers()) {
				t.tick();
				
			}
			if(!Board.getTowersQueue().isEmpty()) { //Builds the towers after the loop
				
				Board.getTowers().addAll(Board.getTowersQueue());
				Board.getSelectables().addAll(Board.getTowersQueue());
				Board.getTowersQueue().clear();
				
			}
			
			
			for (Iterator<Entity> i = Board.getEntities().iterator(); i.hasNext(); )  {
					Entity e = (Entity) i.next();
					e.tick();
					if(isApplyBurn()) {
						if(e.getBurnTime() > 0) {
							e.burn();
						}
					}
					
					if(doTrollAnimation) {
						e.incrementSpriteCounter();
					}
				
					if(e.getX() <= 40) { //IF entity has reached safe haven
						Board.setHp(Board.getHp() - e.getDamage());
						i.remove(); 
					}
						
					else if(e.getHealth() <= 0) {
						System.out.println(e.getValue());
						Board.addGold(e.getValue());
						i.remove();
					}
			}
			
			setApplyBurn(false);
	
			
			for (Iterator<Projectile> i = Board.getProjectiles().iterator(); i.hasNext(); )  {
				Projectile p = (Projectile) i.next();
				p.tick();
				if(p.isReached() || p.getX() > 4000 || p.getY() > 4000) {
					i.remove();
				}
					
		}
			
			for(BuildTowerButton bu : Board.getBuildBtns()) {
				bu.tick();
			}

	}
		else if(gameState == GameState.intro) {
			
		}
		else if(gameState == GameState.end) {

		}
		
}
	
	private void registerTimers() {
		
		if(frames%(2000/DELAY) == 0) { //If two seconds have passed
			if(!Board.getSlowSpawn().isEmpty()) {
				Board.getEntities().add(Board.getSlowSpawn().getFirst());
				Board.getSlowSpawn().removeFirst();
			}
		}
		
		if(frames%(1000/DELAY) == 0) { //If one second has passed
			
			if(Board.getWaveCD() > 0) {
				Board.decrementWaveCD();
			}
			
			if(!Board.getMedSpawn().isEmpty()) {
				Board.getEntities().add(Board.getMedSpawn().getFirst());
				Board.getMedSpawn().removeFirst();
			}
			
			setApplyBurn(true);
			
			//Board.addEntity(new Troll(Board.getSpawnPointX(),Board.getSpawnPointY()));
			
		}
		
		if(frames%(500/DELAY) == 0) { //if half a second has passed
			
			doTrollAnimation=true;
			
			if(!Board.getFastSpawn().isEmpty()) {
				Board.getEntities().add(Board.getFastSpawn().getFirst());
				Board.getFastSpawn().removeFirst();
			}
			
		}
	}

	private void spawnWave() {
		
		wHandler.spawnWave();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		repaint();
		tick();
			
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mousePressed(MouseEvent e) {
		
		clickAction(e.getX(), e.getY());
		
	}
	
	private void clickAction(int x, int y) {
		
		if(gameState == GameState.playing) {
			if(Board.infoPanel != null) {
				clickActionOnInfoPanel(x, y);
			}
			else {
				clickActionNoInfoPanel(x,y);
			}
		}
		else if(gameState == GameState.intro) {
			gameState = GameState.playing;
		}
		else if(gameState == GameState.end) {
			resetGame();
		}
		
	}
	//TODO: Clear queues, towers and units
	private void resetGame() {
		
		Board.setBoard(new Board());
		frames = 0;
		
		Board.initMap();
		Board.initUI();
		Board.initStats();
		
		Board.getTowersQueue().clear();
		Board.getTowers().clear();
		Board.getEntities().clear();
		
		
		gameState = GameState.intro;
		
	}

	private void clickActionOnInfoPanel(int x, int y) {
		int infoPX = Board.infoPanel.x;
		int infoPY = Board.infoPanel.y;
		int infoPW = Board.infoPanel.width;
		int infoPH = Board.infoPanel.height;
		
		if(x > infoPX && x < x + infoPW && y > infoPY && y < y + infoPH) { // musen är inom infoPanel
			
			Rectangle upgBtnArea = Board.infoPanel.getUpgradeBtnArea();
			if(upgBtnArea != null) {
				if(upgBtnArea.intersects(new Rectangle(x, y, 2, 2))) {
					upgradeAction(x, y);
				}
			}
			
		}
		else {
			Board.infoPanel = null;
			clickAction(x, y);
			return;
		}
		
		
	}
	
	private void clickActionNoInfoPanel(int x, int y) {
		
		if(Board.selected != null) {
			
			if(Board.getBuildBtns().contains(Board.selected)) { //IF a buildbtn is selected
				if(Board.isBuildAbleSpot(x, y)) {
				BuildTowerButton b = (BuildTowerButton) Board.selected;
				if(b.isSelected()) {
						if(b.getType() == BuildBtnEnums.archer) {
							if(Board.getGold() >= Archer.ARCHER_VALUE) {
								Board.addTower((new Archer(x,y)));
								Board.setGold(Board.getGold()-Archer.ARCHER_VALUE);
							}
						}
						else if (b.getType() == BuildBtnEnums.wizard) {
							if(Board.getGold() >= Wizard.WIZARD_VALUE) {
								Board.addTower((new Wizard(x,y)));
								Board.setGold(Board.getGold()-Wizard.WIZARD_VALUE);
							}
						}
						else if (b.getType() == BuildBtnEnums.catapult) {
							if(Board.getGold() >= Catapult.CATAPULT_VALUE) {
								Board.addTower((new Catapult(x,y)));
								Board.setGold(Board.getGold()-Catapult.CATAPULT_VALUE);
							}
						}
					}
				}
				deselect();
			}
			else {
				deselect();
				clickAction(x, y);
				return;
			}
			
		}
		else {
			for (Selectable s : Board.getSelectables()) {
				if(x > s.getX() && x < s.getX() + Selectable.getSize() 
				&& y > s.getY() && y < s.getY() + Selectable.getSize()) {
					select(s);
					break;
				}
			}
		}
		
	}

	
	private void upgradeAction(int x, int y) {
				
				Tower tRef = (Tower)Board.infoPanel.getSelectableRef();
				if(Board.getGold() >= tRef.getUpgradeValue()) {
					if(tRef.upgrade()) {
						Board.setGold(Board.getGold()-tRef.getUpgradeValue());
						Board.setInfoPanel(null);
					}
				}	
				
	}
	
	private void select(Selectable s) {
		s.setSelected(true);
		Board.selected = s;	
		for (Tower t : Board.getTowers()) {
			if(t.isSelected()) {
				switch(t.getType()) { 		
				case archer_basic: //TYPE 0 = ARCHER BASIC
					Board.setInfoPanel(new ArcherInfo((Archer)t, TowerEnum.archer_basic));	
					break;
				case archer_experienced:
					Board.setInfoPanel(new ArcherInfo((Archer)t, TowerEnum.archer_experienced));	
					break;
				case archer_master:
					Board.setInfoPanel(new ArcherInfo((Archer)t, TowerEnum.archer_master));	
					break;
				case wizard_basic:
					Board.setInfoPanel(new WizardInfo((Wizard)t, TowerEnum.wizard_basic));	
					break;
				case wizard_experienced:
					Board.setInfoPanel(new WizardInfo((Wizard)t, TowerEnum.wizard_experienced));	
					break;
				case wizard_master:
					Board.setInfoPanel(new WizardInfo((Wizard)t, TowerEnum.wizard_master));	
					break;
				case catapult_basic:
					Board.setInfoPanel(new CatapultInfo((Catapult)t, TowerEnum.catapult_basic));
					break;
				case catapult_experienced:
					Board.setInfoPanel(new CatapultInfo((Catapult)t, TowerEnum.catapult_experienced));
					break;
				case catapult_master:
					Board.setInfoPanel(new CatapultInfo((Catapult)t, TowerEnum.catapult_master));
					break;
				default:
					Board.setInfoPanel(null);
					break;
				}
				break;
			}
		}
		
	}
	
	private void deselect() {
			Board.selected.setSelected(false);
			Board.selected = null;
			Board.setInfoPanel(null);
		
	}
	

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		int xPos = e.getX();
		int yPos = e.getY();
	
		
		for (Selectable s : Board.getSelectables()) {
			if(xPos > s.getX() && xPos < s.getX() + Selectable.getSize() 
			&& yPos > s.getY() && yPos < s.getY() + Selectable.getSize()) {
				s.setHovering(true);
			}
			else
			{
				s.setHovering(false);
			}	
		}
		
		
		
	}

	public boolean isApplyBurn() {
		return applyBurn;
	}

	public void setApplyBurn(boolean applyBurn) {
		this.applyBurn = applyBurn;
	}
	
	
	
	
	
	
	
	
}
