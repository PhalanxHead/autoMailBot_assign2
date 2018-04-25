/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package strategies;

import automail.*;

/**
 * The robot delivers mail!
 */
public class StrongRobot extends Robot {
    
    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public StrongRobot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, int maxWeight){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
    	currentState = RobotState.RETURNING;
        currentFloor = Building.MAILROOM_LOCATION;
        /* Need to make this more extensible */
        tube = new StorageTube(MyProps.getIntProp("Std_TubeSize"), mailPool);
        this.behaviour = behaviour;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.maxWeight = maxWeight;
        this.deliveryCounter = 0;
    }
}
