package creature;

import java.util.LinkedList;

import world.Field;

public class Creature extends Enemy{
	public Creature(int x, int y, LinkedList<Field> path, int spawnTimer) {
		super(x, y, path, spawnTimer);
	}
}
