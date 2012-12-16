package tower;

import java.util.LinkedList;

import core.DefenseCore;

import projectile.Bullet;
import projectile.Projectile;

import creature.Enemy;

public abstract class Tower extends Thread{
	protected int x;
	protected int y;

	protected float load;
	protected int scaleField = 20;
	protected int radius;
	protected LinkedList<Enemy> nearEnemys;
	protected Enemy target;

	protected DefenseCore core;
	
	public Tower(int x, int y, DefenseCore core) {
		this.x = x;
		this.y = y;

		this.radius = 100;
		this.nearEnemys = new LinkedList<>();
		this.target = null;
		
		this.core = core;
	}

	public void run() {
		while(!this.isInterrupted()) {
			this.saveNearEnemys(core.getEnemys());
			
			//Wenn Tower geladen ist schieﬂe!
			if(this.load() && this.target != null) this.shoot(core);
			
			if(this.isInterrupted()) return;
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean load() {
		if(load <= 0) {
			return true;
		}else{
			load -= 5.0f;
			return false;
		}
		
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getRadius() {
		return this.radius;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getAbsX() {
		return (this.x * scaleField) + 10;
	}

	public int getAbsY() {
		return (this.y * scaleField) + 10;
	}

	public boolean equals(Tower other) {
		if (this == other)
			return true;
		if (this.getX() == other.getX() && this.getY() == other.getY())
			return true;
		return false;
	}

	public LinkedList<Enemy> getNearEnemys() {
		return this.nearEnemys;
	}

	public void saveNearEnemys(LinkedList<Enemy> enemys) {
		this.nearEnemys = this.checkRadius(enemys);
	}

	public Enemy getTarget() {
		return this.target;
	}

	/**
	 * berechnet den Winkel zweier Punkte relativ zur X-Achse im Gradmaﬂ
	 * 
	 * @param otherP
	 *            Den 2. Punkt der Winkelberechnung
	 * @return Der Winkel der 2 Punkte
	 */
	public int getAngle(int x, int y) {
		int dx = x - this.getAbsX();
		int dy = y - this.getAbsY();
		int adx = Math.abs(dx);
		int ady = Math.abs(dy);

		if (dy == 0 && dx == 0) {
			return 0;
		} else if (dy == 0 && dx > 0) {
			return 0;
		} else if (dy == 0 && dx < 0) {
			return 180;
		} else if (dy > 0 && dx == 0) {
			return 270;
		} else if (dy < 0 && dx == 0) {
			return 90;
		}

		double rwinkel = Math.atan((double) ady / adx);
		double dWinkel = 0;

		if (dx > 0 && dy > 0) { // 1. Quartal Winkkel von 270∞ - 359∞
			dWinkel = 180 + Math.toDegrees(rwinkel);
		} else if (dx < 0 && dy > 0) { // 2. Quartal Winkkel von 180∞ - 269∞
			dWinkel = 360 - Math.toDegrees(rwinkel);
		} else if (dx > 0 && dy < 0) { // 3. Quartal Winkkel von 90∞ - 179
			dWinkel = 180 - Math.toDegrees(rwinkel);
		} else if (dx < 0 && dy < 0) { // 4. Quartal Winkkel von 0∞ - 89∞
			dWinkel = Math.toDegrees(rwinkel);
		}

		int iWinkel = (int) dWinkel;

		if (iWinkel == 360) {
			iWinkel = 0;
		}

		return iWinkel-180;

	}

	public void shoot(DefenseCore core) {
		this.load = 100.0f;
		core.getProjectiles().add(new Bullet(this.getAbsX(), this.getAbsY(), this.getAngle(target.getAbsX(), target.getAbsY())));
	}
	
	public LinkedList<Enemy> checkRadius(LinkedList<Enemy> enemys) {
		LinkedList<Enemy> result = new LinkedList<>();
		Enemy targetEnemy = null;
		// if(!enemys.isEmpty()) targetEnemy = enemys.getFirst();

		for (Enemy e : enemys) {
			int check = (e.getAbsX() - this.getAbsX())
					* (e.getAbsX() - this.getAbsX())
					+ (e.getAbsY() - this.getAbsY())
					* (e.getAbsY() - this.getAbsY());
			if (check <= radius * radius) {
				result.add(e);
				if (targetEnemy == null)
					targetEnemy = e;
				if (e.getMoveCounter() > targetEnemy.getMoveCounter())
					targetEnemy = e;
			}
		}

		this.target = targetEnemy;
		return result;
	}

}
