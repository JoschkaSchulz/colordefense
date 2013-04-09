package core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import projectile.Projectile;

import creature.Enemy;
import tower.Tower;
import world.Field;
import world.World;

public class DefenseCore {

	//Deklariere die Welt
	private World world;
	
	//Listen für Türme und Feine um sie zu ordnen
	private LinkedList<Enemy> enemys;		//Liste für alle Feinde
	private LinkedList<Tower> towers;
	private LinkedList<Projectile> projectiles;
	
	//Collision Towers Handler
	private MovementProjectiles movementProjectiles;
	
	//Semaphore
	Semaphore enemySemaphore;
	Semaphore enemyBackupSemaphore;
	
	public DefenseCore(int width, int height) {
		this.world = new World(width, height);
		
		this.world.setField(0, 0, Field.CONTAIN_SPAWN);
		this.world.setField(width-1, height-1, Field.CONTAIN_EXIT);
		
		enemys = new LinkedList<>();
		towers = new LinkedList<>();
		projectiles = new LinkedList<>();
		
		enemySemaphore = new Semaphore(1);
		enemyBackupSemaphore = new Semaphore(1);
		
		this.movementProjectiles = new MovementProjectiles(this);
		this.movementProjectiles.start();
	}
	
	/*
	 * World
	 */
	
	public World getWorld() {
		return this.world;
	}
	
	/*
	 * Tower
	 */
	
	public LinkedList<Tower> getTowers() {
		return this.towers;
	}

	public void removeTower(int x, int y) {
		for(Tower t : this.towers) {
			if(t.getX() == x && t.getY() == y) this.towers.remove(t);
		}
	}
	
	/*
	 * Enemy
	 */
	
	public void removeEnemy(int x, int y) {
		try {
			enemySemaphore.acquire();
			//enemyBackupSemaphore.acquire();
		} catch (InterruptedException e1) {
			//e1.printStackTrace();
		}
		Iterator<Enemy> i = this.enemys.iterator();
		Enemy enemy = null;
		while(i.hasNext()) {
			try {
				Enemy e = i.next();
				
				if(e.getAbsX() == x && e.getAbsY() == y) {
					enemy = e;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		
		if(enemy != null) {
			this.enemys.remove(enemy);
			enemy.interrupt();
			System.out.println("Lösche Enemy!");	
			
		}
		//enemyBackupSemaphore.release();
		enemySemaphore.release();
	}
	
	public void addEnemy(Enemy e) {
		try {
			enemySemaphore.acquire();
		} catch (InterruptedException e1) {
			//e1.printStackTrace();
		}
		this.enemys.add(e);
		enemySemaphore.release();
	}
	
	public LinkedList<Enemy> getEnemys() {
		return (LinkedList<Enemy>) this.enemys.clone();	
	}
	
	/*
	 * Projectiles
	 */
	
	public LinkedList<Projectile> getProjectiles() {
		return this.projectiles;	
	}
	
	public void removeProjectile(Projectile p) {
		this.projectiles.remove(p);	
	}

}
