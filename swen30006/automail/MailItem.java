/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

/**
 * Abstract Class for Mail Items
 */
public abstract class MailItem {

	/** Represents the destination floor to which the mail is intended to go */
	protected int destinationFloor;
	/** The mail identifier */
	protected String id;
	/** The time the mail item arrived */
	protected int arrivalTime;
	/** The weight in grams of the mail item */
	protected int weight;

	@Override
	public String toString() {
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
	public int getArrivalTime() {
	    return arrivalTime;
	}

	/**
	*
	* @return the weight of the mail item
	*/
	public int getWeight() {
	       return weight;
	   }

}
