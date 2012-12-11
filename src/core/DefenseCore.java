package core;

import java.util.LinkedList;

import creature.Enemy;
import tower.Tower;
import world.World;

public class DefenseCore {

	//Deklariere die Welt
	private World world;
	
	//Listen für Türme und Feine um sie zu ordnen
	private LinkedList<Enemy> enemys;
	private LinkedList<Tower> towers;
	
	//Collision Towers Handler
	private CollisionTower collisionTower;
	private MovementEnemys movementEnemys;
	
	public DefenseCore(int width, int height) {
		this.world = new World(width, height);
		
		enemys = new LinkedList<>();
		towers = new LinkedList<>();
		
		this.collisionTower = new CollisionTower(this);
		this.collisionTower.start();
		
		this.movementEnemys = new MovementEnemys(this);
		this.movementEnemys.start();
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public LinkedList<Tower> getTowers() {
		return this.towers;
	}

	public LinkedList<Enemy> getEnemys() {
		return this.enemys;
	}

}
