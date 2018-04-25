/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

public class Building {
	
	
    /** The number of floors in the building **/
    public static final int FLOORS = MyProps.getIntProp("Number_of_Floors");
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = MyProps.getIntProp("Lowest_Floor");
    
    /** Represents the mailroom location (Note an out of range floor might break the solution) */
    public static final int MAILROOM_LOCATION = MyProps.getIntProp("Mailroom_Floor");

}
