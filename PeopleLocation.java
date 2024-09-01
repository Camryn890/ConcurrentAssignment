//M. M. Kuttel 2024 mkuttel@gmail.com
// Class for storing  locations of people (swimmers only for now, but could add other types) in the simulation

package medleySimulation;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PeopleLocation  { // this is made a separate class so don't have to access thread
	
	private final AtomicInteger ID; //each person has an ID
	private Color myColor; //colour of the person
	
	private AtomicBoolean inStadium; //are they here?
	private AtomicBoolean arrived; //have they arrived at the event?
	private GridBlock location; //which GridBlock are they on?
	
	//constructor
	PeopleLocation(int ID , Color c) {
		myColor = c;
		inStadium = new AtomicBoolean(false); //not in pool
		arrived = new AtomicBoolean(false); //have not arrived
		this.ID = new AtomicInteger(ID);
	}
	
	//setter
	public void setInStadium(boolean in) {
		inStadium.set(in);
	}
	
	//getter and setter
	public boolean getArrived() {
		return arrived.get();
	}

	public void setArrived() {
		arrived.set(true);
	}

//getter and setter
	public GridBlock getLocation() {
		return location;
	}

	public void setLocation(GridBlock location) {
		this.location = location;
	}

	//getter
	public  int getX() { return location.getX();}	
	
	//getter
	public  int getY() {	return location.getY();	}
	
	//getter
	public  int getID() {	return ID.get();	}

	//getter
	public  boolean inPool() {
		return inStadium.get();
	}
	//getter and setter
	public  Color getColor() { return myColor; }

	public void setColor(Color myColor) { this.myColor= myColor; }
}
