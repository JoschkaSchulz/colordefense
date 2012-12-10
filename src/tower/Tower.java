package tower;

import java.util.LinkedList;

import creature.Enemy;

public abstract class Tower {
	protected int x;
	protected int y;
	
	protected int scaleField = 20;
	protected int radius;
	
	public Tower(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.radius = 50;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public int getAbsX() {
		return (this.x*scaleField)+10;
	}
	
	public int getAbsY() {
		return (this.y*scaleField)+10;
	}
	
	public LinkedList<Enemy> checkRadius(LinkedList<Enemy> enemys) {
		LinkedList<Enemy> result = new LinkedList<>();
		
		for(Enemy e : enemys) {
			int check = (e.getAbsX() - this.getAbsX())*(e.getAbsX() - this.getAbsX()) + (e.getAbsY() - this.getAbsY())*(e.getAbsY() - this.getAbsY());
			if(check <= radius*radius) {
				result.add(e);
			}
		}
		
		return result;
	}
	
	
}
