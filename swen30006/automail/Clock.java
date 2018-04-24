package automail;

public class Clock {
	
	/** Represents the current time **/
    private static int Time = 0;
    
    /** The threshold for the latest time for mail to arrive **/
    public static final int LAST_DELIVERY_TIME = Integer.parseInt(MyProps.getProp("Last_Delivery_Time"));

    public static int Time() {
    	return Time;
    }
    
    public static void Tick() {
    	Time++;
    }
}
