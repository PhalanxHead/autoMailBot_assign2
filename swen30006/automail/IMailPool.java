package automail;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public interface IMailPool {
	
	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
    void addToPool(MailItem mailItem);
	
	/**
	 * Gets an item from the mail pool
	 * @param maxWeight The maximum weight of the desired package)
	 * @return A mail Item meeting specifications.
	 */
	MailItem getMail(int maxWeight);
    
}
