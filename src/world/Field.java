package world;

public class Field {
	
	public static final int TYP_BUILD 	= 1;
	public static final int TYP_STREET 	= 2;
	
	public static final int CONTAIN_NOTHING 	= 1;
	public static final int CONTAIN_PLAYER	 	= 2;
	public static final int CONTAIN_SPAWN	 	= 3;
	public static final int CONTAIN_RUIN	 	= 4;
	public static final int CONTAIN_EXIT	 	= 5;
	
	private int typ;
	private int contains;
	
	public Field(int typ) {
		this.typ = typ;
		this.contains = CONTAIN_NOTHING;
	}
	
	public void setContain(int id) {
		this.contains = id;
	}
	
	public String toString() {
		switch(contains) {
			default:
				return ""+this.typ;
			case CONTAIN_PLAYER:
				return "x";
		}
	}
}
