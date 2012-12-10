package creature;

import java.util.LinkedList;

import world.Field;

public class SpeedCreature extends Enemy {

	public SpeedCreature(int x, int y, LinkedList<Field> path, int spawnTimer) {
		super(x, y, path, spawnTimer);
		
		this.setSpeed(5);
	}

}
