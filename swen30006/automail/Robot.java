package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

/**
 * The robot delivers mail!
 */
public class Robot {

	StorageTube tube;
    IRobotBehaviour behaviour;
    IMailDelivery delivery;
    protected final String id;
    public RobotState currentState;
    private int currentFloor;
    private int destinationFloor;
    private IMailPool mailPool;
    private boolean strong;
    
    private MailItem deliveryItem;
    
    private int deliveryCounter;
    

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
    	currentState = RobotState.RETURNING;
        currentFloor = Building.MAILROOM_LOCATION;
        /* Need to make this more extensible */
        tube = new StorageTube(Integer.parseInt(MyProps.getProp("Std_TubeSize")), mailPool);
        this.behaviour = behaviour;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.strong = strong;
        this.deliveryCounter = 0;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     * @throws ItemTooHeavyException is the robot can't lift the item in its tube
     */
    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException {    	
    	switch(currentState) {
    		/** This state is triggered when the robot is returning to the mailroom after a delivery */
    		case RETURNING:
    			/* If its current position is at the mailroom, then the robot 
    			   should complete its returning tasks and change state */
                if(currentFloor == Building.MAILROOM_LOCATION) {
                	onReturn();
                
                } else {
                	/** If the robot is not at the mailroom floor yet, then move towards it! */
                    moveTowards(Building.MAILROOM_LOCATION);
                	break;
                }
                
            /** This case is triggered when the robot is in the mailRoom */
    		case WAITING:
    			/** Tell the sorter the robot is ready */
    			tube.fillStorageTube(strong);
                /** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
                if(!tube.isEmpty()) {
                	deliveryCounter = 0; // reset delivery counter
        			behaviour.startDelivery();
        			setRoute();
                	changeState(RobotState.DELIVERING);
                }
                break;
                
            /* This state is triggered when the robot is out delivering mail */
    		case DELIVERING:
    			deliverSteps();
                break;
    	}
    }

    /**
     * Completes the steps to deliver the items in the tube.
     * @throws ExcessiveDeliveryException
     * @throws ItemTooHeavyException
     */
	private void deliverSteps() throws ExcessiveDeliveryException, ItemTooHeavyException {
		/** Check whether or not the call to return is triggered manually **/
		boolean wantToReturn = behaviour.returnToMailRoom(tube);
		if(currentFloor == destinationFloor){ // If already here drop off either way
		    /** Delivery complete, report this to the simulator! */
		    delivery.deliver(deliveryItem);
		    deliveryCounter++;
		    if(deliveryCounter > 4){
		    	throw new ExcessiveDeliveryException();
		    }
		    /** Check if want to return or if there are more items in the tube*/
		    if(wantToReturn || tube.isEmpty()) {
		    	changeState(RobotState.RETURNING);
		    
		    } else {
		        /** If there are more items, set the robot's route to the location to deliver the item */
		        setRoute();
		        changeState(RobotState.DELIVERING);
		    }
		    
		} else {
			/* There was code to put the item back in the tube and 
			   head to the mail room but it slowed the system down */
            moveTowards(destinationFloor);
		    
		}
	}

	/**
	 * Completes the steps to be taken on return to the mailRoom
	 */
    private void onReturn() {
    	while(!tube.isEmpty()) {
    		MailItem mailItem = tube.pop();
    		mailPool.addToPool(mailItem);
            System.out.printf("T: %3d > old addToPool [%s]%n", Clock.time(), mailItem.toString());
    	}
    	changeState(RobotState.WAITING);
    }
    
    /**
     * Sets the route for the robot
     */
    private void setRoute() throws ItemTooHeavyException{
        /** Pop the item from the StorageUnit */
        deliveryItem = tube.pop();
        if (!strong && deliveryItem.weight > 2000) throw new ItemTooHeavyException(); 
        /** Set the destination floor */
        destinationFloor = deliveryItem.getDestFloor();
    }

    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
    private void moveTowards(int destination){
        if(currentFloor < destination){
            currentFloor++;
        }
        else{
            currentFloor--;
        }
    }
    
    /**
     * Prints out the change in state
     * @param nextState the state to which the robot is transitioning
     */
    private void changeState(RobotState nextState){
    	if (currentState != nextState) {
            System.out.printf("T: %3d > %11s changed from %s to %s%n", Clock.time(), id, currentState, nextState);
    	}
    	currentState = nextState;
    	if(nextState == RobotState.DELIVERING){
            System.out.printf("T: %3d > %11s-> [%s]%n", Clock.time(), id, deliveryItem.toString());
    	}
    }
    

}
