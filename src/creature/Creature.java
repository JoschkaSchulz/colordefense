package creature;

public class Creature {
	int direction;
	int x;
	int y;
	
	public Creature(int x, int y) {
		this.direction = 0;
		this.x = x;
		this.y = y;
	}
	
	public void rotate(int deg) {
		this.direction += deg;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void move(int distance) {
		this.x += (Math.sin(this.getDirection()/360.0)*distance);
		this.y += (Math.cos(this.getDirection()/360.0)*distance);
	}
	
	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}
}
