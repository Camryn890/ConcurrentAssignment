//M. M. Kuttel 2024 mkuttel@gmail.com
// GridBlock class to represent a block in the grid.
// only one thread at a time "owns" a GridBlock - this must be enforced

package medleySimulation;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class GridBlock {
	
	private final AtomicInteger isOccupied;
	// changed to atomic
	private final AtomicBoolean isStart;  //is this a starting block?
	private AtomicIntegerArray coords; // the coordinate of the block.
	
	GridBlock(boolean startBlock) throws InterruptedException {
		isStart = new AtomicBoolean(startBlock);
		isOccupied = new AtomicInteger(-1);
		coords = new AtomicIntegerArray(new int[2]);
	}
	
	GridBlock(int x, int y, boolean startBlock) throws InterruptedException {
		this(startBlock);
		coords.set(0,x);
		coords.set(1,y);
	}
	
	public int getX() {return coords.get(0);}
	
	public int getY() {return coords.get(1);}
	
	
	
	//Get a block
	public synchronized boolean get(int threadID) throws InterruptedException {
		if (isOccupied.get() == threadID) return true; //thread Already in this block
		return isOccupied.compareAndSet(-1,threadID); //space is occupied
		// set ID to thread that had block
	}
		
	
	//release a block
	public synchronized void release() {
		isOccupied.set(-1);
	}
	

	//is a bloc already occupied?
	public synchronized boolean occupied() {
        return isOccupied.get() != -1;
    }
	
	
	//is a start block
	public synchronized boolean isStart() {
		return isStart.get();
	}
}
