package automail;

// import java.util.UUID;

/**
 * Represents a mail item
 */
public class MailItem {
	
    /** Represents the destination floor to which the mail is intended to go */
    protected final int destinationFloor;
    /** The mail identifier */
    protected final String id;
    /** The time the mail item arrived */
    protected final int arrivalTime;
    /** The weight in grams of the mail item */
    protected final int weight;

    /**
     * Constructor for a MailItem
     * @param destFloor the destination floor intended for this mail item
     * @param arrivalTime the time that the mail arrived
     * @param weight the weight of this mail item
     */
    public MailItem(int destFloor, int arrivalTime, int weight){
        this.destinationFloor = destFloor;
        this.id = String.valueOf(hashCode());
        this.arrivalTime = arrivalTime;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return String.format("Mail Item:: ID: %11s | Arrival: %4d | Destination: %2d | Weight: %4d", id, arrivalTime, destinationFloor, weight );
    }

    /**
     *
     * @return the destination floor of the mail item
     */
    public int getDestFloor() {
        return destinationFloor;
    }
    
    /**
     *
     * @return the ID of the mail item
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the arrival time of the mail item
     */
    public int getArrivalTime(){
        return arrivalTime;
    }

    /**
    *
    * @return the weight of the mail item
    */
   public int getWeight(){
       return weight;
   }
}
