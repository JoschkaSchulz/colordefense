package gui;

import java.util.Iterator;
import java.util.LinkedList;

import creature.Creature;
import creature.Enemy;
import creature.SpeedCreature;

import processing.core.PApplet;
import tower.NormalTower;
import tower.Tower;
import world.Field;
import world.World;

public class CreatureWorldCombinedGUI extends PApplet {

	private World world;
	private LinkedList<Field> path1;
	private LinkedList<Field> path2;
	private LinkedList<Field> path3;
	
	private LinkedList<Enemy> enemys;
	private LinkedList<Tower> towers;

	public void setup() {
		int sizeX = 50;
		int sizeY = 50;

		enemys = new LinkedList<>();
		towers = new LinkedList<>();
		
		world = new World(sizeX, sizeY);
		world.setField(0, 5, Field.CONTAIN_SPAWN);
		world.setField(0, 20, Field.CONTAIN_EXIT);
		
		world.setField(20, 5, Field.CONTAIN_CHECKPOINT);
		world.setField(20, 20, Field.CONTAIN_CHECKPOINT);

		path1 = world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 5), new Field(Field.CONTAIN_EXIT, 20, 5));
		path2 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 5), new Field(Field.CONTAIN_EXIT, 20, 20));
		path3 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 20), new Field(Field.CONTAIN_EXIT, 0, 20));

		size(sizeX * 20 + 1, sizeY * 20 + 1);
		background(255);
		frameRate(30);
	}
	
	private void addCreature() {
		SpeedCreature c = new SpeedCreature(0, 5, (LinkedList<Field>) path1.clone(), 0);
		c.addPath((LinkedList<Field>) path2.clone()); c.addPath((LinkedList<Field>) path3.clone());
		enemys.add(c);
	}
	
	private void addSlowCreature() {
		Creature c = new Creature(0, 5, (LinkedList<Field>) path1.clone(), 0);
		c.addPath((LinkedList<Field>) path2.clone()); c.addPath((LinkedList<Field>) path3.clone());
		enemys.add(c);
	}
	
	public void setFieldByXY(int x, int y, int zielFeld) {
		x /= 20;
		y /= 20;

		if((x==20 && y==5) || (x==20 && y==20) || (x==0 && y==5) || (x==0 && y==20)) return;
		
		if (world.getField(x, y).getContain() != zielFeld) {
			world.setField(x, y, zielFeld);
			
			path1 = world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 5), new Field(Field.CONTAIN_EXIT, 20, 5));
			path2 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 5), new Field(Field.CONTAIN_EXIT, 20, 20));
			path3 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 20), new Field(Field.CONTAIN_EXIT, 0, 20));
		
			if(path1 == null || path2 == null || path3 == null){
				world.setField(x, y, Field.CONTAIN_NOTHING);
				
				path1 = world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 5), new Field(Field.CONTAIN_EXIT, 20, 5));
				path2 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 5), new Field(Field.CONTAIN_EXIT, 20, 20));
				path3 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 20), new Field(Field.CONTAIN_EXIT, 0, 20));
			}
		}
	}

	int zielFeld = 0;
	
	@Override
	public void mousePressed() {
		int x = mouseX/20;
		int y = mouseY/20;
		
		if((x==20 && y==5) || (x==20 && y==20) || (x==0 && y==5) || (x==0 && y==20)) return;
		
		zielFeld = world.getField(x, y).getContain();
		switch (zielFeld) {
		case Field.CONTAIN_NOTHING:
			zielFeld = Field.CONTAIN_BLOCK;
			break;
		case Field.CONTAIN_BLOCK:
			zielFeld = Field.CONTAIN_NOTHING;
			break;
		}

		setFieldByXY(mouseX, mouseY, zielFeld);
	}
	
	public void keyPressed() {
		System.out.println("Keycode: "+keyCode);
		if(keyCode == 32) {
			this.addCreature();
			System.out.println("Whitespace - Spawn normal Creature");
		}else if(keyCode == 10) {
			System.out.println("Creatures: "+this.enemys.size());
			System.out.println("Return - Show active Enemys");
		}else if(keyCode == 84) {
			int x = mouseX/20;
			int y = mouseY/20;
			
			towers.add(new NormalTower(x, y));
			System.out.println("Key T - Set Tower at mouse pos");
		}
	}
	
	@Override
	public void mouseDragged() {
		//System.out.println(mouseX);
		//if (mousePressed)
			setFieldByXY(mouseX, mouseY, zielFeld);
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
				} else if (path2 != null && path2.contains(world.getWorld()[x][y])) {
					fill(200, 75, 0);
				} else if (path3 != null && path3.contains(world.getWorld()[x][y])) {
					fill(150, 25, 0);
				} else if (world.getWorld()[x][y].getContain() == Field.CONTAIN_BLOCK) {
					fill(0, 0, 0);
				} else {
					fill(255, 255, 255);
				}
				rect(x * 20, y * 20, 20, 20);
			}
		}
	}
	int pc = 0;
	public void draw() {
		background(255);
		stroke(0);
		drawWorld();
		pc++;
		
		if(pc%20 == 0) this.addSlowCreature();
		
		//Handle Creatures
		Iterator<Enemy> i2 = enemys.iterator();
		while(i2.hasNext()) {
			Enemy e = i2.next();
			
			e.move();
			
			if(!e.reachedTarget()) rect(e.getAbsX()-5, e.getAbsY()-5, 10, 10);
			else i2.remove();
		}
		
		//Handle Towers
		Iterator<Tower> i1 = towers.iterator();
		while(i1.hasNext()) {
			Tower t = i1.next();
			
			t.checkRadius(enemys);
//			fill(200,0,0,30f);
//			ellipse(t.getAbsX(), t.getAbsY(), t.getRadius()*2, t.getRadius()*2);
			fill(0,0,0);
			ellipse(t.getAbsX(), t.getAbsY(), 10, 10);
		}
	}
}
