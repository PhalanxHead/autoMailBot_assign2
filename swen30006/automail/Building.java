package automail;

public class Building {
	
	
    /** The number of floors in the building **/
    public static final int FLOORS = Integer.parseInt(Simulation.amProperties.getProperty("Number_of_Floors"));
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = Integer.parseInt(Simulation.amProperties.getProperty("Lowest_Floor"));
    
    /** Represents the mailroom location (Note an out of range floor might break the solution) */
    public static final int MAILROOM_LOCATION = Integer.parseInt(Simulation.amProperties.getProperty("Mailroom_Floor"));

}
