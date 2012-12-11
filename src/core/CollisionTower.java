package core;

import java.util.Iterator;

import tower.Tower;

public class CollisionTower extends Thread {

	private DefenseCore core;
	
	public CollisionTower(DefenseCore core) {
		this.core = core;
	}

	public void run() {
		while(true) {
			
			Iterator<Tower> i1 = core.getTowers().iterator();
			while(i1.hasNext()) {
				Tower t = i1.next();
				
				t.checkRadius(core.getEnemys());
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
