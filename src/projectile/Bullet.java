package projectile;

public class Bullet extends Projectile {

	public Bullet(int x, int y, int direction) {
		super(x, y, direction);
		
		this.setSpeed(2.5f);
	}

}
