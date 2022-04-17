package main;

import java.util.Iterator;
import java.util.Random;

import units.Troll;

public class WaveHandler {
	
	
	Board board;
	Random rand = new Random();
	
	public WaveHandler(Board board) {
		
		this.board = board;
		
	}
	
	public void spawnWave() {
		
		int wave = Board.getWave();
		int trollsToSpawn = numberOfTrolls(wave);
		int fastTrolls = fastTrolls(trollsToSpawn, wave);
		int medTrolls = (trollsToSpawn - fastTrolls)/2;
		int slowTrolls = (trollsToSpawn - fastTrolls)/2;
		int hp = 50;
		int speedMult = 1;
			
		
		for(int i = 0; i < fastTrolls; i++) {
			if(wave < 10) {
				speedMult = rand.nextInt(wave) + 1;
			}
			else {
				speedMult = 10;
			}
			Board.getFastSpawn().add(new Troll(Board.getSpawnPointX() + rand.nextInt(50), Board.getSpawnPointY(),speedMult,hp));
		}

		for(int i = 0; i < medTrolls; i++) {
			if(wave < 5) {
				speedMult = rand.nextInt(wave) + 1;
			}
			else {
				speedMult = rand.nextInt(5) + 1;
			}
			Board.getMedSpawn().add(new Troll(Board.getSpawnPointX() + rand.nextInt(50),Board.getSpawnPointY(), speedMult, hp));
		}
		
		for(int i = 0; i < slowTrolls; i++) {
			if(wave < 5) {
				speedMult = rand.nextInt(wave) + 1;
			}
			else {
				speedMult = rand.nextInt(5) + 1;
			}
			Board.getSlowSpawn().add(new Troll(Board.getSpawnPointX() + rand.nextInt(50),Board.getSpawnPointY(),speedMult,hp));
		}
		
		
	}
	
	private int numberOfTrolls(int wave) {
		
		
		int result = (int) (Math.pow(1.10, wave) + 2*wave + 2);
		
		return result;
	}
	
	private int fastTrolls(int totTrolls, int wave) {
		double percentage = (1-Math.pow(5, -(wave/50)));
		int result = (int) (totTrolls*percentage);
		return result;
	}
	

}
