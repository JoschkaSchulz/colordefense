package gui;

import core.DefenseCore;
import processing.core.PApplet;
import world.Field;
import world.World;

public class CoreGUI extends PApplet{
	
	private DefenseCore core;
	private String modus;
	
	public void setup() {

		core = new DefenseCore(20, 20);
		this.modus = "-";
		
		size(650, 425);
		background(255);
		frameRate(30);
	}
	
	private void drawWorld() {
		for (int y = 0; y < core.getWorld().getWorld()[0].length; y++) {
			for (int x = 0; x < core.getWorld().getWorld().length; x++) {
				if (core.getWorld().getWorld()[x][y].getContain() == Field.CONTAIN_SPAWN) {
					fill(255, 0, 0);
				} else if (core.getWorld().getWorld()[x][y].getContain() == Field.CONTAIN_EXIT) {
					fill(0, 255, 0);
				} else if (core.getWorld().getWorld()[x][y].getContain() == Field.CONTAIN_CHECKPOINT) {
					fill(0, 0, 255);
				} else if (core.getWorld().getWorld()[x][y].getContain() == Field.CONTAIN_BLOCK) {
					fill(0, 0, 0);
				} else {
					fill(255, 255, 255);
				}
				rect(x * 20, y * 20, 20, 20);
			}
		}
	}
	
	public void keyPressed() {
		switch(keyCode) {
			default:
				this.modus = keyCode+"";
				break;
			case 83:	//Spawn
				this.modus = "Spawn Placer";
				
				break;
			case 69:	//Exit
				this.modus = "Exit Placer";
				
				break;
			case 66:	//Block
				this.modus = "Block Placer";
				
				break;
		}
	}
	
	public void draw() {
		background(255);
		stroke(0);
		
		//Zeichne die Welt
		drawWorld();
		
		//Gebe die Hilfe aus
		textSize(14);
		fill(0, 0, 0);
		text("Hilfe", 410, 25);
		text(" | Modus: "+this.modus, 475, 25);
		
		textSize(10);
		text("Taste s - Spawn Placer", 410, 40);
		text("Taste e - Exit Placer", 410, 50);
		text("Taste b - Block Placer", 410, 60);
		text("Taste ? - ???", 410, 70);
	}
}
