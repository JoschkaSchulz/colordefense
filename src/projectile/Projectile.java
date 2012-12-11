package projectile;

public abstract class Projectile {
	private float x;
	private float y;
	
	private float speed;
	private int direction;
	
	public Projectile(float x, float y, int direction) {
		this.x = x;
		this.y = y;
		
		this.speed = 1.0f;
		this.direction = direction;
	}
	
	public void move() {
		x += Math.cos(direction)*speed;
		y += Math.sin(direction)*speed;
	}
	
	public float getAbsX() {
		return this.x;
	}
	
	public float getAbsY() {
		return this.y;
	}
}
