/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

/**
 * a MailDelivery is used by the Robot to deliver mail once it has arrived at the correct location
 */
public interface IMailDelivery {

	/**
     * Delivers an item at its floor
     * @param stdMailItem the mail item being delivered.
     */
	void deliver(MailItem mailItem);
	
	/**
	 * Finds out the number of delivered mail items
	 * @return Number of delivered mail items
	 */
	int getDeliveredNum();
    
}