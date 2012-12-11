package core;

import java.util.Iterator;

import creature.Enemy;

public class MovementEnemys extends Thread{
	
	private DefenseCore core;
	
	public MovementEnemys(DefenseCore core) {
		this.core = core;
	}

	public void run() {
		while(true) {
			
			//Handle Creatures
			Iterator<Enemy> i2 = core.getEnemys().iterator();
			while(i2.hasNext()) {
				Enemy e = i2.next();
				
				e.move();
				
				if(e.reachedTarget()) i2.remove();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
