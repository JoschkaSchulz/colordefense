package world;

/*
 * f = g + h
 * h = entfernung zum Ziel
 * g = 10 horizontal/vertikal 14 diagonal
 * 
 * immer niedrigstes anliegendes kästchen nehmen!
 * 
 * Tutorial: http://www.policyalmanac.org/games/aStarTutorial.htm
 */
public class World {
	public final static int NORTH 	= 1;
	public final static int EAST 	= 2;
	public final static int SOUTH 	= 3;
	public final static int WEST 	= 4;
	
	private int posX;
	private int posY;
	
	private Field[][] world;
	
	public World(int width, int height, int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		
		//create world
		world = new Field[width][height];
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				world[w][h] = new Field(Field.TYP_BUILD);
			}
		}
		world[this.posX][this.posY].setContain(Field.CONTAIN_PLAYER);
	}
	
	public void printWorld() {
		String s = "";
		
		for(int w = 0; w < world.length; w++) {
			s = "[ ";
			for(int h = 0; h < world[0].length; h++) {
				s += world[w][h].toString()+" ";
			}
			System.out.println(s+"]");
		}
	}
}
