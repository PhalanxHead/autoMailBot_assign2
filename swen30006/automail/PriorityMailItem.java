/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

public class PriorityMailItem extends MailItem{
	
	/** The priority of the mail item from 1 low to 100 high */
    private final int PRIORITY_LEVEL;
    
	public PriorityMailItem(int destFloor, int arrivalTime, int weight, int priorityLevel) {
		super.destinationFloor = destFloor;
        super.id = String.valueOf(hashCode());
        super.arrivalTime = arrivalTime;
        super.weight = weight;
        this.PRIORITY_LEVEL = priorityLevel;
	}
	
    /**
    *
    * @return the priority level of a mail item
    */
   public int getPriorityLevel(){
       return PRIORITY_LEVEL;
   }
   
   @Override
   public String toString(){
       return super.toString() + String.format(" | Priority: %3d", PRIORITY_LEVEL);
   }

}
