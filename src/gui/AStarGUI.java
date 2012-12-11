package gui;

import java.util.LinkedList;

import processing.core.PApplet;
import world.Field;
import world.World;

public class AStarGUI extends PApplet {

	private World world;
	private LinkedList<Field> path1;
	private LinkedList<Field> path2;
	private LinkedList<Field> path3;

	public void setup() {
		int sizeX = 25;
		int sizeY = 25;

		world = new World(sizeX, sizeY);
//		world.setField(0, 5, Field.CONTAIN_SPAWN);
//		world.setField(0, 20, Field.CONTAIN_EXIT);
		
		world.setField(20, 5, Field.CONTAIN_CHECKPOINT);
		world.setField(20, 20, Field.CONTAIN_CHECKPOINT);

		path1 = world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 5), new Field(Field.CONTAIN_EXIT, 20, 5));
		path2 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 5), new Field(Field.CONTAIN_EXIT, 20, 20));
		path3 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 20), new Field(Field.CONTAIN_EXIT, 0, 20));

		size(sizeX * 15 + 1, sizeY * 15 + 1);
		background(255);
		frameRate(30);
	}
	
	public void setFieldByXY(int x, int y, int zielFeld) {
		x /= 15;
		y /= 15;

		if((x==20 && y==5) || (x==20 && y==20) || (x==0 && y==5) || (x==0 && y==20)) return;
		
		if (world.getField(x, y).getContain() != zielFeld) {
			world.setField(x, y, zielFeld);
			
			path1 = world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 5), new Field(Field.CONTAIN_EXIT, 20, 5));
			path2 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 5), new Field(Field.CONTAIN_EXIT, 20, 20));
			path3 = world.aStar(new Field(Field.CONTAIN_SPAWN, 20, 20), new Field(Field.CONTAIN_EXIT, 0, 20));
		}
	}

	int zielFeld = 0;
	
	@Override
	public void mousePressed() {
		int x = mouseX/15;
		int y = mouseY/15;
		
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
				rect(x * 15, y * 15, 15, 15);
			}
		}
	}
	
	public void draw() {
		background(255);
		stroke(0);
		drawWorld();
	}
}
