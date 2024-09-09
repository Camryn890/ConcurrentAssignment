//M. M. Kuttel 2024 mkuttel@gmail.com
//Class to represent a swim team - which has four swimmers
package medleySimulation;

import medleySimulation.Swimmer.SwimStroke;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SwimTeam extends Thread {

	public static StadiumGrid stadium; //shared
	private Swimmer [] swimmers;
	private int teamNo; //team number

	public CyclicBarrier barrier = new CyclicBarrier(2);

	public static final int sizeOfTeam=4;

	public static CountDownLatch[] latch; // array of latches that synchronise simmers strokes
	public static CountDownLatch latches = new CountDownLatch(10); // latch to synchronise swimmers readiness


	SwimTeam( int ID, FinishCounter finish,PeopleLocation [] locArr) {

		this.teamNo = ID;

		latch = new CountDownLatch[sizeOfTeam]; // initialize latches for each team

		swimmers = new Swimmer[sizeOfTeam];
		SwimStroke[] strokes = SwimStroke.values();  // Get all enum constants
		stadium.returnStartingBlock(ID);

		for (int i = teamNo * sizeOfTeam, s = 0; i < ((teamNo + 1) * sizeOfTeam); i++, s++) { //initialise swimmers in team
			locArr[i] = new PeopleLocation(i, strokes[s].getColour());
			int speed = (int) (Math.random() * (3) + 30); //range of speeds
			swimmers[s] = new Swimmer(i, teamNo, locArr[i], finish, speed, strokes[s], barrier, latch); //hardcoded speed for now
			latch[s] = new CountDownLatch(1); // initialise a latch for each swimmer
		}
	}


	public void run() {
		try {
			for(int s=0;s<sizeOfTeam; s++) { //start swimmer threads

				swimmers[s].start();

				barrier.await(); // waits for barrier to synchronise swimmers


			}
			for(int s=0;s<sizeOfTeam; s++) swimmers[s].join();			//don't really need to do this;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
	

