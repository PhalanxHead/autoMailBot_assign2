package automail;

public class Clock {
	
	/** Represents the current time **/
    private static int time = 0;
    
    /** The threshold for the latest time for mail to arrive **/
    public static final int LAST_DELIVERY_TIME = Integer.parseInt(MyProps.getProp("Last_Delivery_Time"));

    public static int time() {
    	return time;
    }
    
    public static void tick() {
    	time++;
    }
}
