package world;

public class Field {
	
	public static final int TYP_BUILD 	= 1;
	public static final int TYP_STREET 	= 2;
	
	public static final int CONTAIN_NOTHING 	= 1;
	public static final int CONTAIN_PLAYER	 	= 2;
	public static final int CONTAIN_SPAWN	 	= 3;
	public static final int CONTAIN_BLOCK	 	= 4;
	public static final int CONTAIN_EXIT	 	= 5;
	public static final int CONTAIN_CHECKPOINT 	= 6;
	
	private int x;
	private int y;
	
	private int typ;
	private int contains;
	
	//For the a*
	private Field priv;	//Vorgängerfeld
	private int h;
	
	public Field(int typ, int x, int y) {
		this.x = x;
		this.y = y;
		
		this.typ = typ;
		this.contains = CONTAIN_NOTHING;
		
		this.h = -1;
		this.priv = null;
	}
	
	public Field getPriv() {
		return this.priv;
	}
	
	public void setPriv(Field f) {
		this.priv = f;
	}
	
	public int getH() {
		return this.h;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public int calcF(Field target) {
		return this.calcF(target.getX(), target.getY());
	}
	
	public int calcF(int targetX, int targetY) {
		// F = |---------- G -------------| + |--- H ---|
		return this.calcG(targetX, targetY) + this.getH();
	}
	
	public int calcG(Field target) {
		return this.calcG(target.getX(), target.getY());
	}
	
	public int calcG(int targetX, int targetY) {
		return ((Math.abs(targetX - this.x) + Math.abs(targetY - this.y))*10);
	}
	
	public boolean equals(Field other) {
		if(this.x == other.x && this.y == other.y) {
			return true;
		}else { return false; }
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	
	public void setContain(int id) {
		this.contains = id;
	}
	
	public int getContain() {
		return this.contains;
	}
	
	public String toFString(Field start) {
		if(this.contains == CONTAIN_BLOCK) return "< XXX >";
		else if(this.equals(start)) return "< END >";
		else if(this.getH() == -1) return "<9999>";
		else if(this.calcF(start) != 0) return "< "+(this.calcF(start) < 100 ? "0"+this.calcF(start) : this.calcF(start))+" >";
		return "< 00 >";
	}
	
	public String toCoordString() {
		if(this.contains == CONTAIN_BLOCK) return "< XXX >";
		return "< "+this.x+"/"+this.y+" >";
	}
	
	public String toTypString() {
		switch(this.contains) {
			default:
				return "< "+this.x+"/"+this.y+" >";
			case CONTAIN_PLAYER:
				return "< PLAYER>";
			case CONTAIN_NOTHING:
				switch(this.typ) {
					default:
						return "< "+this.x+"/"+this.y+" >";
					case TYP_STREET:
						return "< STREET>";
					case TYP_BUILD:
						return "< BUILD >";
				}
		}
	}
	
	public String toString() {
		return this.toCoordString();
	}
}
