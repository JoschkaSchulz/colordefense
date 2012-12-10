package gui;

import java.util.LinkedList;

import creature.Creature;
import creature.Enemy;
import creature.SpeedCreature;
import processing.core.PApplet;
import world.Field;
import world.World;

public class CreatureGUI extends PApplet {

	private LinkedList<Enemy> enemys;
	private World world;
	
	private long pc;
	
	private LinkedList<Field> path1;
	
	public void setup() {
		size(625,625);
		background(255);
		frameRate(30);
	
		this.pc = 0;		
		this.world = new World(30, 30);
		
		this.enemys = new LinkedList<>();
		
		//Blocks
		for(int i = 0; i < 30; i++) {
			this.world.setField(i, 8, Field.CONTAIN_BLOCK);
			this.world.setField(i, 18, Field.CONTAIN_BLOCK);
		}
		
		this.world.setField(2, 14, Field.CONTAIN_BLOCK);
		this.world.setField(2, 13, Field.CONTAIN_BLOCK);
		this.world.setField(2, 15, Field.CONTAIN_BLOCK);
		this.world.setField(2, 12, Field.CONTAIN_BLOCK);
		this.world.setField(2, 16, Field.CONTAIN_BLOCK);
		
		this.world.setField(4, 14-3, Field.CONTAIN_BLOCK);
		this.world.setField(4, 13-3, Field.CONTAIN_BLOCK);
		this.world.setField(4, 15-3, Field.CONTAIN_BLOCK);
		this.world.setField(4, 12-3, Field.CONTAIN_BLOCK);
		this.world.setField(4, 16-3, Field.CONTAIN_BLOCK);
		
		this.world.setField(6, 14+1, Field.CONTAIN_BLOCK);
		this.world.setField(6, 13+1, Field.CONTAIN_BLOCK);
		this.world.setField(6, 15+1, Field.CONTAIN_BLOCK);
		this.world.setField(6, 12+1, Field.CONTAIN_BLOCK);
		this.world.setField(6, 16+1, Field.CONTAIN_BLOCK);
		
		this.world.setField(8, 14-2, Field.CONTAIN_BLOCK);
		this.world.setField(8, 13-2, Field.CONTAIN_BLOCK);
		this.world.setField(8, 15-2, Field.CONTAIN_BLOCK);
		this.world.setField(8, 12-2, Field.CONTAIN_BLOCK);
		this.world.setField(8, 16-2, Field.CONTAIN_BLOCK);
		
		this.world.setField(10, 14+1, Field.CONTAIN_BLOCK);
		this.world.setField(10, 13+1, Field.CONTAIN_BLOCK);
		this.world.setField(10, 15+1, Field.CONTAIN_BLOCK);
		this.world.setField(10, 12+1, Field.CONTAIN_BLOCK);
		this.world.setField(10, 16+1, Field.CONTAIN_BLOCK);
		
		this.world.setField(12, 14-3, Field.CONTAIN_BLOCK);
		this.world.setField(12, 13-3, Field.CONTAIN_BLOCK);
		this.world.setField(12, 15-3, Field.CONTAIN_BLOCK);
		this.world.setField(12, 12-3, Field.CONTAIN_BLOCK);
		this.world.setField(12, 16-3, Field.CONTAIN_BLOCK);
		
		this.world.setField(14, 14-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 13-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 15-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 12-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 16-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 17-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 18-2, Field.CONTAIN_BLOCK);
		this.world.setField(14, 19-2, Field.CONTAIN_BLOCK);
		
		path1 = this.world.aStar(new Field(Field.CONTAIN_SPAWN, 0, 14), new Field(Field.CONTAIN_EXIT, 29, 14));
		if((LinkedList<Field>) path1.clone() != null) {
			enemys.add(new Creature(		0,	14,	(LinkedList<Field>) path1.clone(),	0));
			enemys.add(new Creature(		0,	14,	(LinkedList<Field>) path1.clone(),	50));
			enemys.add(new Creature(		0,	14,	(LinkedList<Field>) path1.clone(),	100));
			enemys.add(new Creature(		0,	14,	(LinkedList<Field>) path1.clone(),	150));
			enemys.add(new SpeedCreature(	0,	14,	(LinkedList<Field>) path1.clone(),	250));
			enemys.add(new SpeedCreature(	0,	14,	(LinkedList<Field>) path1.clone(),	275));
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
				rect(x * 20, y * 20, 20, 20);
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
		fill(255,0,0);
		for(Enemy e : enemys) {
			if(e.getSpawnAt() < this.pc) {
				rect(e.getAbsX()-5, e.getAbsY()-5, 10, 10);	//creature
				
				e.move();
			}
		}
	}
}
