package creature;

import java.util.LinkedList;

import core.DefenseCore;

import world.Field;

public class SpeedCreature extends Enemy {

	public SpeedCreature(int x, int y, LinkedList<Field> path, int spawnTimer, DefenseCore core) {
		super(x, y, path, spawnTimer, core);
		
		this.setSpeed(5);
	}

}
