package automail;

// import exceptions.RobotNotInMailRoomException;
import exceptions.TubeFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube {

    public final int MAXIMUM_CAPACITY;
    public Stack<MailItem> tube;
    private IMailPool mailPool;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(int maxCap, IMailPool mailPool){
    	this.MAXIMUM_CAPACITY = maxCap;
        this.tube = new Stack<MailItem>();
        this.mailPool = mailPool;
    }

    /**
     * @param strong is whether the tube belongs to a strong robot.
     */
	void fillStorageTube(boolean strong) {
		/* Determine max weight for the tube via the properties file */
		int maxWeight = strong ? Integer.parseInt(MyProps.getProp("Weight_Max")) : Integer.parseInt(MyProps.getProp("Weak_Weight_Max"));
		MailItem curItem;
		
		try{
			while(!isFull()) {
				curItem = mailPool.getMail(maxWeight);
				if(curItem != null) {
					this.addItem(curItem);  // Could group/order by floor taking priority into account - but already better than simple
					
				} else {
					break;
				}
			}
		}
		catch(TubeFullException e){
			e.printStackTrace();
			
		}
	}
    
    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == MAXIMUM_CAPACITY;
    }

    /**
     * @return if the storage tube is empty
     */
    public boolean isEmpty(){
        return tube.isEmpty();
    }
    
    /**
     * @return the first item in the storage tube (without removing it)
     */
    public MailItem peek() {
    	return tube.peek();
    }

    /**
     * Add an item to the tube
     * @param item The item being added
     * @throws TubeFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item) throws TubeFullException {
        if(tube.size() < MAXIMUM_CAPACITY){
        	tube.add(item);
        } else {
            throw new TubeFullException();
        }
    }

    /** @return the size of the tube **/
    public int getSize(){
    	return tube.size();
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem pop(){
        return tube.pop();
    }

}
