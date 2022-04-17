package main;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	public static Frame gameFrame = new Frame();
	public static Panel gamePanel = new Panel();
	
	public Frame() {
		
		
		
		
	}

	
	public static void main(String[] args) {
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.add(gamePanel);
		gameFrame.pack();
		gameFrame.setVisible(true);
	
	}
	
	
	
	
}
