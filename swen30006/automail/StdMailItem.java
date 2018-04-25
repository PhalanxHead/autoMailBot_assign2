/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

// import java.util.UUID;

/**
 * Represents a mail item
 */
public class StdMailItem extends MailItem {
	
    /**
     * Constructor for a MailItem
     * @param destFloor the destination floor intended for this mail item
     * @param arrivalTime the time that the mail arrived
     * @param weight the weight of this mail item
     */
    public StdMailItem(int destFloor, int arrivalTime, int weight){
        super.destinationFloor = destFloor;
        super.id = String.valueOf(hashCode());
        super.arrivalTime = arrivalTime;
        super.weight = weight;
    }
}
