//M. M. Kuttel 2024 mkuttel@gmail.com
//Class to represent a swim team - which has four swimmers
package medleySimulation;

import medleySimulation.Swimmer.SwimStroke;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SwimTeam extends Thread {
	
	public static StadiumGrid stadium; //shared 
	private Swimmer [] swimmers;
	private int teamNo; //team number
	public static final int sizeOfTeam=4;
	public static CyclicBarrier cyclicBarrier = new CyclicBarrier(sizeOfTeam);
	public static CountDownLatch latch = new CountDownLatch(4);

	private static final Lock ik = new ReentrantLock();
	
	SwimTeam( int ID, FinishCounter finish,PeopleLocation [] locArr ) throws InterruptedException {
		this.teamNo=ID;
		
		swimmers= new Swimmer[sizeOfTeam];

	    SwimStroke[] strokes = SwimStroke.values();  // Get all enum constants
		stadium.returnStartingBlock(ID);

		for(int i=teamNo*sizeOfTeam,s=0;i<((teamNo+1)*sizeOfTeam); i++,s++) { //initialise swimmers in team

			//latch.notifyAll();
			locArr[i]= new PeopleLocation(i,strokes[s].getColour());
	      	int speed=(int)(Math.random() * (3)+30); //range of speeds 
			swimmers[s] = new Swimmer(i,teamNo,locArr[i],finish,speed,strokes[s],ik); //hardcoded speed for now
		}
	}
	
	// synchronised method
	public synchronized void run() {
		for(int s=0;s<sizeOfTeam; s++) { //start swimmer threads

			swimmers[s].start();

		}
        //for(int s=0;s<sizeOfTeam; s++) swimmers[s].join();			//don't really need to do this;

	}
}
	

