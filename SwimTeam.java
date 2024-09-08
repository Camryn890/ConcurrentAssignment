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

	public Lock lock;

	public static final int sizeOfTeam=4;


	public static CountDownLatch[] latch;

	public static CountDownLatch latches = new CountDownLatch(10);


	SwimTeam( int ID, FinishCounter finish,PeopleLocation [] locArr) {

		//this.barrier = new CyclicBarrier(2);
		this.lock = new ReentrantLock();
		this.teamNo = ID;

		latch = new CountDownLatch[sizeOfTeam];

		swimmers = new Swimmer[sizeOfTeam];
		SwimStroke[] strokes = SwimStroke.values();  // Get all enum constants
		stadium.returnStartingBlock(ID);

		for (int i = teamNo * sizeOfTeam, s = 0; i < ((teamNo + 1) * sizeOfTeam); i++, s++) { //initialise swimmers in team
			locArr[i] = new PeopleLocation(i, strokes[s].getColour());
			int speed = (int) (Math.random() * (3) + 30); //range of speeds
			swimmers[s] = new Swimmer(i, teamNo, locArr[i], finish, speed, strokes[s], barrier, lock, latch,latches); //hardcoded speed for now
			latch[s] = new CountDownLatch(1);
		}
	}


	public void run() {
		try {
			for(int s=0;s<sizeOfTeam; s++) { //start swimmer threads

				swimmers[s].start();

				//latches.await();

				barrier.await();

				barrier.reset();

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
	

