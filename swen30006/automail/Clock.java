/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

public class Clock {
	
	/** Represents the current time **/
    private static int time = 0;
    
    /** The threshold for the latest time for mail to arrive **/
    public static final int LAST_DELIVERY_TIME = MyProps.getIntProp("Last_Delivery_Time");

    public static int time() {
    	return time;
    }
    
    public static void tick() {
    	time++;
    }
}
