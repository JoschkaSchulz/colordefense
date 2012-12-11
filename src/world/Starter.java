package world;

import java.util.LinkedList;

public class Starter {
	public static void main(String[] args) {
		World world = new World(5, 5);
		if(world.aStar(new Field(Field.TYP_BUILD,2,0), new Field(Field.TYP_BUILD,0,4)) == null) System.out.println("EXIT ON ERROR");
//		LinkedList<Field> path = world.aStar();
		System.out.println("Pfad:");
		System.out.println((world.aStar(new Field(Field.TYP_BUILD,2,0), new Field(Field.TYP_BUILD,0,4))).toString());
		System.out.println("~~~~");
		world.printFWorld(new Field(Field.TYP_BUILD,0,4));
		System.out.println("~~~~");
		world.printWorld();
		System.out.println("~~~~");
	}
}
