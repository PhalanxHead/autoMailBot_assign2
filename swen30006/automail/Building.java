package automail;

public class Building {
	
	
    /** The number of floors in the building **/
    public static final int FLOORS = Integer.parseInt(MyProps.getProp("Number_of_Floors"));
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = Integer.parseInt(MyProps.getProp("Lowest_Floor"));
    
    /** Represents the mailroom location (Note an out of range floor might break the solution) */
    public static final int MAILROOM_LOCATION = Integer.parseInt(MyProps.getProp("Mailroom_Floor"));

}
