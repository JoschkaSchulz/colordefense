package world;

import java.awt.Point;
import java.util.LinkedList;

import javax.swing.text.html.HTML.Tag;

/*
 * f = g + h
 * h = entfernung zum Ziel
 * g = 10 horizontal/vertikal 14 diagonal
 * 
 * immer niedrigstes anliegendes kästchen nehmen!
 * 
 * Tutorial: http://www.policyalmanac.org/games/aStarTutorial_de.html
 */
public class World {
	public final static int NORTH 	= 1;
	public final static int EAST 	= 2;
	public final static int SOUTH 	= 3;
	public final static int WEST 	= 4;
	
	private Field[][] world;
	
	public World(int width, int height) {		
		//create world
		world = new Field[width][height];
		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				world[w][h] = new Field(Field.TYP_BUILD,w,h);
			}
		}
	}
	
	public void setField(int x, int y, int typ) {
		world[x][y].setContain(typ);
	}
	
	public Field getField(int x, int y) {
		return world[x][y];
	}
	
	public Field[][] getWorld() {
		return world;
	}
	
	private LinkedList<Field> getAllAround(Field target) {
		LinkedList<Field> ret = new LinkedList<>();
		
		if(target.getX() > 0) 
			if(world[target.getX()-1][target.getY()].getContain() != Field.CONTAIN_BLOCK)
				ret.add(world[target.getX()-1][target.getY()]);
				
		if(target.getX() < world.length-1)
			if(world[target.getX()+1][target.getY()].getContain() != Field.CONTAIN_BLOCK)
				ret.add(world[target.getX()+1][target.getY()]);
		
		if(target.getY() > 0) 
			if(world[target.getX()][target.getY()-1].getContain() != Field.CONTAIN_BLOCK)
				ret.add(world[target.getX()][target.getY()-1]);
		
		if(target.getY() < world.length-1)
			if(world[target.getX()][target.getY()+1].getContain() != Field.CONTAIN_BLOCK)
				ret.add(world[target.getX()][target.getY()+1]);
		
		return ret;
	}
	
	private Field getLowestF(LinkedList<Field> list, Field target) {
		
		Field result = list.getFirst();
		for(Field f : list) {
			if(f.calcF(target) < result.calcF(target)) result = f;
		}
		return result;
	}
	
	public LinkedList<Field> aStar(Field start, Field target) {
		//Variables
		LinkedList<Field> open = new LinkedList<>();
		LinkedList<Field> closed = new LinkedList<>();
		LinkedList<Field> path = new LinkedList<>();
		
		//Start
		start.setH(0);		//Set the H start value to zero
		open.add(start);	//Add the Start
		LinkedList<Field> around = this.getAllAround(open.get(0));	//Get all the nodes around
		for(Field f : around) {
			if(!open.contains(f) && !closed.contains(f)) {
				f.setPriv(open.get(0));
				f.setH(10 + open.get(0).getH());
				open.add(f);
			}
		}
		open.remove(start);
		closed.add(start);
		
		while(!closed.contains(target) && !open.isEmpty()) {
			Field add = this.getLowestF(open, target);
			open.remove(add);
			closed.add(add);
			
			around = this.getAllAround(add);
			for(Field f : around) {
				if(!open.contains(f) && !closed.contains(f)) {	//Wenn nicht in der open oder closed hinzufügen
					f.setPriv(add);				//Vorgänger setzten
					f.setH(10 + add.getH());	//Neuen H Wert für dieses Feld berechnen ausgrund des vorgänger Feldes
					open.add(f);
				} //Überprüfen ob G besser ist... ist es aber nieeeee :D ist immer gleich
			}
		}
		
		//search the end in the open List
		boolean found = false;
		for(Field f : closed) {
			if(f.equals(target)) {
				path.add(f);
				found = true;
				break;
			}
		}
		if(!found) return null;
		
		while(!path.contains(start) && !path.contains(target)) {
			path.add(path.getLast().getPriv());
		}
		
		return path;
	}
	
	public void printFWorld(Field start) {
		String s = "";
		 
		for(int h = 0; h < world[0].length; h++) {
			s = "[ ";
			for(int w = 0; w < world.length; w++) {
				s += world[w][h].toFString(start)+" ";
			}
			System.out.println(s+"]");
		}
	}
	
	public void printWorld() {
		String s = "";
		
		for(int h = 0; h < world[0].length; h++) {
			s = "[ ";
			for(int w = 0; w < world.length; w++) {
				s += world[w][h].toString()+" ";
			}
			System.out.println(s+"]");
		}
	}
}
