// Simple class to record when someone has crossed the line first and wins
package medleySimulation;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FinishCounter {
	private final AtomicBoolean firstAcrossLine; //flag
	private final AtomicInteger winner; //who won

	private final AtomicInteger winningTeam; //counter for patrons who have left the club
	boolean won = true;

	FinishCounter() { 
		firstAcrossLine = new AtomicBoolean(true);//no-one has won at start
		winner= new AtomicInteger(-1); //no-one has won at start
		winningTeam= new AtomicInteger(-1); //no-one has won at start

	}
		
	//This is called by a swimmer when they touch the fnish line
	public void finishRace(int swimmer, int team) {

		if(firstAcrossLine.compareAndSet(true,false)) {
			winner.set(swimmer);
			winningTeam.set(team);}

	}
	
	//Has race been won?
	public boolean isRaceWon() {
		return !firstAcrossLine.get();
	}


	public int getWinner() { return winner.get(); }
	
	public int getWinningTeam() { return winningTeam.get();}

}
