package core;

import java.util.Iterator;

import projectile.Projectile;

import creature.Enemy;

public class MovementProjectiles extends Thread {
	
	private DefenseCore core;
	
	public MovementProjectiles(DefenseCore core) {
		this.core = core;
	}

	public void run() {
		while(true) {			
			try {	//Versuche alle zu bewegen
				//Handle Creatures
				Iterator<Projectile> i2 = core.getProjectiles().iterator();
				while(i2.hasNext()) {
					Projectile p = i2.next();
					
					p.move();
				}
			}catch(Exception e) {}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
