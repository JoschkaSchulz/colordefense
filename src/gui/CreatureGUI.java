package gui;

import java.util.LinkedList;

import creature.Creature;
import processing.core.PApplet;
import world.Field;
import world.World;

public class CreatureGUI extends PApplet {

	private Creature c;
	private World world;
	
	private long pc;
	
	private LinkedList<Field> path1;
	
	public void setup() {
		size(400,400);
		background(255);
		frameRate(30);
	
		this.pc = 0;
		this.c = new Creature(200, 200);
		
		this.world = new World(5, 5);
		path1 = this.world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 2), new Field(Field.CONTAIN_EXIT, 4, 2));
	}
	
	public void keyPressed() {
		if(keyCode == 37){ //Links
			this.c.rotate(90);
		}else if(keyCode == 39){ //Rechts
			this.c.rotate(-90);
		}else if(keyCode == 38) { //Vorwärts
			this.c.move(10);
		}
	}
	
	private void drawWorld() {
		for (int y = 0; y < world.getWorld()[0].length; y++) {
			for (int x = 0; x < world.getWorld().length; x++) {
				if (world.getWorld()[x][y].getContain() == Field.CONTAIN_SPAWN) {
					fill(255, 0, 0);
				} else if (world.getWorld()[x][y].getContain() == Field.CONTAIN_EXIT) {
					fill(0, 255, 0);
				} else if (world.getWorld()[x][y].getContain() == Field.CONTAIN_CHECKPOINT) {
					fill(0, 0, 255);
				} else if (path1 != null && path1.contains(world.getWorld()[x][y])) {
					fill(255, 125, 0);
				} else if (world.getWorld()[x][y].getContain() == Field.CONTAIN_BLOCK) {
					fill(0, 0, 0);
				} else {
					fill(255, 255, 255);
				}
				rect(x * 50, y * 50, 50, 50);
			}
		}
	}
	
	private int getFieldMidX(int x) {
		return x*50+25;
	}
	private int getFieldMidY(int y) {
		return y*50+25;
	}
	
	public void draw() {
		background(255);
		this.pc++;
		
		//World in big
		drawWorld();
		
		//Creature
		stroke(0,0,0);
		rect(this.c.getX(), this.c.getY(), 20, 20);	//creature
		
		//this.c.move(1);
	}
}
